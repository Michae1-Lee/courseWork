# Telegram Chat Monitor

Spring Boot service that receives Telegram webhooks, stores messages, and calculates basic response metrics.

## Quick start
1. Copy the sample environment file and set your bot token (do **not** commit real tokens):
   ```bash
   cp .env.example .env
   echo "TELEGRAM_BOT_TOKEN=<your_bot_token>" >> .env
   ```
   Optionally adjust `TELEGRAM_WEBHOOK_PUBLIC_URL`, `TELEGRAM_WEBHOOK_PATH`, incident keywords (`MONITORING_KEYWORDS`), or database credentials in `.env`.

2. Build and start PostgreSQL plus the app:
   ```bash
   docker-compose up --build
   ```

3. If `TELEGRAM_WEBHOOK_PUBLIC_URL` is set, the application will automatically register the webhook at `<public_url><path>`. Otherwise, set the webhook manually via the Telegram Bot API using the same path from `.env` (default `/telegram/webhook`).

4. Send a message to your bot in Telegram. Incoming updates will be persisted and metrics recalculated every five minutes.

## Configuration notes
* `TELEGRAM_BOT_TOKEN` is required; other variables have defaults defined in `application.properties`.
* `.env` is ignored by git to keep secrets out of version control.
