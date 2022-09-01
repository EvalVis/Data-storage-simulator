# Data storage simulator

## Models' relations

Since document can have 0 or 1 attachment
which is just another document, one-to-one relationship
solution was chosen. This prevents 
duplication: attachment model points to
its data in File table but also to a parent document.
Document points its data in File table and to case.

![](/Models/models.drawio.png)