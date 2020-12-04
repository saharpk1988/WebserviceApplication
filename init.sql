CREATE DATABASE IF NOT EXISTS photo_app;
CREATE USER 'sahar'@'localhost' IDENTIFIED BY '123pass';
GRANT ALL PRIVILEGES ON photo_app.* TO 'sahar'@'localhost';
FLUSH PRIVILEGES;
