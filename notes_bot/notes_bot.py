from telegram import Update
from telegram.ext import Application, CommandHandler, MessageHandler, filters, ContextTypes
from secrets import TOKEN
import requests

NOTES_ENDPOINT = 'http://localhost:8085/notes'

# Commands
async def get_all_notes_command(update: Update, context: ContextTypes.DEFAULT_TYPE):

    try:
        response = requests.get(NOTES_ENDPOINT)
        response.raise_for_status() # Launch an exception for states codes HTTP 4xx/5xx

        # Parse the response JSON
        notes = response.json()

        # Build the message based on the JSON
        if isinstance(notes, list) and len(notes) > 0:
            message_lines = []
            for note in notes:
                note_id = note.get("id", "N/A")
                note_name = note.get("name", "No Name")
                note_content = note.get("content", "No Content")
                message_lines.append(f"ID: {note_id}\nName: {note_name}\nContent: {note_content}\n")

            message = "\n\n".join(message_lines)
        else:
            message = "No notes found."

        # Send the message to Telegram
        await update.message.reply_text(message)

    except requests.exceptions.RequestException as e:
        print(f"Error making GET request: {e}")
        return None

async def get_note_command(update: Update, context: ContextTypes.DEFAULT_TYPE):
    if context.args:
        note_id = context.args[0]
        url = NOTES_ENDPOINT + f"/{note_id}"
        try:
            response = requests.get(url)
            note = response.json()
            note_name = note.get("name", "No Name")
            note_content = note.get("content", "No Content")
            message_lines = f"ID: {note_id}\nName: {note_name}\nContent: {note_content}\n"
            await update.message.reply_text(message_lines)
        except Exception as e:
            await update.message.reply_text(f'Error making GET request: {str(e)}')
    else:
        await update.message.reply_text('Please provide a note ID')

async def post_note_command(update: Update, context: ContextTypes.DEFAULT_TYPE):
    full_text = ' '.join(context.args)
    texts = full_text.split('.')
    if len(context.args) >= 2:
        name = texts[0].strip()
        content = texts[1].strip()
        data = {
            'name': name,
            'content': content
        }
        try:
            response = requests.post(NOTES_ENDPOINT, json=data)
            note = response.json()
            note_id = note.get("id", "No ID")
            note_name = note.get("name", "No Name")
            note_content = note.get("content", "No Content")
            message_lines = f"ID: {note_id}\nName: {note_name}\nContent: {note_content}"
            await update.message.reply_text(f'Success.\n{message_lines}')
        except Exception as e:
            await update.message.reply_text(f'Error making POST request: {str(e)}')

    else:
        await update.message.reply_text('Please provide both a name and description for the note.')

async def delete_note_command(update: Update, context: ContextTypes.DEFAULT_TYPE):
    if context.args:
        note_id = context.args[0]
        url = f'{NOTES_ENDPOINT}/{note_id}'
        try:
            response = requests.delete(url)
            await update.message.reply_text(f'DELETED Note with the id: {note_id}')
        except Exception as e:
            await update.message.reply_text(f'Error making DELETE request: {str(e)}')
    else:
        await update.message.reply_text('Please provide a note ID.')

async def patch_name_note_command(update: Update, context: ContextTypes.DEFAULT_TYPE):
    if len(context.args) >= 2:
        note_id = context.args[0]
        note_name = " ".join(context.args[1:])
        url = f'{NOTES_ENDPOINT}/{note_id}'
        data = {'name': note_name}

        try:
            response = requests.patch(url, json=data)
            note = response.json()
            note_name = note.get("name", "No Name")
            note_content = note.get("content", "No Content")
            message_lines = f"ID: {note_id}\nName: {note_name}\nContent: {note_content}\n"
            await update.message.reply_text(f'Status Code: {response.status_code}\n{message_lines}')

        except Exception as e:
            await update.message.reply_text(f'Error making the PATCH request: {str(e)}')
    else:
        await update.message.reply_text('Please provide a note ID and the new name.')

async def patch_content_note_command(update: Update, context: ContextTypes.DEFAULT_TYPE):
    if len(context.args) >= 2:
        note_id = context.args[0]
        note_content = " ".join(context.args[1:])
        url = f'{NOTES_ENDPOINT}/{note_id}'
        data = {"content": note_content}

        try:
            response = requests.patch(url, json=data)
            note = response.json()
            note_name = note.get("name", "No Name")
            note_content = note.get("content", "No Content")
            message_lines = f"ID: {note_id}\nName: {note_name}\nContent: {note_content}\n"
            await update.message.reply_text(f'Status Code: {response.status_code}\n{message_lines}')
        except Exception as e:
            await update.message.reply_text(f'Error making the PATCH request: {str(e)}')
    else:
        await update.message.reply_text('Please provide a note ID and the new content.')


async def patch_all_note_command(update: Update, context: ContextTypes.DEFAULT_TYPE):
    note_id = context.args[0]
    full_text = ' '.join(context.args[1:])
    texts = full_text.split('.')
    if len(texts) >= 2:
        note_name = texts[0].strip()
        note_content = texts[1].strip()
        url = f'{NOTES_ENDPOINT}/{note_id}'
        data = {
            'name': note_name,
            'content': note_content
        }
        try:
            response = requests.patch(url, json=data)
            note = response.json()
            note_name = note.get("name", "No Name")
            note_content = note.get("content", "No Content")
            message_lines = f"ID: {note_id}\nName: {note_name}\nContent: {note_content}\n"
            await update.message.reply_text(f'Status Code: {response.status_code}\n{message_lines}')
        except Exception as e:
            await update.message.reply_text(f'Error making the PATCH request: {str(e)}')

    else:
        await update.message.reply_text('Please provide a note ID, the new name and the new content.')


async def error(update: Update, context: ContextTypes.DEFAULT_TYPE):
    print(f'Update {update} caused error: {context.error}')

async def start_command(update: Update, context: ContextTypes.DEFAULT_TYPE):
    await update.message.reply_text('Hello! I\'m the bot for notes')

if __name__ == '__main__':
    print('Starting Bot...')
    app = Application.builder().token(TOKEN).build()

    # Commands
    app.add_handler(CommandHandler('start', start_command))
    app.add_handler(CommandHandler('get_all_notes', get_all_notes_command))
    app.add_handler(CommandHandler('get_note', get_note_command))
    app.add_handler(CommandHandler('post_note', post_note_command))
    app.add_handler(CommandHandler('delete_note', delete_note_command))
    app.add_handler(CommandHandler('patch_name_note', patch_name_note_command))
    app.add_handler(CommandHandler('patch_content_note', patch_content_note_command))
    app.add_handler(CommandHandler('patch_all_note', patch_all_note_command))

    # Errors
    app.add_error_handler(error)
    
    print('Polling...')
    app.run_polling(poll_interval=3)
