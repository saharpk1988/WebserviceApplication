FROM mysql:8.0.22
# Environment variables
ENV MYSQL_ROOT_USER: root
ENV MYSQL_ROOT_PASSWORD=secretadmin
ENV MYSQL_DATA_DIR=/var/lib/mysql

RUN mkdir /usr/sql
RUN chmod 644 /usr/sql


CMD ["mysqld"]

