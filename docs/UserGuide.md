# User Guide

Team Name: AY2021S1-CS2103T-T09-4

GreenTea is a **desktop app for learning a new language, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you like to use a FlashCard system to learn new languages, this app would be perfect for you. In this User Guide (UG), you will learn about the various commands and actions that would allow you to use GreenTea seamlessly.

- Table of Contents
  - [Viewing Help: help](#viewing-help-help)
  - [===== DECK OPERATIONS ====](#=====-DECK-OPERATIONS-====)
  - [Creating a new deck: new](#creating-a-new-deck-new)
  - [Removing a deck: remove](#removing-a-deck-remove)
  - [Selecting a deck: select](#selecting-a-deck-select)
  - [===== ENTRY OPERATIONS ====](#=====-ENTRY-OPERATIONS-====)
  - [Adding a New Entry: add](#adding-a-new-entry-add)
  - [Listing All Entries: list](#listing-all-entries-list)
  - [Editing an Entry: edit](#editing-an-entry-edit)
  - [Deleting an Entry: delete](#deleting-an-entry-delete)
  - [Clearing All Entries: clear](#clearing-all-entries-clear)
  - [Exiting the Program: exit](#exiting-the-program-exit)
  - [Saving the data](#saving-the-data)
  - [FAQ (WIP)](#faq-wip)
  - [Command summary](#command-summary)

### Viewing help: `help`

Shows a cheat-sheet of commands in addition to a link of this UG.

Format: `help`

### ===== DECK OPERATIONS ====

### Creating a new deck: `new`

Creates a new deck that can store entries of word-translation pairs.

Format: `new <NAME OF NEW DECK`

Examples: - `new Japanese-Animals`

### Removing a deck: `remove`

Removes a particular deck from GreenTea

Format: `remove <INDEX OF DECK>`

**Note:** to get the index of a deck, you may view it on the GUI. It would be the number displayed beside a particular deck

Examples: `remove 1`

### Selecting a deck: `select`

Selects a deck to perform deck-level operations on

Format: `select <INDEX OF DECK>`

**Note:** to get the index of a deck, you may view it on the GUI. It would be the number displayed beside a particular deck

Examples: `select 1`

### ===== ENTRY OPERATIONS ====

### Adding a new entry: `add`

Adds a new entry to the word bank.

Format: `add w/<WORD> t/<TRANSLATION>`

Examples:

- `add w/Fruits t/果物`
- `add w/Why aren’t they here yet? t/どうして彼らはまだここにいないの`

### Listing all entries: `list`

Lists all entries in the word bank with their respective index.

Format: `list`

### Editing an entry: `edit`

Edits an entry whose index is `<INDEX>`, replacing the original word with `<WORD>` and its translation with `<TRANSLATION>`.
You may provide either `<WORD>` or `<TRANSLATION>`, or both and only the one(s) provided will replace the original(s).

Format: `edit <INDEX> w/<WORD> t/<TRANSLATION>` OR `edit <INDEX> w/<WORD>` OR `edit <INDEX> t/<TRANSLATION>`

**Note:** to get the index of an entry, you may run `list` - the index would be shown beside its respective entry

Examples:

- `edit 1 w/hello` edits the word in `entry#1` to `hello`.
- `edit 1 t/こんにちは` edits the translation in `entry#1` to `こんにちは`
- `edit 1 w/hello t/こんにちは` edits the word and translation in `entry#1` to `hello` and `こんにちは` respectively.

### Deleting an entry: `delete`

Delete the entry whose index is `INDEX`.

Format: `delete <INDEX>`

**Note:** to get the index of an entry, you may run `list` - the index would be shown beside its respective entry

### Clearing all entries: `clear`

Clears all entries from the word bank.

Format: `clear`

### Exiting the program: `exit`

Exits the program.

Format: `exit`

### Saving the data

Entries ares saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

---

## FAQ (WIP)

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous GreenTea home folder.

---

## Command summary

| Action                                                                     | Format, Examples                                                                                     |
| -------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------- |
| **Help**                                                                   | `help`                                                                                               |
| **New**                                                                    | `new <NAME OF NEW DECK>` e.g. `new Japanese-Animals`                                                 |
| **Remove**                                                                 | `remove <INDEXOF DECK>` e.g. `remove 1`                                                              |
| **Select**                                                                 | `select <INDEXOF DECK>` e.g. `select 1`                                                              |
| **************\*\*\*\***************\***************\*\*\*\*************** |
| **Select a Deck before Using the following Commands**                      |
| **************\*\*\*\***************\***************\*\*\*\*************** |
| **Add**                                                                    | `add w/<WORD> t/<TRANSLATION>` e.g. `add w/Hello t/hola`                                             |
| **List**                                                                   | `list`                                                                                               |
| **Edit**                                                                   | `edit <INDEX> w/<WORD> t/<TRANSLATION>` OR `edit <INDEX> w/<WORD>` OR `edit <INDEX> t/<TRANSLATION>` |
|                                                                            | e.g. `edit 1 w/hello t/こんにちは` OR `edit 1 w/hello` OR `edit 1 t/こんにちは`                      |
| **Delete**                                                                 | `delete <INDEX>` e.g. `delete 3`                                                                     |
| **Clear**                                                                  | `clear`                                                                                              |
| **Exit**                                                                   | `exit`                                                                                               |
