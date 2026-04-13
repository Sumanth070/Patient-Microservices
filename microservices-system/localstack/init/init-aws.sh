#!/bin/bash

echo "🚀 Setting up LocalStack infra..."

# Create DLQ
DLQ_URL=$(awslocal sqs create-queue --queue-name appointment-notification-dlq --query 'QueueUrl' --output text)

DLQ_ARN=$(awslocal sqs get-queue-attributes \
  --queue-url $DLQ_URL \
  --attribute-names QueueArn \
  --query 'Attributes.QueueArn' \
  --output text)

# Create main queue
QUEUE_URL=$(awslocal sqs create-queue --queue-name appointment-notification-queue --query 'QueueUrl' --output text)

QUEUE_ARN=$(awslocal sqs get-queue-attributes \
  --queue-url $QUEUE_URL \
  --attribute-names QueueArn \
  --query 'Attributes.QueueArn' \
  --output text)

# Replace DLQ ARN
sed "s|REPLACE_DLQ_ARN|$DLQ_ARN|g" /opt/code/redrive-policy.json > /tmp/redrive.json

# Attach DLQ
awslocal sqs set-queue-attributes \
  --queue-url $QUEUE_URL \
  --attributes file:///tmp/redrive.json

# Create SNS topic
TOPIC_ARN=$(awslocal sns create-topic \
  --name appointment-topic \
  --query 'TopicArn' \
  --output text)

# Subscribe queue
awslocal sns subscribe \
  --topic-arn $TOPIC_ARN \
  --protocol sqs \
  --notification-endpoint $QUEUE_ARN

# Replace ARNs in policy
sed -e "s|REPLACE_QUEUE_ARN|$QUEUE_ARN|g" \
    -e "s|REPLACE_TOPIC_ARN|$TOPIC_ARN|g" \
    /opt/code/policy.json > /tmp/policy.json

# Apply policy
awslocal sqs set-queue-attributes \
  --queue-url $QUEUE_URL \
  --attributes file:///tmp/policy.json

echo "✅ Infra setup complete"