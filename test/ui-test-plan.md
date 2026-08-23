# Console UI test plan

Run from the repository root with `python test-ui/scripts/run_ui_tests.py test/ui-test-plan.md`.
Commands are run by the system shell. Console output includes both stdout and stderr.

The UI runner clears `data/victoria.txt` before each case so cases remain independent.
The save case below verifies that adding a task creates the data file.

## ToDo and list

### Aim

Confirm that a ToDo is stored and displayed with the ToDo marker.

### Inputs

```text
todo borrow book
list
bye
```

### Command

```text
java -cp out Victoria --test
```

### Expected output

```text
 Got it. I've added this task:
   [T][ ] borrow book
 Now you have 1 tasks in the list.
____________________________________________________________
 Here are the tasks in your list:
 1.[T][ ] borrow book
____________________________________________________________
Bye! Always nice to chat with you. See you soon!
____________________________________________________________
```

## Specific deadline and event errors

### Aim

Confirm that malformed deadline and event commands identify whether the description or timing part is missing.

### Inputs

```text
deadline /by Sunday
deadline return book
event /from 2pm /to 4pm
event meeting /from 2pm
event meeting /from /to 4pm
bye
```

### Command

```text
java -cp out Victoria --test
```

### Expected output

```text
 Oops! The description of this task cannot be empty.
____________________________________________________________
 Oops! The deadline is missing /by <date/time>.
____________________________________________________________
 Oops! The description of this task cannot be empty.
____________________________________________________________
 Oops! The event is missing /to <end>.
____________________________________________________________
 Oops! The event start time after /from cannot be empty.
____________________________________________________________
Bye! Always nice to chat with you. See you soon!
____________________________________________________________
```

## Load saved tasks

### Aim

Confirm that a task record already present on disk is loaded when Victoria starts.

### Inputs

```text
list
bye
```

### Command

```text
java -cp out Victoria --test
```

### Expected output

```text
 Here are the tasks in your list:
 1.[T][X] persistent task
____________________________________________________________
Bye! Always nice to chat with you. See you soon!
____________________________________________________________
```

## Delete a task

### Aim

Confirm that deleting a one-based task number removes the task and updates the task count.

### Inputs

```text
todo buy milk
event project meeting /from Aug 6th 2pm /to 4pm
todo submit report
delete 2
list
bye
```

### Command

```text
java -cp out Victoria --test
```

### Expected output

```text
 Got it. I've added this task:
   [T][ ] buy milk
 Now you have 1 tasks in the list.
____________________________________________________________
 Got it. I've added this task:
   [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
 Now you have 2 tasks in the list.
____________________________________________________________
 Got it. I've added this task:
   [T][ ] submit report
 Now you have 3 tasks in the list.
____________________________________________________________
 Yay! I've removed this task:
   [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
 You now have 2 tasks. Keep going!
____________________________________________________________
 Here are the tasks in your list:
 1.[T][ ] buy milk
 2.[T][ ] submit report
____________________________________________________________
Bye! Always nice to chat with you. See you soon!
____________________________________________________________
```

## Ignore corrupted records

### Aim

Confirm that malformed records on disk are ignored while valid records still load.

### Inputs

```text
list
bye
```

### Command

```text
java -cp out Victoria --test
```

### Expected output

```text
 Here are the tasks in your list:
 1.[T][ ] valid tasks
____________________________________________________________
Bye! Always nice to chat with you. See you soon!
____________________________________________________________
```

## Empty list

### Aim

Confirm that listing an empty task list uses a dedicated message.

### Inputs

```text
list
bye
```

### Command

```text
java -cp out Victoria --test
```

### Expected output

```text
 There are no tasks in your list.
____________________________________________________________
Bye! Always nice to chat with you. See you soon!
____________________________________________________________
```

## Reject non-lowercase commands

### Aim

Confirm that standard commands must be entered in lowercase.

### Inputs

```text
LIST
Bye
bye
```

### Command

```text
java -cp out Victoria --test
```

### Expected output

```text
 Oops! I don't recognize that command. Try a standard command format.
____________________________________________________________
 Oops! I don't recognize that command. Try a standard command format.
____________________________________________________________
Bye! Always nice to chat with you. See you soon!
____________________________________________________________
```

## Deadline and event

### Aim

Confirm that deadline and event commands create the correct subclasses and display their string date/time values.

### Inputs

```text
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
bye
```

### Command

```text
java -cp out Victoria --test
```

### Expected output

```text
 Got it. I've added this task:
   [D][ ] return book (by: Sunday)
 Now you have 1 tasks in the list.
____________________________________________________________
 Got it. I've added this task:
   [E][ ] project meeting (from: Mon 2pm to: 4pm)
 Now you have 2 tasks in the list.
____________________________________________________________
Bye! Always nice to chat with you. See you soon!
____________________________________________________________
```

## Reject non-standard input

### Aim

Confirm that text without a recognized command prefix is rejected instead of becoming a ToDo.

### Inputs

```text
read book
bye
```

### Command

```text
java -cp out Victoria --test
```

### Expected output

```text
 Oops! I don't recognize that command. Try a standard command format.
____________________________________________________________
Bye! Always nice to chat with you. See you soon!
____________________________________________________________
```

## Handle input errors with exceptions

### Aim

Confirm that empty task descriptions and unknown commands produce specific error messages and do not stop the session.

### Inputs

```text
todo
blah
bye
```

### Command

```text
java -cp out Victoria --test
```

### Expected output

```text
 Oops! The description of a task cannot be empty.
____________________________________________________________
 Oops! I don't recognize that command. Try a standard command format.
____________________________________________________________
Bye! Always nice to chat with you. See you soon!
____________________________________________________________
```
