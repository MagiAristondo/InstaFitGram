# InstaFitGram

Aquí explicaré els problemes que he tengut.

## Inserts d'usuaris

Per algún motiu me diu que 'IDENTITY_INSERT is OFF'. Això fa que no pugui fer inserts d'usuaris especificant l'Id.
Si activ l'identity insert des de l'SSMS segueix sense deixar-me fer inserts.

SOLUCIÓ -> Resulta que si quan execut s'insert, just abans activ s'IDENTITY_INSERT funciona. Però no basta fer-ho una vegada des de java i ja.
Cada vegada que faci un insert he d'executar sa linia just abans

```sql
SET IDENTITY_INSERT Usuaris ON;
INSERT INTO Usuaris (Id, Nom, Email, PasswordHash, IsInstructor) VALUES (?,?,?,?,?);
```