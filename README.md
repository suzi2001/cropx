# cropx
cropx home task
# election
The future election system

Available get commands:
http://localhost:8080/election/getTop10
http://localhost:8080/election/vote/<votingUserId>/<selectedUserId>/

In the example below user15 votes for user2:
http://localhost:8080/election/vote/15/2/

#The vote rest command should be should be changed to POST instead of get
#The DB initialization should be removed and the queries should be updated accordingle
#skipped rest path variables validation
#currently I run with 100 users (because the DB initialization is slow)
#did some unit tests, but  need to add more
