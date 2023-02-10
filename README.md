# Delivery
Test task solution

For starting application execute following command `docker-compose up --build` in terminal. Note that this project is dependent on demo application https://github.com/CovertHypnosis/Demo because it's composer is in charge of setting up both MySQL and Redis Server. Purchase logic(transaction) is managed in this application.Purchase process starts in Demo application, which in turn, sends message to Redis publisher. Afterwards, message is processed in Delivery application. After transaction is complete successfully sendgrid is in charge of sending email to user("currently commented") and Twilio SMS tool in order to send sms notification to user's phone.
