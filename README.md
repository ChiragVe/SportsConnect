To use the above code .
Make sure you have a database named "sportsconnect" in your mysql database.
Your database should have a table named "footballMatches".
The table structure should be :

+-----------+-------------+------+-----+---------+----------------+
| Field     | Type        | Null | Key | Default | Extra          |
+-----------+-------------+------+-----+---------+----------------+
| matchId   | int         | NO   | PRI | NULL    | auto_increment |
| team1     | varchar(50) | YES  |     | NULL    |                |
| team2     | varchar(50) | YES  |     | NULL    |                |
| matchDate | date        | YES  |     | NULL    |                |
| score     | varchar(20) | YES  |     | NULL    |                |
| MatchType | varchar(30) | YES  |     | NULL    |                |
+-----------+-------------+------+-----+---------+----------------+
