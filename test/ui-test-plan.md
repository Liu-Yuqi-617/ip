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
