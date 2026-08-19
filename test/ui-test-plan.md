# Console UI test plan

Run from the repository root with `python test-ui/scripts/run_ui_tests.py test/ui-test-plan.md`.
Commands are run by the system shell. Console output includes both stdout and stderr.

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
 I couldn't understand that command. Please use one of the standard formats above.
____________________________________________________________
 I couldn't understand that command. Please use one of the standard formats above.
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
 I couldn't understand that command. Please use one of the standard formats above.
____________________________________________________________
Bye! Always nice to chat with you. See you soon!
____________________________________________________________
```
