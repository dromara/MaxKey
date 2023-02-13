create database if not exists  `maxkey` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */ ;

use maxkey ;

source /docker-entrypoint-initdb.d/latest/maxkey.sql   ;
source /docker-entrypoint-initdb.d/latest/maxkey_data.sql   ;
 