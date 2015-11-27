t-server

RESTful backend for Algohub website and mobile APPs

##1. Compile on Ubuntu

###1.1 Prequisites

To make unit tests pass, you have to do the following things:

####1.1.1. Install MySQL and create the database

Install MySQL server,

    sudo apt-get -y install mysql-server
    sudo mysql_install_db
    sudo /usr/bin/mysql_secure_installation

Use `utb8mb4` to enable full unicode support,

    sudo vim /etc/mysql/my.cnf , add the following lines to appropriate section:
    [client]
    default-character-set = utf8mb4

    [mysql]
    default-character-set = utf8mb4

    [mysqld]
    character-set-client-handshake = FALSE
    default_time_zone = '+00:00'
    character_set_client = utf8mb4
    #character_set_connection = utf8mb4
    #character_set_database = utf8mb4
    character_set_filesystem = utf8mb4
    #character_set_results = utf8mb4
    character_set_server = utf8mb4
    #collation_connection = utf8mb4_unicode_ci
    collation_server = utf8mb4_unicode_ci
    default_storage_engine=InnoDB

    sudo service mysql restart


Change the password to `root123456` and enable remote access for `root`,

    mysql -u root -p
    # change root’s password
    UPDATE mysql.user SET password=PASSWORD('root123') where USER='root';
    # enable remote access for root
    GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
    set sql_mode = strict_all_tables;
    FLUSH PRIVILEGES;

Check `utf8mb4` related settings,

    SHOW VARIABLES LIKE "%character%";
    SHOW VARIABLES LIKE "%collation%";

Create a database named `algohub`,

    create database algohub;
    ALTER DATABASE algohub CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

####1.1.2 Install Redis

    sudo apt-get install redis-server

###1.2 Compile and test

    ./gradlew clean build

##2. Compile on CentOS

TODO
