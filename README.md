# rabbitmq-email-sender

### Overview
This service is about queuing email events into `RabbitMQ` to be sent later.

The `Publish/Subscribe` pattern is used here.

Sending email asynchronously allows retrying the operation in the background  in case it fails and without affecting the response time of a REST API for example.

### Variables to specify in `application.yml`

| ENV var                     | properties default     |
|-----------------------------|------------------------|
| RABBITMQ_HOST               | localhost              |
| RABBITMQ_PORT               | 5672                   |
| RABBITMQ_VIRTUAL_HOST       | /                      |
| RABBITMQ_USERNAME           | guest                  |
| RABBITMQ_PASSWORD           | guest          	       |
| RABBITMQ_EXCHANGE_NAME      | jad-test-exchange      |
| RABBITMQ_ROUTING_KEY_NAME   | jad-test-routing-key   |
| RABBITMQ_QUEUE_NAME         | jad-test-queue       	 |
