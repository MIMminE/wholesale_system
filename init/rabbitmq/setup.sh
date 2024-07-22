#!/bin/bash

echo "Declaring queue..."
rabbitmqadmin -u $RABBITMQ_DEFAULT_USER -p $RABBITMQ_DEFAULT_PASS declare queue name="my_queue" durable=true

echo "Declaring exchange..."
rabbitmqadmin -u $RABBITMQ_DEFAULT_USER -p $RABBITMQ_DEFAULT_PASS declare exchange name="my_exchange" type=direct

echo "Binding queue to exchange..."
rabbitmqadmin -u $RABBITMQ_DEFAULT_USER -p $RABBITMQ_DEFAULT_PASS declare binding source="my_exchange" destination="my_queue" routing_key="my_key"
