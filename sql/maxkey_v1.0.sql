-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: maxkey
-- ------------------------------------------------------
-- Server version	5.5.23-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `ID` varchar(45) NOT NULL COMMENT 'ä¸»é”®',
  `UID` varchar(45) DEFAULT NULL COMMENT 'ç”¨æˆ·ID',
  `APPID` varchar(45) DEFAULT NULL COMMENT 'åº”ç”¨ID',
  `RELATEDUSERNAME` varchar(200) DEFAULT NULL COMMENT 'ç”¨æˆ·å',
  `RELATEDPASSWORD` varchar(200) DEFAULT NULL COMMENT 'å¯†ç ',
  `MODIFIEDDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'ä¿®æ”¹æ—¶é—´',
  `APPNAME` varchar(100) DEFAULT NULL,
  `USERNAME` varchar(45) DEFAULT NULL,
  `DISPLAYNAME` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='FormBasedç”¨æˆ·é…ç½®è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES ('26b1c864-ae81-4b1f-9355-74c4c699cb6b','7BF5315CA1004CDB8E614B0361C4D46B','fe86db85-5475-4494-b5aa-dbd3b886ff64','test@connsec.com','6bc4cb2c2967c12ed0bdd93ee2e48bdd','2015-05-05 11:12:26','è…¾è®¯ä¼ä¸šé‚®ç®±','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('36371b00-1a88-4fce-955d-1828e778bdd6','7BF5315CA1004CDB8E614B0361C4D46B','c8038bd4-12a4-4b45-9d43-61b3ecdc2eb4','myltpv9527@163.com','3752d262dd2efc6db55a752ab2049f89','2015-01-16 15:36:44','æœ‰é“äº‘ç¬”è®°','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('37d640d9-5f1d-4b41-a20e-bad0d547fa20','7BF5315CA1004CDB8E614B0361C4D46B','850379a1-7923-4f6b-90be-d363b2dfd2ca','myltpv9527@163.com','3752d262dd2efc6db55a752ab2049f89','2015-01-17 03:05:24','ç½‘æ˜“163é‚®ç®±','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('44f7e28d-399c-4756-a0c6-bad96bde4a8a','7BF5315CA1004CDB8E614B0361C4D46B','c1cabfaeb9a448028ffab2148da9f65c','21313','c1e66f5a57a76f19c9683e8a3a58898a','2015-05-05 08:06:49','QQ Login','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('56bf4642-46d5-4d82-83a8-4b777eb0f371','3557da42-7f8d-4a55-ae19-b4abb1be926d','850379a1-7923-4f6b-90be-d363b2dfd2ca','shimingxy@ddd.com','26e47d909a07145e50aac4b523a24bc4','2015-05-05 09:13:34','ç½‘æ˜“163é‚®ç®±','wwww','wwww'),('e3ee7f1c-48cf-48ee-ad07-29491b649bf5','7BF5315CA1004CDB8E614B0361C4D46B','a08d486a-2007-4436-aeda-4310e9443ec7','a1','9653392173f7e8eb2a4545aa816506ff','2019-10-20 13:06:08','OAuth v1.0a Demo','admin','ç³»ç»Ÿç®¡ç†å‘˜');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apps`
--

DROP TABLE IF EXISTS `apps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apps` (
  `ID` varchar(45) NOT NULL COMMENT 'ä¸»é”®',
  `NAME` varchar(300) NOT NULL COMMENT 'åº”ç”¨åç§°',
  `LOGINURL` varchar(300) NOT NULL COMMENT 'åº”ç”¨ç™»å½•åœ°å€',
  `CATEGORY` varchar(45) DEFAULT NULL COMMENT 'åº”ç”¨ç±»åž‹',
  `SECRET` varchar(400) DEFAULT NULL COMMENT 'SECRET',
  `PROTOCOL` varchar(300) DEFAULT NULL COMMENT 'åº”ç”¨åè®®',
  `ICON` blob COMMENT 'åº”ç”¨å›¾æ ‡',
  `STATUS` tinyint(3) unsigned DEFAULT NULL COMMENT 'çŠ¶æ€',
  `CREATEDBY` varchar(45) DEFAULT NULL COMMENT 'åˆ›å»ºäºº',
  `CREATEDDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'åˆ›å»ºæ—¶é—´',
  `MODIFIEDBY` varchar(45) DEFAULT NULL COMMENT 'ä¿®æ”¹äºº',
  `MODIFIEDDATE` datetime DEFAULT NULL COMMENT 'ä¿®æ”¹æ—¶é—´',
  `DESCRIPTION` varchar(400) DEFAULT NULL COMMENT 'æè¿°',
  `VENDOR` varchar(45) DEFAULT NULL,
  `VENDORURL` varchar(200) DEFAULT NULL,
  `CREDENTIAL` tinyint(4) DEFAULT '0',
  `SHAREDUSERNAME` varchar(100) DEFAULT NULL,
  `SHAREDPASSWORD` varchar(400) DEFAULT NULL,
  `SYSTEMUSERATTR` varchar(45) DEFAULT NULL,
  `ISEXTENDATTR` varchar(4) DEFAULT NULL,
  `EXTENDATTR` varchar(400) DEFAULT NULL,
  `SORTINDEX` int(10) unsigned DEFAULT '0',
  `ISSIGNATURE` tinyint(4) DEFAULT '0',
  `VISIBLE` tinyint(4) DEFAULT '0',
  `ISADAPTER` tinyint(3) unsigned DEFAULT '0',
  `ADAPTER` varchar(400) DEFAULT NULL,
  `PRINCIPAL` varchar(45) DEFAULT NULL,
  `CREDENTIALS` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='åº”ç”¨è¡¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apps`
--

LOCK TABLES `apps` WRITE;
/*!40000 ALTER TABLE `apps` DISABLE KEYS */;
INSERT INTO `apps` VALUES ('1327c121-cfad-49ba-bf61-afd3a1e09d5c','LTPA-Cookie','http://tokenbased.demo.maxkey.org:8080/demo-ltpa/ltpa.jsp','FINANCE','d6227a3d7756c255874ec7029678b8d1','Token_Based',_binary 'ÿ\Øÿ\à\0JFIF\0\0\0\0\0\0ÿ\Û\0C\0		\n\r\Z\Z $.\' \",#(7),01444\'9=82<.342ÿ\Û\0C			\r\r2!!22222222222222222222222222222222222222222222222222ÿÀ\0\0Ž\0–\"\0ÿ\Ä\0\0\0\0\0\0\0\0\0\0\0	\nÿ\Ä\0µ\0\0\0}\0!1AQa\"q2‘¡#B±ÁR\Ñð$3br‚	\n\Z%&\'()*456789:CDEFGHIJSTUVWXYZcdefghijstuvwxyzƒ„…†‡ˆ‰Š’“”•–—˜™š¢£¤¥¦§¨©ª²³´µ¶·¸¹º\Â\Ã\Ä\Å\Æ\Ç\È\É\Ê\Ò\Ó\Ô\Õ\Ö\×\Ø\Ù\Ú\á\â\ã\ä\å\æ\ç\è\é\êñòóôõö÷øùúÿ\Ä\0\0\0\0\0\0\0\0	\nÿ\Ä\0µ\0\0w\0!1AQaq\"2B‘¡±Á	#3Rðbr\Ñ\n$4\á%ñ\Z&\'()*56789:CDEFGHIJSTUVWXYZcdefghijstuvwxyz‚ƒ„…†‡ˆ‰Š’“”•–—˜™š¢£¤¥¦§¨©ª²³´µ¶·¸¹º\Â\Ã\Ä\Å\Æ\Ç\È\É\Ê\Ò\Ó\Ô\Õ\Ö\×\Ø\Ù\Ú\â\ã\ä\å\æ\ç\è\é\êòóôõö÷øùúÿ\Ú\0\0\0?\0÷ú(¢€\n(¢€\n(¢€\n(¢€\n+\ÅZ¬º\'†ouZh”l\r\Ð1`?L\çð®À\Þ4Ö¯üMŸuö˜nC}\åU(ÁK0c\ë4QE\0QE\0QE\0QE\0QE\0QE\0QE\0TR\Ë<²º¢\"–fc€\0\êI\ì+\Ë<Sñ6Y™\ìôò\ã\åZ\í‡\Ì\ß\î\Ð´yô­\0z¯\â=\'CMÚ…\ìq1Xó—?E\×¨ü\\IM7L’OI.(ÿ\0¾W\'õ\å\Ò\Ë$ó4³;I+³»f>¤žM2\ìuZ\ß\Ä\rc^°–\Â\â;H­\å\Æõw\Éc\Ü\nÁ\Òõ;R†þÐ ž\"J\ï‚GÐš¦`H\ä¤r\08\0“\èh\èvµH\Èš}¬\Ê:˜Ù£o\×p®¯Iø— \ê%c¸y,f<bqò\çý\á\Ç\çŠñA8E±ô\ìr$±¬‘°taÀ\ä\ê\rI_<h>)\Õ<;0k)É€œµ¼™h\Ûðþ\î1ø×²øg\Åö&¶\ÌÉºAû\Ûg?2ûƒüC\Ü~8¦#¢¢Š(\0¢Š(\0¢Š(\0¢Š(\0¦³ª)v`ªI\'\0\nuy\Ç\Åµ­¢h–¯‰nu\Ã)\åc\ì?\àDÀ{\Ð3\ãŸ\ZI¯Üµ…“²é‘¶28ó\Øw?\ìú\Äö\ÇEwðOö\ìƒQ\ÔP:6Â§O=‡Qþ\è\ï\êxõ¤Q•\á¿\êž$a,J-ì³†¹”P£«\ÓÞ½CHøu ih­%·\Ûg™.~aŸeû£ò5\Ô\ÇC\Z\Ç\Z„E\0*¨À\0t\0v-1\\\ç¼O¢6¡\á;\Ý;O†8\ät8\ÕB©*Á±\Ç8\Åyÿ\0€¼)¬\Ûø¦\ë\Ëm`µY¦w¥@Qß®sÓŠö\Z(—¨øwH\Õ‹\Ý:\Úbˆ >Œ0G\ç\\¿ð¨i´)\Û#Ÿ³N\ÙÏ°o\è:õ\Z(\æ;›Y\ì®\Þ\æ†h\ÎpA§Y\Ý\ÜX]\Çuk3E<M•u<ƒýG·z÷oxJ\Ï\Ä\ÖD0X¯cS\ä\\\Èÿ\0dú©ô\í\ÔW…_Y\\i×³Y\Ý\Æbž*\ê{oPzƒ\ÜR\î~ñ\\>&Ó²Ác½„ž!\ÓÙ‡±ý\ÓW\Î\Z³>ƒ«Á¨[’J:g‡C\Ô¯ó¾‡²¼†þ\Ê»w\Èwf˜2\ÅQ@‚Š( Š( ¥•-\á’Y,q©f\'°$\×\Î:Î§&³¬]\ê“™\ä, ÿ\0\nôUü^\Ùñô\Øø2ý•Š¼ª°©\í\éšðZOh\Òkú\í¶ž„ª\ÈÙ‘‡ð \å\åÀ÷\"¾†µµ†\Ê\Ö+[x\Ö8bPˆƒ P0y\Ç\ÂM45\rQ‡\Ì\Î-\Ðú\07\êG\å^@0¢Š(QE\0QE\0WüPð\ê]\é\ÃZ·@\'¶fÀûÑ“\Ôû‚\"k\Ñ*˜#»¶–\Úe\Ý¨Q‡¨#ù\ÐÌ•\ë\n5ƒq¦\Ü\é2¾ZÕ¼È?ÀÇ‘ø6\ïªò»\ÛF°¿¸³“\ïÁ+D\ßUb?¥t¯MŸ-q	p­{\år?U†{½QLAEP\\ÏŒ¼R</§E4py÷¹HÔœ(À\É\'\àqù\×MXúÿ\0‡¬|Gaö[\Õqµ·\Ç$gŒd§­\0xÿ\0ˆ¼u¨x“M[›kh£Y]\Ñn\É $ñ\Ír\Õ\Þø\ËÀv~\ÑVþ\Ö\ê\æf3¬l%Û€<ðp?:\à©u¾ñõ\ï‡tÔ°†\Æ\ÚXU\ÙÙ™™Y‹žGÓ¥{6•¨Åª\éV·ð±\ÜF$Un£=Ó¥ygƒ¼	¦ø‹EŽþ\â\î\é\\JÑ¼q\ÊxäŒŒ‚+Ö­ma±´†\Ö\ÝpB¡@\0À\Ä\Éè¢ŠQE\0QE\0\Ãx\Ç\Ç\ÇÃš‚iö¶‹q>Á$†F!T\àrO®\æ¹Oø\ÃÄ·Q\ÝK4¶\×»\ÆÜ£>™<\ÐŠ\êÚ‹jº­\ÕûÄ±µ\Ã\ïdRJƒœg\éM\ÓoŸL\Ô\ío£P\Ïo*Èª\Çˆ9Á©µ\Ë´­rö\ÂZX\í\å1‡pb\0\Îq\ïšfaý«¬\ÙXd,e”d€O\'ðŠ=Kø¯-Æ£\ÚtQÁ+2E#¹8r+\Ôk\Ó~i¶7ð\ÝM{qr\"`\â&UU,Fq\É\Õ\ßS$(¢Š\0(¢Š\0\ç<u`uj1¢\î‘L£\ÝHo\ä\rxÒ¾ždWBŽ+==«\ç_i\r¡\ë\×z{²7&2‰*.> \Ð4w?	5EY/ô©±q\'®>Vÿ\0\ÙMz¥|Ù£\ê“\èÚ½¶¡o\Ë\ÂÛŠ\ç—£õô6›¨[\êºtÖ¯¾”2ž\ã\Ôpx?J—h¢ŠQE\0QE\0Vúò->\Â{É›CH\Ç\ØÕªóŠ>$U‰tgùÜ¬—Dº½U~¤\àŸ`=h\Ìnn\î\êk™Nd™\ÚFú±$ÿ\0:\ê¾X›\ßA!\\¥¬m3{m«~•\ÈW±|-\ÑMž‰.¥*\â[\Ö2:F¹ó%\åHgESQE\0QE\0Wñ\'\Ã\rªé«©Z¦n\í\ïP9’.¤}GQø\×yE\0|½]_‚üe/†®Œ¤\ÓflºLgû\Ê?˜\ïõ­ø¬e—WÒ¢-hÄ´ð¨\æÝ”w\×\Ó\é\Ó\Ïi}3g{o¨Z\Çsi2Mƒ(\èr«ó¦‡\â]OÃ³™,.6\Æ\Ç/Œ\Æÿ\0U\ì}\Æ\rz^‘ñSJºUMJ)l¥\èX$gñ\Ä~4\Åc\Ð)¡ƒgW\â¿\Ø\Ë\àû\étZÝ§eU_*Q¼À \àŸ¥yÿ\0\Ã\ÝQôÿ\0@¯t\"µ™\\M½ö©I\ä\ã9Ÿñ ,{­\Ì\ßø÷\ÃzrÚŠN\ãø-Ÿ\Äqùš\àµÿ\0Š÷\è\ÐiQOV!¥#Û²þ§\ÜP#²ñ—\íü=ZÚ²Í©¸ùS¨‹?\Ä\ß\ÑzŸ¥xœó\Ës<“\Ï#I,ŒYÝŽK1\êM1Ù\ÙÝ™™‰ff9$ž\ä÷5sJÒ¯5B;;Œ“?àª½\É=€¤Qs\Âþ›\Äz\ÔVh`_žyð <þ\' ÿ\0\ëW\ÐpÁ¼Á\nŠ5\nª½€cøgÃ–\Þ\ZÒ–\ÖžVù§˜Œú\Ðßn\Ó$(¢Š\0(¢Š\0(¢Š\0(¢Š\0B# ×x§\á¤W¯%\î†R	\ÎK[7¹ÿ\0dÿ\0	ö\éô¯F¢€>g¿Ó¯4Ë£m}m-¼\Ãød\\g\Ü„{Š­_K^\é\Öz¹†ö\Ú+ˆðJ‡áž•\Ç\ê\nôK¢^\Ò[‹6=•÷¨üŸÖ\ÜñŽ§\'­žµ\Üx“\á\Ìú•>¢5(\ç†¹SV;˜/©\ë™\Ðô‰5\Ýf\r6V\'›v aKtJC3©\'¥z­Ÿ\Â(T†½Õ¤q\Ý`ˆ/\êIþU\Õ\é	\ÐtfW·±Y&^“N|\Æ\ã<À\ns\Ê|=\à=[]d•\ãkK#Éže °ÿ\0ezŸ¯Þ½‡AðöŸ\á\Û/³\ÙC´œ%n^C\êOô\è+^Šb\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€2üA¤&»¡]i¯)‹\ÎP¤A\Ç~@®7Áÿ\0nô=u5+û¨\É\"HrrH\Æ\âH\àž+Ñ¨ Š( Š( Š( Š( Š( ÿ\Ù',1,NULL,'2019-11-09 10:13:11',NULL,NULL,'','','',0,NULL,NULL,NULL,'0',NULL,4,0,1,1,'org.maxkey.authz.token.endpoint.adapter.TokenBasedDefaultAdapter',NULL,NULL),('38c8a544eaa04aaeaa49d9c77ace40cd','Token_Based_Json','http://tokenbased.demo.connsec.com:8080/','FINANCE','c1f6adfcadd8ba23f73395f16a45dbe7','Token_Based',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0U\0\0\0X\0\0\0„k»„\0\0\0	pHYs\0\0\Ä\0\0\Ä•+\0\0\0tIME\Þ!5ž½ò\Ê\0\0\0tEXtAuthor\0©®\ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬\Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0À\æ‡\0\0\0tEXtSource\0õÿƒ\ë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨\î\Ò\'\0\0 \0IDATxœ\íyœT\å•÷¿\Ïs—ªê½¡\ÙZQ@EP\Ü…\ã“˜\Ä%Ž˜\è\ä}Í˜8“\è¨q‹3.˜u|\'!f\ÔD‡\à›Œ\Ñ\à‚ˆŠ‚,*‹¬‚\Ð,\Ý@¯Uu—\ç¼Üº—ª¦\é2\ï\Ì\ä|¸}«n\Ýû,\ç9\Ïy\Îùó\\\Ô\ÑG-ž\ç‘\Íf\0D¥¥ýS\Ì\'Á¶m\Ò\é4}ûöE2xð`&Mš„\çyÿ\Ù\íü/I\"B*•\âõ\×_§µµ”Rr\íµ\×\Ê_\é\ã\Óô\é\ÓEk-¶ˆ\Ë\åÃ \ÐZ÷z\ê‡aXr–\Âôˆ\ËSJaY\Ö_Tµc‚\0¥Æ˜’¶h­÷ù\Ü\n\ÃÛ¶\É\år(¥°\ã´\Öh­{\ÜiÁƒˆ\àº\îG>[Ü±Þ’¤ð)ú,(Rô›`PDƒ\èº\î~\Ë‚\0\éUßµ\Ö\É9¾f%\Ò\ÔmG\n\n9¾/f¦eYIlÜ¸‘Í›7³~ýzššš’F|ö³Ÿå¤“N\Â\ÓCÉˆX3.bbt\Þ\Ë\È\Â\"/|hll\ä ££ƒT*@EEC†\á\È#¤¾¾žaÃ†%5…aˆeYûô5\îWŒ-\æa‰¤vþ¡«‡\ã‚\ã\ém\Û6¾\ï3{ölfÍš\Å[o½\Åö\íÛ»e\ÍQG\Å\É\'Ÿœ0õÀ¥\"\â (D)Œ$RE´ò#–‹B”Bhm\Ó\Ö\Ö\Î]w\Ý\Õm\ÉeeeŒ?ž‹.ºˆ«®ºŠ>}ú–e†a‰v\ÕÞ®øe\ïsWw\Ý*2·\Â0\Äq6n\Ü\È%—\\\Âo¼Qr¯eY%SÉ²,Œ1TVV\î£g¬\î¢i­@¤PI\á3‚26¨h\Ò\0-‚vdÏž=%}‰×ŽŽ^{\í5^{\í5|ðA{\ì1¦L™’XB½Ñµ\Ì\Ôb&h­ioo\ç\â‹/fÑ¢E¤\Ói‚ HF6\ÃDš‹©£££\Ûi´?\éN\0B\Ð\"‘Þ”‚2D¡” ´¥A)\Â0dÛ¶m]–\ëC¥Ž\ã°~ýz.¸\à\Þ|óM†’¨­ž\Ò35¦ p]—™3g²h\Ñ\"\\\×%—\Ë%\Ó9CÈ˜1c;v,555‰š8þø\ãK”û¾\ÒZ¼Ô|V+Ö›!\"!\"\ZŒ ´EÒ‹ƒ\Ø(\Êj«úp\ï½÷&\Ú\Ò\Ò\ÂÚµkY½z5\ï¿ÿ~T®R\äóy\Ça\çÎ\Üw\ß}<ø\àƒø¾\ßkGH¾öµ¯‰ˆˆ\ïûbŒ\é\Ò3Æˆ1F\Â0”0\å”SN¥”(¥¤\ÐOqG\î¾ûnill\ìÖ–\Ã0)«S\r…CDL\á³)|£³	C1atMN²~‡dý@‚’\çø¹@üv_\Âv#\Æ\ëº¹\\NfÏž-ýúõÛ¶“>(¥¤®®Nššš’þv\Ý^I®ù¾/\"\"—^z©X–%=’\ÔXÊ¶m\ÛÆŠ+’i¬µ\Æ\Ã\å—_\ÎM7\ÝD‰:\"s#6Ùº¦\"iˆ%C\í½.ª ÁF!\Ê\n¤\íX\×\Ø\Ê\êMMX\Ã\ÖrD}\ri ôŒ\â+Œ	E«¤_|1¹\\Ž+®¸\"QK\"Bcc#\ï½÷§vZbƒö\Ä\ìS\ã‚W¬XÁ\îÝ»“†c\0˜>}z²\0\Ä6klž\Ä÷\Ä\ÛW¯My‰\ïÕ€ ¢€\0#šÀRŽ Hñ›ùKù\ÅC/²}\Ã&\Ä\å¤HgúÐ¯Oš¿¹ô\\x\Ö\ì0ÀQb,Ð -E\ä\É\å.¼ðB\r\ZDCC@²\ê/Z´ˆSO=5atOT@%U)Eccc\Ô\åÂ¢P[[Ë‘G‰\Ö\Z\Çq’>~¦XB»×§1SÑ‘TF&hbrˆR´fS\\só¿3{þB¾tÑ™|ÿ\ÆË¨V\ËÚ†v\æ\Í_ÇŸ\Þ\Ú\Î\Õ7ýŽ\'>¹Œ\Ýúe†d@‚\0Ë²1:’ôhòhªªª7n\r\r\r%fÞ¶m\ÛJ\ìÔžX=bj\\I[[[\É5¡®®Ž\Ú\ÚÚ¤%\ì\êô½³#Q¸J\ì¡TÁl*,TJaD£\ÐX\Ê\á\ËßœÅ‚•˜óð|ú˜A¼ñ\Ú:®û\é|­\ÝA«\åµu\ÔôÁ\Ó/l%\Ìþ–\ß\Üw9\å’+‹R\é‚]›\"”h!\Z4h\Ð>m\í\è\èH\Ú\Úu{»§^9¼\í\í\íû\\K§Ó¤R©\Ä#)¦b§¢\Ø÷.%Urˆ\è\Èc*ø\ì–•\æ¾G\ß\à™…Kù¿¿ºŠO3ˆÀ÷øã›«ù?­cO&H;lk\ÝI³R3h$Ï¼\ÜÀ¿žSV\Úr@Á+00vc‹\Û3µ¸J½bj¬‹G/þ/ZÅŠÿÀ¨`N¦{ôQP\nŒmi¶7û\Ìx|!÷³8c\ä ðÁv\\²õP—F¥-|rXN–GÞ´“®«\ç\Ñ\'\ßf\ã\ÎV´\Î`\nÎ‚\È\ÞÁ.V\âö\Æ}\ì9\Õc;µ¸¢ù\Æ\Ì\í©rOV%(Q‘§¯\" F¡PZ±ð÷(¦_t*\ï®[Ï¼\×W³%7€‡~·”tY\Z?ô±\Åe\"-B?UÁ¦\æ¯/^\ÉðOŸ€	}”%(ö.œ1#‹\Û\\,,=¥^1µ˜ŠX\ÌÄžzN1 ¢$ZH0\n”ÂˆH€%«\ß[\Ê1C`P\ßr¾ô\í™·lT Ú²q\\Á-\Ñ\Zg€\0•RøaÀºõ;d!¨\0°’¶v\×\æ\ÞHj\ïAÄƒL‘lJ!ÉƒÊƒÑ À\èH¿\ín5”÷\íÃ²\rM,[³“\Úú£\ÈT\çœ˜ª\0²„J!hÀA\Ä\Ñ\ä²~\äq)Ð‰¿RJ]	Ao$µWL\í®ò\Ó(A%p\â\Ð&‹ƒ-_\ÕUl\ß\ÝÂªµ;ðMÓ…A@´°)D\Å(U\\&h\åRQA‰\ÂHX`ô¾k±¤\Æ\çÞ¨%©\Åu‡<\èô\n¡²	•\r€ñqhSŠE\ZÙ´£•v\ãd\ÊŒB¡QK^º*fL`p”0\ê¨AˆD Jöõ\ì–N\íS‹W\Ë}\Zÿ-’\\‘½\ßE\"\í]*Z\Üöš¨$7 ø~\Z×©\á\á\ç\ß\æ¥\Å ]Ãœ\â–U\Z…B¡E\"tªP».°\ã8˜öŒR\Æ)\ÇE J[dvÿô\Ñ\Î\Ê~øs 7X\ìE\Å.(Ù¢²(Ž\æl¤\È\â\é-bŠ9ý3E”Bƒ¡¶p\Ë\ÊøÍ‹\ïr\Û=O\ãT\Ä\Ø\å\ìjó\É\Ê*Ø—\ìµ 2‡Œ1Q„­ù\Ö\å\'2°<…\n\à\ÖÀ}\ÕUñô÷<¯¤o=‘\Øá©±T577\'ï¬‡DE¾»…Š}ö\0,K£J\\=!B”V…0‰ &Š-‰ñq\\‡Ž@óÏ¿|‰=üªb(¡±\É\çò\äÀ\ÛCºf\0Z[…AŒ%U¡-\rbhÙ¾o\\z:_9ÿ‚|ˆ¶¬\Ârhv9\Å\ãk\Ùl¶×±´¦~”±W²~ýú’\ï\0™t\n¥mP>øA§[°œ:\0>\Ü\Õ\Æ\î¶,–­9l`_ªm0ˆThPJƒe•\æù·\Ör÷\ÌWxuE:Ý`Ojm&ß—‘GL¤½\Å\ç/¿\Ã\ÖšL\í\á(Ý†ˆJ\ZÁ\ÎmæŽ¯O\á»×œƒ6cE¶®‡	UÁ´°O7l\Ø\Ðe¼*\æSñy¿Lý¨‘‰Á\åÜcG­¶¢ŒK\Îo%Už¡1_\Ç\Ì_\çws–²z™O{K@&\å°!\çœ4ŒK\Ï;•GÀ¶,ZZ/_Å¬g–óÌ››0x¤µÍ\n‡«þ\æd>û©1PCŠH³,½\äDù\ÃRÿ\ãR:¨A”ƒFfwq\×\rŸ\åºó\'†hm2‚Vš\ÐDƒ)\"Œ9r&mØ°]»vQWW·3;ó«óõ}¦gC8þ\îû>¶m³j\Õ*/^œ@d±B?v\Ü”J<\Â@á–•³\ä\Ã]ü\ïžf\á\ÂML9iw\\;‘£†¢µ¥W­\æ\É\ç6ò\ïOÿ\Ã\ê-Ü´C\Ãö,M»,|\×Ee\ê(\Ï6óõÏ\âº+NgP\ß*\0Œ\ç„X0~ø \Ækµvÿô\èû”÷;‚Ž\æ&\Î8®WŸ?¼×Š­5P˜\È0P‘\ÉUœƒ0r\ä\ÈD8 \nh6551w\î\\.»\ì2<\Ï\ë2ü\Þ]\0sŸ\éA¢+‹ñ\Òt:\rÀwÞ‰\ïû	3EKkN;ý\ÔHŸ\Z;e±z»á’¿}Œ\Æ]üûÏ¾\ÈyS¥aWO>ûó—\ì M\ê¨<¼œ=Û¶°lG#\è\0Ä¡r\à@‚\ÝmU-|ÿž/pöÄ¡…AÍ¢tˆe¥±m\rxù,X).ÿüTú\ãV\Zƒ€ \ì\àÓ§@F\ß\ä\ÐvyABe\n\ã\"Â±\Ç\ËGÁºu\ë°m;‘¼3fpÁPYY‰\ïû%|Š\Ãóq\ÎC1•¬þ\Æ\Ç!•J\áº.Ž\ã\àº.\étšõ\ë\×3}út~û\Û\ß&±(\×u	Ã³?ýiNœ</\Ñ\Ê%gl¾u\×\ïÙ²©\Ù^Í—\Ï>/Ÿ\ç\ê\ëÿ~0—§—l\æ¥\åo±bó\ZB2eý)K¤*SIv\ÏVN“\â\Ù/\ç\ì‰C\Égó˜0e» \Ëm!\ÊÅ¨\Êq¥2¨–£\èG.ÛŽ¶F­ŠL&Ap\áØ‚ƒP\äR\Çý(++ãª«®B$J4SJa\Û6Ë—/\çœs\ÎaÁ‚X–…\ã8%<q§KœµDR-\ËbÍš5Ì™3\'Zt‚€\íÛ·³d\É.\\Hkkk2\íòù<C‡\å_þ\å_BŸ l!\åö\å\å×¶òÜŸ\ß\åŸo½³\Æ…ö,}\Ê39÷\Ã<\é\Ê*°0aˆ	Z*q”…\ÉnaL}Š‡î½„Á5):ry,Û ´€h,cG\è•.¨(*ô°µ\ËØ‘õ\Ì]²•Êš4\ëªøL\Z£ml1˜HLŽ\Â^=\í\×_=,`Îœ9\ÉuÛ¶Y°`§v\Z“\'Of\âÄ‰6Œòòr|\ß\ç¸\ãŽã“Ÿüd²ï’©¶móö\Ûosýõ\×\ï\Ãý˜\é±\Þq‡O}\êSü\ìg?c\È\Ð!ø9G¹ †\ß<»‚au‡ñµ\'°vG;\ÙŸwoeÞ‚pu_üœ­5\ÊÊ¢MžP:ðD!\áN¾û‹^“\"\çûXndW\nv„*©\0”ƒ.\ï¢öÚ¦u\Õ@\'mW\á¤2‘‰l\n¢¹-\Òýš\á8³f\Í\â¶\Ûn\ã\á‡f÷\îÝ‘j+ôù\Í7\ß\ä\Í7\ß,\áÇ•W^É™gžY*Ú‡©\"QJ`:NF0\Öq,_)Å´iÓ¸\ãŽ;8ù\ä“\È\år¸–‹A\á\á³h\å&¦LIM\Ê\æü\ëg²q[\Ï7•Ø®\è_{(±°\ÄFi\Ê+\Ò\Ô÷©Áˆ`[ ”]p_£l\n-V\äj\Æ\É*Š9¡e\å),¥#\ÏL[i3‰ÿ!}ª$¾“\Ö\Z\Ïó°,‹þð‡ü\Ã?üw\ß}7¿ü\å/“0uœ\ÞKp†TWWw\ém•(¥¹\\Ž\\.GGGù|\Ïó’¬¹ø\á·\Þz‹«¯¾šûï¿Ÿl6‹eY&‡QYD¹\ä\Û,†ÓŸyK6ò\îªv<†ò\Z$SFP˜‚–€*T¯$Àµ¹¶:²ù\Â5ƒ&À\"À’0C”*¸ø…œ½Z£‹¬\ç\ÓZ÷ˆ6GcÚ\Å/6™L†\ßý\îw|ù\Ë_\æñ\Ç/ñ\Ã0\Ä÷}òù<\í\í\í\är9ZZZºô¶\ìÎ…9’«¯¾\Z­5\Ùl–††V¬XÁ–-[’QÝ³g{ö\ì\á†n\à\Ïþ3³f\Í\ÂMiŒ†”(RJhl\ÛÎ›\Ëódý>¸™Y/@‰\Â\ÒQüI„J*²m‰€”-9´R\ä=E¤¦B°4‚ÆˆFL#ˆ0Øˆ!šŽ\\ŽÀX\ä\Å\î–¤€H\çªdP¤ù3ÆJ¥ø\Þ÷¾\Ç\í·ß¾—9K Cúô\éÃ¨Q£8ò\È#©¬¬$C\Î8\ãŒ.\ÃG%L\rÃ‰\'ò‹_ü¢\ä¦\æ\æf^x\á\îº\ë.–/_žH­\ëºÌ™3‡o¼‘Ÿþô§4\ç\ÚH§\á”É‡±xù\n\Òã§ l	¢ni%!J™(EGT7X„V\ßÿ\åŸ8uòH«p\Évx¤]½7\ÃOb¼ ÀTc\nñ|‡]-\nT\n/\'lÙ‘%u4¡€­\ã¨w\Ì\à\Ò>Û¶\ÍO<Á\í·ßŽëº‰g†!õõõ\Üz\ë­I(»+)\ï\ÌÔ’\éÛ©¾\ï\ãyž\ç†!UUU|\á_\à\å—_fÒ¤I‰\'Û«¿œ9“5\ë\ÖQ\æ–Cpþg†±\ê\ÝfV¾\ßHEM¼ñ°È¡ñRfGù£beð•ª\ì\Ïò\Í>_»\é\ßØ²«…²r— T…\Ù[@¢T  @i\ÛvQJX¹b#ÚªDYU¼½d%JA€Bë½‘Y)\0)J\nª¬\È#œ1cF2•\ãõcÀ€Ì›7¯ý\ë\Z4ˆ •˜\Ï\ç“\\«\Î\Õ>:5\Î\Þ(\Î\Ü²\Ù,µµµ\Ü{\ï½%£\ä8ž\ïó\â/\âh…Ÿmc\ê‰#8\ç\Ôq¼õ\Æ2\Ê\Ê28\Æ\Æ4–q\Ðâ ŒB‹\"…¯l;…-\Z	Sdª\à¥wòœwÝ¿òü¢µ¤\ÒZ;\ß†BÞ€…\n/×ˆm\åX±±‘\Åkv’©Ê¶-.\ÞÊŽVŒca\Â6D¼\Èü21˜m<¢•+W²lÙ²\Änýýn¸£>šl6‹\ïû	œ\è8NIV\ãG.T¯x\ét\Zc§žz*cÇŽ-\É\áX¾li4m\Ò6\åÀnü#†\Ú464\ã¤mB-øZ\ãkEh	b\åp\Ë\\–\Æ”u4n\ÛH¶i#¡rx·Á\á\â¿ÿ\rßº\ïIVmÚ\í¸¤l\ÑA(aˆ›q\Ø#·ýôm¶\ï±ho\ÛL\Ý\Ì;ë›¹úž?°+pql?	<CI\r‰ Ha\íÚµ\É*oŒÁ÷}***¸è¢‹0\Æ\àº.¶m\'üˆƒ˜7\é¦\î¥Šú˜1cx\ï½÷’g\0\Þ}oET™¶	\Ï\àþ5<öÀ\Õüý³ya\Ù$]‰\å¨\È\Â\"ôB\Ô\îõŒ\è\ïp\Í\×\Î\à\äñƒÉ‡†\Í;\Zy\ãõU,X\ÐÁ»ü\ä?6ñ\ëù¿\á‹g\æ’OÍ¸Ñƒ)\ÏT yi\Ùnú\ÞoY½ažPÏ©“£Ï,\Ý\Ü\ÄKó?\à\Ü+Á÷þ\îSLr89\ÏG“E\ã eÐˆŠ\Üñ•+W–Hœˆ0d\È†Z\â®w¦\îÐªeR\Ç\çQ£F%L.\Î\èˆ\Ò\Å5–	¼,#\ëk™ý¯\ËsWóûg\ßb\Ó\ÖÝ´uø¸VŠºÃª8k\Ê(.=\ï8Ž>¬\ïÞŠ\Æ\ãŠOLd\ë¶6~þ\Ìr~>g5Û·µò¯ÿ±Y¿_Á¨¾us9\ÝÁ¼?-fÄ <ù\ÐeL›0œõ›w1{\ÞRv\í0z$K¯\æ’oý3¾{!—Ÿw<A\è¡ñl\ÄD‘Z€>ø ¤ÿa2jÔ¨f\î/\\´_I\ÝF‡¬Ÿ÷0E1\ÅÒ®\"4y*•\æ«g\â«g¢©µƒ\Öö´J3°®·°hF!FE§\rõ\Ó\Üy\Õ\É\\~öXþùÁyü\Å-øµÇ°¤¹‰·¾‹´\ZN;\îXÿÁ\\aÖ‚¥\\ö\Ç0þ‘ ªÀÍ‘©¬ª:n¼\ïi†\ëË´c‡†9”Vq´¦¤o\ÅL\ìÓ§O\"8=Ý±sÀ\á”b·®s¤1úbPª`d\ã\"b£•F$$\Ìf1¹€¾e\å>°CTb‘4“\Ç\Òp\Ú\Ö\àXˆc“<o#¥xøŸ>Ï­_?\ÏÛ¨ªV*Ëª©¯\Ípÿ-\ç3¸:Ž:„27Ce¿*úÖ„\Ôg\Ú(óZ0VÍºšûÿ\Ïh÷\rJ¹(”\n¢övŽ®¢©=\r§ô(ð·¿Hc¼‹\ÒMw·rG““€l7\âzX–‹’¢4!(Á\Ó>¢Ž.»OlZ=[¯œ\Âw¿:\Z«u7Á¶2.ÿ\ÌN^\Ã\æ]»h\ê\Èñû§–bL¡\ÕN^\í$§ ¯*IUT³tEKV6 µ…1>\n\Î6k\ç\\¯\Î\É!J½\ÊP\é:­0r\ãD­¢\"Ji”\àŠ.DK5(;Ê,<i\ÇÉ½XQšŠ²I,¥Ð„˜\Ðð\í+\Ï\ã¯4°xI\çŸó^]¶Ž¯\Üô<ºº‚-\r\Ø\é:\Ä3UxDŽE:Øm94U¼²d-§{„\n\Ñ.@ Wa\èýò\ç@o\ì,•û^+¸Ø…\ÐZ„’v•\è(t,*b´\è‚4H\ÉY£+sˆ@p#BÆ¶9ñø\Ñ\èò2ú\×\ÖðóYøp\ën¶7yX\é\Ê\Èk“\ÈFÀ*xo\n/v5\Ç;T¬BúODû[˜{C½Ú\ÒU\îQ!‘Xú¢Ä²\Èç–’…¡x¿DI-ºP¸o\ïuCœX[–Æˆ\Ç\æ\Í\ÛX²Ñ§²<…\Ö6\í€E€I¨h`rmK\\^­#ð¡\àu\ÅÀ\ÞFR\á $¨•„§cq%B ö2K\ÑeU|R/”~/„@Pp\ÄÀ2\È\í\ä\Ý÷iñ«fB?D\ÙN!\Ï\'ž\'Ñ³¡´„X*`È€ºþj\nöò¾}\è®=¡^e¨|TJLW9I‡°\nª\ã\äñƒ©¬\rxò¥÷q\Üjr–`\Ù\Z%a´ßª°`Æ¤•`©€\Úr‡Ó§ŒF©¥t\Ñ\"Õ•jû8ô±ÔºZ-;›^g\ZÅ¤\n\å›À0jh\Î;w^ò¹Ž<¡-xvÂŒD#¢pmMÛ®&¦ž>š1Cj1A{\ÄXJói;÷\ç/ž oœ-–\ÈÝŠ½¬ƒ\ÅÐ˜¢@ž Â€»¦ŸÍ‘‡	Û›\Öc\ë>d)\Ã\Ò/C%ø(Œ\Ö8—|s;G\ÕY\ÜþO EmqA\"J·\Òw\îcoú\Ò#¦Æ•VVV–T\æ\Ú\Ü\ÜÜ¥¹UœˆV|ô \â‚E Ï°¾•<2\ã\nF\×ûø›\ÑA3NJc\Ù\Z\í\Z´\0m\ìÙ¹‘\áý¿úþŒ¨«ˆt­J\ç©\Ä:{\çÎ%ý\ë\Ü\ÇCfüÞ·o\ä«^CC7n\ì’aÅªaøB·u°O¥,Œ\í\ây>\'ª\çùŸ_\Ë7/>šA©m\ä\Z\Û\È\îð\È5\ìÁ4\î¢?>\×\\4™§~u-\'ÕŸ\Ð\Ñ\ÆSF´o5\ÂRóùü>\àDnj¿@=2©\âcÄˆ”••%;8b ÷…^`ò\ä\É\är¹\È\îœüUŒ’÷„±q\ì\Î\0i\Ûzm\ÔWgø\á·?\ÇÿºòS,Z¶‘MJÕ¥˜t\ì0¯«B\èÀ\Ë7£%ƒ\ï”­0: =\ÊËªX´h|ðA	@\r0aÂ„\í\rð\Þ\Ôx_j2aÂ„’½©J)©­­•gŸ}¶\Ë\ç‹\Ë\én¯g÷\Ï=\'\"\Æ\"aVL“ \èfó©\ä$ô\Å;»-wÍš52n\Ü8Dk[\ÒRUU%›6m‘ \í\Þ\Ô³m›/¼%K–”\0Õ»w\ï\æ3ŸùgŸ}6§v\ZÃ‡\'“\É$’§\×H¿Š£¡h§S(›\0¶a|\ÂA4 4J9hÕ‡ŽŽv^zùx^Ë±\É\å²4\í\Ü\Î+¯¼Ê‹ó^¢©©	Û¶K@÷³\Î:‹Áƒ\'ùc½¡\í¢ö}_\Â0”-[¶H]]\0âº®h­“\èò˜9s¦ˆˆxž\×CI5\Ñ.jŠ$\Ç3FòF\ÄH(&\ÌJvˆ1y	\Ã@c$£½\Õ\Öo­œn\Û\ä8NrŽgÞ«¯¾*\Æ\É\årûY\ÝIj}­5AP__Ï£>Juu5ž\ç•l\æŠwÿ¹®‹\ëºd2\\×¥ªªª$—\é@)º7\Â¤rP¸JP¢A§\Ñ:ƒR.ZG LœˆfÙšº~µ…¶”¶)I8\å\'?ù	§œr\nAôZJ{\åû[–…\çyœs\Î9¼ò\Ê+ü\à?`\îÜ¹\É ºz+D¹W\Ð¯%\nI>ª`\Äh–”\Þ\Z×¡T´yn÷\î\Ý%Y{©¶¶–)S¦ð\ío›3\Ï<\ß÷?\Ö+•z½\ã/~\ÛÄ¸q\ãx\ä‘GØ¾};Ë—/gÙ²e¬\\¹’\æ\æf:::Á÷}úõë·\×u`\nñVžhó‰*½F’¸’|ˆ\ëI¥RL:•öövD¢\×<UVVR^^Îˆ#˜8q\"\ÇsÃ‡O\ÚYE\îghÃ¾€BW’\ÔU86^€D„0m\Ú4¦M›\Ömea–$ôLb%þ\Ç\Þ\Å\×,g¯³P°©Ÿyæ™,=\îGqö^WÑŽ’u	½—ª¸ýb\ÝO³\ÎûT\ãnq’0”¾\æPQ\ÜQË²’\\0 ÄŽ\ÛÇ º\nuÛ©«W¿Q¢8“¸§\Ô\æ\Z›*ÅŒ\îN\çl*–²xÀ‹ƒx]Å¦zS~œ\r˜\rx\'Oüw¤CÝ¯¸ü\Ø&·!-ZT’Áv(§\æŠ¥\Û÷}\\\×e\çÎñLPO‘\ÞNû¿\Ò^!¬®®\Æþ+3[ÿ\ß\ì÷ÿ\ïDÿe˜ú_E\ÇGaö¿\0Û½û\ËK\ê\Ê6Œ\ß\ÄS¬¢\â\Í\Å\åu.søÂ¡ ¢\îŽ\â\ä\Øx‡‹\çy%[ûô\éƒm\ÛI\ÆvGGGb\Ç\Zc8\Îó<lÛ¦²²Ë²\È\çó´¶¶–ì¸‹©8®\×—\ç“Æ¯\Ì;\Ô\Ô-,öqŽ|PÖ®]+Ë—/—±c\Ç&\×\'Mš$\Ï>û¬lÛ¶MZ[[¥¹¹YÖ¯_/O?ý´Lž<Y”R	—N§\å\æ›o–Å‹Ë®]»¤½½]¶l\Ù\"s\æÌ‘s\Î9§º1b„¼ó\Î;òþû\ï\ËÊ•+eÊ”)I£F’÷\Þ{OÖ¯_/·\Ür\Ë!\éo|\Ô\Ô\Ô\ÄôÁ;\âHÀs\Ï=—\à“\'O@†\";w\îL®\ïÜ¹Sv\ìØ‘|ÿ\êW¿*€Ø¶-òüó\Ï\'¿yž\'Û·o/Á7¯¿þú¤\Þc=¶ä·¹s\ç&\Ø\î˜1c’\ë?þñ9SºNu_.—\Ã“œ¾ø\Å/Ò·o_\Ú\Û\Ûù\ÊW¾Â°a\Ã=z4gœqw\Þyg’|7\ß|3Ó¦M#ž{\î9Æ\Çð\áÃ¹\à‚hhh@D˜1c\'œpBR·\ïû‰Ê˜:u*\çž{.y<\í\í\ícøñ\ã\ÒG\é\åC\"©O=õ”c$›\Í&’z\çwŠ\ïû²g\Ï9ýôÓ»}¶¶¶V6o\Þ,AHcc£\Ô\×\×\'Èµ\×^›¼\×t\æÌ™È¸q\ã\Äó<	\Ã0‘þ…\n cÇŽ•|>/\Æ¹ÿþûJ?‹ß¹Z|I\íŠb\é}\ã7°,‹\Ê\ÊJ\æÍ›\Ç\Ûo¿Í¯ýk®\Ä&Ó¶\0\0—IDAT»\î:†ž\Ü7h\Ð ˆRŠeË–±u\ë\Ö”\ÑZ3þü\ä\'cÆŒö‚BJ)fÏžÍ²eË˜<y2\çž{nI>\ÂÁrtö\n2¦Ch1\ê>w\î\\\î¹\çòù<–e1~üx.»\ì2~ü\ãóú\ë¯sþù\ç\Ñ+\ãc“(~­hŒ\0chiiIÀ\ï\âwøÇ«ýºuë’­IÿøÿHŸ>}’i°À•ýÁ£‡Œ©]Á€ar\Ë-·0i\Ò$¾ù\Íoò\È#°n\Ý:|ß§ÿþ\Ü}÷\Ý\0‰yƒ\Ì\Åe(¥(++£¬¬`Ÿÿ\ïE)Euu5O<ñ›6mâ¤“N\â²\Ë.K^Oúq\Â$\Å\ÔyCZ1rI-\Æ2cF¬X±‚x€\éÓ§3v\ìX–,Y‚1†¾}ûÒ¯_?6m\ÚDSSS2½û÷\ï_€Ÿxâ‰‰MºjÕª¨#EÌŠz3f\Ì@k\Í5\×\\CEEEI»>.ýE%µ3r$!Š\Ïþó<ò\È#L:•þýûS]]\Í\á‡ž0!Þ½\Ý\Ò\Ò\Âc=†Öš¾}ûò«_ýŠ#Ž8‚t:\ÍÔ©S¹\ãŽ;ð<¥?üpRO}ˆ¥÷\ÑG\åwÞ¡ªª*ùý/\ÌÃ²,QJÉ“O>¹\Ï\ê\Ýu×•Ø¨6ll6›\\›1c†@d\ÐWUUÉ‚’\ß\Ú\Û\ÛeÓ¦M%¶\è\Í7ßœ\Ô;v\ì\Ø$›\äž{\îI®_|ñ\ÅbŒIVÿý\èG‡\ÜNí–©ª\Ó\ë\æ{z¼ô\ÒK†¡´´´\È\èÑ£“Ž?üðÃ²j\Õ*immcŒ´¶¶Êš5kdÆŒ’J¥J\ê-++“;\î¸CV¬X‘Ü¿c\Ç™?¾|\îsŸØ›®3j\Ô(yÿý÷¥¡¡A¾ó\ï$fm\Ûò\ÔSO\É\æÍ›\å\Ã?”\Ûn»\í 0¯;\Þ|$Scð@Û¶\å¸ãŽ“»\ï¾[ZZZ\Ä#+W®Ë²J\î\Ëd2R__/&L#FH:\Þo¹\étZ?üp?~|’\Ó\Õ\Éd2R^^.™L&¥”h­¥²²R***’ûO‘Ô˜I=)Ð²,Ù°aC\ÉýÒ—¾T\"5®\ëv[WW\í< u=ˆ\âÄ¹C\ÅÀ\î˜\Ú-J\Õ9\rò£(\É,[¶Œ\íÛ·³f\Í\Zz\è!^~ù\å\êª\ØûquE\ÅK1Êµ_\ã»\è¾\âˆjÜ§C\éH6\ØtÕ°ž4 nx&“!Ÿ\Ï\'*\Î\nüŸ@555O\Íf³À\Þ\×-ýObhL\Ý2µ8ù`\é@1uþ\í\"3cú¹j¶\Í\ï\0\0\0\0IEND®B`‚',1,'superadmin','2013-05-25 03:45:54','admin','2015-05-06 15:30:59','sdf','For Test','For Test ',0,NULL,NULL,NULL,'0',NULL,3,0,1,0,'',NULL,NULL),('41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','http://cas.demo.connsec.com:8080/demo-cas','HR','27d258510c99f7f9b3301292b11d72c4','CAS',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0E\0\0\0@\0\0\0L@ºš\0\0\0	pHYs\0\0\Ä\0\0\Ä•+\0\0\0tIME\Þ\Ù\Ó\ë¸\0\0\0tEXtAuthor\0©®\ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬\Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0À\æ‡\0\0\0tEXtSource\0õÿƒ\ë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨\î\Ò\'\0\02IDATxœ\íšk]\Õu\çk\ï}Î¹·o«»õlI	#@Œ0Œ\0;€x\ØøÛ”q\Æ\ã›xj\ìrò!±S\ãq1E2\å”\'¶ó°\'N°S\Ä\Ø~F\áƒcF„dˆ‡­V¿\ï½\ç\ì½\Ö|8·õ¢\ÉL>\Í|\èÕ­\î>}\Ï9ûü÷zþ\×33\æq\Üÿ\ëüÿˆyR\æÀ<)s`ž”90O\Ê˜\'eÌ“2\æI™ó¤ÌyR\æÀ<)s`ž”90O\Ê˜\'eÌ“2\Â\áfå•¤\n\âP‹D-\É] øœd\Æ\Ó/¼\Ì\ã/\ì\æ\ÎGvð\Äó/±ml†‰½{1\0\ïÁWýKŒ“9e\Íz\Î\Ýtg»Še‹‡03œs¨h\çJ{¹Ë¿\î\ØIŽb*˜\Ë\Ñq*´cbÅ¢~\Î;i=Á;0¤^¯‚TV‚AŽƒ13]±u\ç³l›\àé»™\í\Z9‚\ÛÓ¼\é\È\å¬^µ„\áo^w,}€•˜!‡‹L¦´\Ì\Ô!\Ï<Ž\Û~õ4\×\Þq??ú$»^\Û¡	yQ¯Ì¹ú“ˆ Ò\É8$…ŽBjs\Íx\'W\ìC”1\áE@g \Þñ\Þÿþ-n½\í.( 	ˆf@ú`¦\ÃI§Ä½_ú†r%© \Îp\êH.QughHòÀ3{Ç¹n\Ë\Ý\Üý\è\Ël}n\ãcc\à2 «Ù³^ 3\rY\Æ\ÊEýœ|\ä\n\Þû¶Ó¹â¢³hú¹,EgŽŽ¯\È1úó&¿~ò9®¾ñV~xÿ\ã\Ð\Í wø\å‚¥>\ÕV¢Z_\Ä	yJDŸ!8X8€Ž°|ñ0fµ5Šs˜8\áž_>Æ½=ˆ_s&UMjRð¯©Õ¤\ìL3>1\É\à’~À FŠ,§+¯~ù£\Û\Ø5:\n)ƒf,\è\Ç{‹†Š &ˆ¡µXuxy*òòc/ò‹Gž\â§­\ç\è\åKjR6APSZ\Þq|õŸn\çškobo7‡Á%¸Á.”*],v\ç15!Np!Ã’\Ò\rˆ”xš\Ä\ÎG.mpÑ¦ \à\Äafh¬\èd\r¾ò³Ÿ31Åº%h†w\É’jŽ‰±Š©ñid\É\Ì‡bxŸ3\Ñ5>ùµorý\í÷C>@^,\"‰Ô¤%E;\Îy\ÈUCºj`¡¹\0­f8{\Ó\é/^HJeMŠˆ\0µŸ¦¤x/\ìž\èðû_ÿ\ß\Ýr,ZBh*©3Šf8ª$ó9\ê<I­v˜P5AE&r4ux\Ë\Ñ\Ë9jx€#Á9ª¤dyÁýl\å\Öž\Ã÷/F\ËI‚S¢oa±ƒ\ËK¤SaY\ÎHµ}Sû€\á:\æ™ (3eÅ§ÿü:®\ßòÜ¢aP%•3¤°€\Ã!\Í&•y\Ò:Þ¹\ÌH¤\Ò}óO­<§\ê´kRRª™oS\Òòž\Ý#3\\ú§\ß\æG¶\â— e…fr¥\á\\gBU¶!NC£ høFm\Ó\ÎeT\í.\ÚñtªLs\éo‡%#Š’\ÄbÄ²ÀŸß¼­fðE‰Š¸Dr\ëxLG5©Œw3\ÐT\á\\‰M®¾\é®\Ûò ~p1)X\Äû@(\í\ÏÑ‰¨&€‚®Hòi4!Áª#Vó¾\Ó7¢fH½˜bZ›µ%Ú•\ç¿ñ=xð1ü²eh\ÙF-B	\Ï<©3ƒj\â­Ç®á‚“×²y\Ý1wÜ±.ðÄ¨\äž™`\Û\Ó/q\ßö\í<ºó	\Î|ójshªð‚~ô~ö\Ød v»¸F?\ÝN.‚\ÄcXm\Å\æDÔŒ\Ìg<½s„¿¾ù§Ð¿€d\nš@ŒD†}\è\Þ\Ý·t	N9™£aQ\éZ\âÕ‰O<û\nÏ¿¸‡™©)Þ¾\éŽ$¦D\Âˆ)U\ê\ÒÌšüñ÷o\äº;ï£¹h\í\ÉI(bø\àIcc¬\Ó\Z>sù\Å\\ú\ÖSje\0(u¦P\ÎX3\ÌkVð\ÑwœÆž\ê\È…”Ž)_ÿñ\í´\Û>¤\àIVBAM‚ºÚ£]/\æÏ¶»0;uŠH\à†Ÿÿœ\é©I¤±\ÓXg—\Þõµû\ZŸüÀyü\á¥³z\Ù\Ð\ëj‘ß¼6ÁS\Ûwñ\Ý[o\çCçžŒY­¡Q“Rv»­&<úöýÛ¥+°Rñ¹\'y V\äE“rd\ï;\ïl¾|\ÕGX»b¤SU›\\Z8zÁ\ÍtDqUM,:’)ª‰,/¸k\ëvn}\è	\ÜÀTd$BY\âRh\ÒKUuzv\ÂÎ½\ã¤ÁA•{ž||˜a\"u\á<63\ÍE›Ž\ã+Ÿ¸œ\"•\Ä\éó‘\è#qô\âÀÚ¥\Çs\Ñ\Ç!¤I|\èG\ÍjR\Z\Í&¥\Â\çÿñöuúðY¤›f°,«pyFù\Ú88÷\\þ\î3ÿ‰¡\ÂQµg\Ð`8\ß \ÐK¡R?„`–7À \ÛI\ä™P‰’‰‘¾ù\Ã{\éVž\Ì+\êXûX¹`O¾¶·\Þy‘\ÚRœc\×ø8f¹F÷FöŽ\ÕÁ\Ô\è\åg©“†%\å¬\r(\Ìh—3\ÅB¢\Ï®NIõ¥.™\æˆ(\É	˜\Ã[¯\Ìw\Þó£û\çŽÇ¶!\àº3¸žo‰\ÏÐ™6\'³š/~ú\n†\ZM\Ðx_Ð§9ˆ .\Ã\È@2Lr\ZA\Èr>\á¨ð\Þq\ï\ã;\ØòÀ¯¡¯…j\Ä4’`²\ÃI\Ç,\çÒ³Î„n¹\ß\Ìj+|qt’¤	\È\Û7Â¾}£€Ÿ-nAÁT\áùW\Ç0\ç0—Si$:«¡‚ 4Ã¤“\0\âA\Z5¡ôH\ét:\\ÿ/ÃŒ?M\ÕpˆH	¨GÈ¸úª÷²v‘§J‰\Âed\ä N\ï\r\ç­\Þ0\'u\à\ãÀzSrd*t\Ôóõ[\îbº[\áD\Ñ\Ü8\np\r.{û	œ¼jp¶B¨\ã‹8áµ™D7)f\ÒP\Â`Tk\"\ÄpN\êSŠŒ·ü7>¸¾F‹‚„hD\\]¥+†\ÈTI\à=ž/‚óR“²\ã\å\Ýüò±­H#G$Œ\Ø\Ø\Ä>Þ¾açŸº”\ÞÕ…—aõ\Ï^…:\ëþ®·³\Þ{œs¤”lc(øù¿>\Ç-¿z\×,(KpŽ®Žq\Ô\Ê®¼ð\"B\æ \Ï@ô8ˆP\Å6;_ÞƒYb\åð*‚\ZÁ{\Ðz=\Î{\È\n&ºW~\áü\Éng<9šAP*:\Ö!\ÒW‘ˆûcûŸ\ÇjR\Üþ¯ŒŒ\ã›MÔ´\ØÀ¬\"kd\\v\æf­*D\ä\ßü\Ô)´Gƒ*\â\Üþþî‡·\Ó.+‡K†ó®\îEÚ“ü\Þ%o£\åýÍ€ø³Õ¶e\Õ\åù=£8\ç)2c\ã±+!– ®¾wŒ\ÄCBdJŸ»ö&>xõ_p\ë¯v’ù&M\ß TŠXQc¶t…¸\çñ÷¡(X\Âp850X\Ð\ß\àÝ›6b1bÁ#‡œ>7D¤¶\Z\ç¨Û“ˆw»Ÿx™ŸÜ·\Éû\Ñ,\Ð\Õ\nU;r\ì\ê5|ð\ìÓˆª¬\\¶˜f–\Í\æø\ÞO*±g¼]\ïh>ù®³Y>P«ˆs—\Õå˜â­ƒ³\nZýüó\Ög¹ô\ê?\ãª/\\\ÇÖ—ö\á\Z-P‡F\Åõ\Ô\é­[¤\ç>¿~öy\È,õº]3\\!*+–.fõ\â–J\Ô{\Ì\ä\r¨\èml\ÏÁHªL:vùö-?e:	®\Ê1KX#G“aU\ä\Ê\ÞÉªe‹i[\Å\ÚÕ«È²¼Wü8ˆƒJxñ•}\0t;]6¬Z\Îg¯ºªš\ïÁ\êc¾!øþ>º}\×\Þû ~öü\Õmw\"¡ \'@RL8$8€g&º=Ÿ\ê‚P¡\Ê=T‰KW\âœ`y Sw˜¡½¥ ˆ•TNˆšð÷\ï\Ø\Í\îÿ%ô^µ:jb\å\Ê{÷©˜)\r—!Z\ÑjzNgÈ©p¡Dž\Z­\ãK\æHQø\Ýw\Å\ç.»€4>FR\Ë\n‰¤†¦\n¤\"\ÅpJ\Ñ\ìc÷x\äS_»Oÿ\íM¤ƒ$TJe†YªIiOO\×MœR·±˜8\ÐÄŠ\áe=\\Ï¼þO„\Ôq%#˜\â\Å/|\íŸn£\Ó.p\ÚBQL\êŒ@9\Íe\çnbqAŒ%•‚x\ÇQ\Í>(B?)\n^=d]¦ª1\Ä\"Áw1’\"t\å%ü\Í\ï_ÁpšD\Ç\'±¾Åˆ4ð4ð© \Ä?¥˜E|Pðµ\ë¾Ï—o¸r¨Œ\Ì^u\Ìj\ÅL\Ó)!½t²ph˜\Õx¿kü\ßÀ!e\"øŒ»ž\ÚÎz\Z-\Ì\'Œi-\Ëûó©\Íg’‘Ñ \Ï;2\ïlõC\'¢¾Irý¤T 2\ÄK/—LNu)¼”¤àª’O¼\ç\\nú\ã\ßã’³NÀF^\Ä:“¨÷¨@–\åTV‘¨ \Ëp‹Wp\Í?ü„?ò|‘¡•aZ\Õ9\0\Þ÷ü±÷ÀÁƒ«-b\ï\è\è¬Côö\àtüú\ÏlQ¡ˆº8þú\'÷2=ñ^A\Æ!W\Ä\åÐ\áò÷žÃ±«—1\Ã>v—/ó\Ì\Ès¼0µ‹\æÊœ0¬¸\Æ(\ä\Ó$ºX\Ùój‡½\ãb9¦‘”*±SrÖ†\ãø_\×|šo\îw8e\í\"ljf%©™Ó‘ˆùº]°ªBò\í(|\ã\Ç?£]V8*	›-óIŠ9©](„\Ú2ª\Îñ\â\Ï#\âd)\Òû›\Õ\Ù,e=^#y^ð\Ëm»¸\åO!\Í¤2$/°ª\"u„e+û9ñ­9\ß\Ùz-;G·1+T\r\ï§Xµ¡Á\Ç7\n T{•\í/Lñ\ÂHAw\×\ÊN‘!ŠR­ ¤\0e*iX\àŠ\ÍgòŽSN\à[·\ß\Ï¿s“\íi$óXŠHRBpÐ!5š\Üÿ\Ô\ìœ\Ø\Çú%\ËHªx\ëÉ‘!Ñ²ºwH]\Ìe8/(\Çv¿B\r1!†DHqµ:»‹PiûYB/K}\å\Æ;™šš@2’µ°*\ÐRN<uŒ68{ñ\ÆZ¤Ê¦ð¤J\È\\Ž½–óË—Œ\ÎøYªb9?\Ð\æ\Î\Ý?¡o\á\é¬:‘ñXRL|½.…Ø‰µøoº˜£—®\àS_¿ž1\í\".€sT±\Äy˜2>Vò\ìs#¿x)!ehˆ5)k–-\ä7¯L\".IE\Ù¦\Züf\Ï>¾w\ïC|tó&:\Ý6©\Èpx„º&%$hÏ±@0RŠ\äyƒ[~„\Ýw¾	jM¬=\ÎQ\ÇTœñ¶&«ŽPH]RJ„\ÂHÚNÁúE\ÇqÁ	\çs\ß#ûø\Ú7¿}CP	„)ˆ	l†¿u1{FGØ¶\çvN<rK[G‘bI\æòºœ®\Îz\ÝI>¼ù-l\Ù\Ë5\×\Þ\0­VO6\Í\êI‚dM^\Ù=‚\0ž\0TuL9k\Ý\Ñ01†¾¶\n\\m\Æ\ä”1ð¥[·0\Ò\í\ÐpM‚Ó„WCTjöqu`5©\ï\ée7ò¥Ÿþ‚n•(\Ìa\Õ>N?kŒ÷½?²b\Í¨t»	‘®\ÍÐŸ†¸ô\äð±\Ó>\É	Na8_ÝŒ\"/ÈƒC¼#oö\Ãtö\ê\"6»™#W®\ç‰\ÝO³sbš\'¢UÄ¤8 “€\å-’*—y|]óH\Ö\Û:\ë	^Dú†š€¨=\Â¼ÿ\Ì\ru\Ó$\ìS8°ªDúxb\Ç+\\ý\í\è\à\èß™F«I’Îˆ\ÄdTjD5\Ô9œübÛ³\Üõ«pý«Q™\äô‹\àœ÷‘\rd´K¥#;±\Ö(“)£\Ï-\ç·7|ˆVü\ÅTš±¸U\àûU5¾Ä¤$\Ù(4fx\é…@\ã\âŒ5§°wr”]{v†®.’Q™\áE˜\ì´ñ>\ë)sô\á\r\æø£Vab˜³^\Þ\ÎZwo>~-qzB\Í`WÁw±4…0À\ß\Ü|7ÿò\ßò\è\Î1´5@h, \Ëû>O<yVë›¢\Ê5ÿx¤	œ$\\\Ã3±§\ä‡\×+7~«Cxu\'¶Ö“Æ”en9Wœð	Þ¼\äT´c\ÐTœ(,£\Ñð¨(\ÑhŽ\Å~pƒ<7²‡NR\ÊX2/\ç­Gn\ÂWŽ\é\é¢Fº©‹H¢™y&þ\è\ïo!õdŒ\ÚP\ÌCJ,]¸ˆE\Ñn\ÝÂˆ\Ô1e\Ù\Âÿñ\Ý\çó«¯_yT.T^\ëÖ¼ú‡ù\îó³G¶s\Ñ\Æ\ã¹øÌ\r\à¼\'˜’F§\Ú05\Âð’E<ô\äó¸‰:†vŒm{ˆ-ú]\ÉeŸü§4½t}…q\Ò\ÂõTI±¦4‘‘hö1\Ð\Z`ztòf\Ý<ZD™a:kp\Ã\Ý÷ò7?\ÈU¿ý6,]À‘kŽ¥UH…\Ó]\Øö\ìsüÅ¶p\ë#\Ï\àúZh\ê\Ô.À»’4\Þ\áœs\Îd\é\Ð\ÉJ<õ\Ø$\0h\êr\å…gpó÷s÷SOk	¡tu:\Î:¤nÁ\0{c\Å÷\îz˜\ï\Ýö\04[„,%¥-\n#|þ—ò\Ô/0½ošl\á\0šº˜¾\åI>x\á&\Î9ñ8’Vœ»ú|PP”\àQP\Ë1sô…ðŠË‘\Ô\ÆI$Y´\ä¥Ý“d}\r¶<ý[®þ*K±fõ¬XX\Ðp‘Lã£‰Ÿoš‰™\\ˆv_KU	y¤¯ð\\uÁ\é„ª”‘ùº=\ì8¹\ã¯>óQ.ÿüWøõó“d-š¹P)T\Ú\Å9EÍƒk\à|Ð„«ó†„6o9\åN[·–?ý\â7a #¥H&\r*5R\ÈÉµ\Ã\Ç.~–f\Ð\ÑHfõXCguV@\Äh5\Zô÷5!N@ ž\ìV029w-™²ÀHeŒl}b=\×!U\Ðh5qCƒhU÷_\âr\Z±Kô}töNò;—l\â\'MJõ¦\ÌVë½¾9P\ÎL²~\å\"~ð\'Ÿ\á\ä5+©\ÆF™\ÑD-pˆöõd¼Dª*\ÊXQY¢ô\×\èÃºS\\qÁ™\Üt\ï#tÛ†+§‚• Y¼\ëÔ·p\Æ\Ú`\'u9\ï¼\ÇfõAU\é_P°d\Ñ@\Ý\å9¨‘,AŠL·;ŒO3\ÔWO÷\n›À7•0”\áõ–-&+–\âR-\Î\"Ft\Ì\äM:{÷q\áÛ\ç¿~üýX¬z…¨*@‚\ÌS•m\Ö\r/\æÿów¹ü]g\Ó\èLa£û@r’z|VKv>ddEŽóÉ•\Îø§u<+VsóC’\r6pI	.Ã‡\çJš>òŸ\ßw>ypDb!µ¶\n\ëU9³q0%\ÅÏ¢M|U\âDEŸò,§c¦\ÝfÕ¢¥H‚¤\r¼ôc1C;‚–U‡>¤„bJtt¸`#\×~ö\n–\r4Iø:\0TšzY¸#\r2 •‰\ã–4ù\Î\\\É=›\Ï\â+7\ÞÁOn§c3t§ªZZž$\Ô\æ\ÚpPE.=}3\×Ý±…‰WFaQ€²\"\ÎöŽcûxÛ™\'s\Þ)k©bI\å`‚Ÿ`\Èlw]/nV@öAH\í6iŸ\ëMõrR9Cù\ê%ƒCýXY\áúû0ø 8¯\à±iÖ© [-[\Åÿ¸b3Ÿx\Ï\ÛñN‰1‘9\ÚþV\ï\0)\Þ{úöQ\Ìj=dó\ÆulÞ¸Žm»F¸\ãxø\ÅWØ¾gŠ©\é)\ÄyD„XU±`§ž}4\Ïüð>xÉ¦^à¨²@LE\Ù\á¿\\r1E\Ô2<9\âzD˜? \î×¨©\"®[\Ïø;§p\rƒ*y¢O0=Î‘G,g\í\È}ÍŒ™N¦¦\êI§¯I$ƒF\à\èe9qx!ž}\Z>\ï–´š½\æÕ‘…Zo–\Þe–±\ZM	\êŠ6’\Ð*’‡¼ö¹\Ùÿ+ŒOL\â{RC,KúrO\æ¾¹Ÿ\Ú9P¡1!¡\Ú[ˆ\Ø~©UGý\rM\Óø\Ð\Úö^\ë²\ïµIvŒM±gb†ª¬[\ç\ë\ÍU\r\Þ4¼Œ£V,©Ç°@J©Ž\î\0!‡w¶ûI¡W\Ý\Í\Î=¢Õ£4õ›Pu8\ÉðÁõ2œP#VµaH=¨«_¹PA¨™82\'=\âRrÐ½{Ë¢£©.¸T1|=Á³Z@Eó I\ãc—¤BÂ\î~ÿ¨ý,³¤²=;£7Nõ¬\Û)j]\Ü!ûeÄ¤ž©ˆõ\ÎJ/xKd,G‚ ž\Ò\Ã\á\ÐÞŽ¨AL«ú \Ì9\ÔÕ‹\ÕZ@Jõk\É\×\"»\Ñ{.®—\â³Z8rBf\à0a®\Û\Ï\âuo2‰)\ê\\ýZ–\Z<\nxó\àuJ\"\á©\Í4¡8œÕ³\â:(@ˆR—\è’pAMô^%x\ÃÅ¨\Ø~×ª|Âœ\â\\BÌƒ:*dj˜xÚ¦\ä\â\ÈEðN;0gNZ[®‚ˆ\çP‡sXJ\ï\Ï\Þy&=w0Á\î~³_m<gu\'g½÷\Ö\0\×{å«·HÜ¹\ï\á˜\Õc\êZ¥g…R\ß[¸¬§w9zcRo\\zóg\éÍ¶\é½ù$ˆ8xÉ¿ƒ”×¯tö*ûüa9\è¡z\Ò\Óa\ì\Ä\àþo¼+\Ïyù\×Á\ç\à¯\Ëa÷\æÀv{ƒ\ã‡\ãu\î\ÃA¯5\Ô+˜ý]™¤üØ‡;ðÐ‡\Ó8»{o`)=BfS\ä¡g°„ƒIŸ%\ëmC	\Ø\r¡.7\ä\ß\é>ó˜\ãzNÌ“2\æI™ó¤ÌyR\æÀ<)s`ž”9ð¿HI±’zo¸\0\0\0\0IEND®B`‚',1,'admin','2014-09-16 13:56:03','admin','2016-10-11 20:50:57','','For Test','For Test',0,NULL,NULL,NULL,'0',NULL,13,0,1,1,'org.maxkey.authz.cas.endpoint.adapter.CasDefaultAdapter',NULL,NULL),('525d261fa3b04d19af0debabbd5a1e2d','SalesForce     ','https://login.salesforce.com/','SAAS','e8297041ca3347987bc739a2c9f33e7d4909881759ea590b0c091d4f645202e71e698f87c64032dc548d6ec7dc3c4863','SAML_v2.0',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0O\0\0\0@\0\0\0[b*S\0\0\0	pHYs\0\0\Ä\0\0\Ä•+\0\0\0tIME\Þ&\ë\ëR²\0\0\0tEXtAuthor\0©®\ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬\Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0À\æ‡\0\0\0tEXtSource\0õÿƒ\ë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨\î\Ò\'\0\0 \0IDATxœ¼÷¯g\ç}\ç÷z\Ê\é\ßvû\Ü\é…\Ãá°Š\"\Õ)S–%[²½\ëU\ÖF^`±ˆ\Ý\ä§ý!	`{ÿ€ A€\0\Ù €ƒ7X¯¬µd­Š©BI¤š%±‰}È©wnÿ\ÖSŸ’\Î÷\Î%%6ýÎ½s\ïýžö~>½	\ï½\ç`\Íÿ\ç8n.‰C\Üúˆù\Ä\ßòYÁ\Û×­«­C\Ä!\Âùù5-Nx¼·(wË™B‚8©ðH@¢\Ä-w™?‡ý…{‰\Ï}óMü·i¿\ßüú«Ÿý\ïZú\Ö+[\Ó^\Í\npò\àf…C9‡‚ö)n+H-€E1%‹¤\ÐTuES[d\"•\Â	Jb¬CXK$\à”%4%\"ðH\åÀÀ\îë¯±´~’¢™f˜\Æ!¥jw\Öqc­†FA5ÿ•4Eƒ\ÂÜ€­¶¯4’\Ä\Ï/ \áA	8\Ø3ñwC©oýAÍ·\ÏË›\àµ7p,J¨v«\Ü|›¼lùzI¼\Å6†(‰B\Z ²5\nE¨$¡’(g!Ï¡jÚ›I0†kŸ~øc~þì³œ»÷.>õ\ßüKXYÀ\äu@€<\0n^£¸‘ø…|û¨\Í?\'ð8\Åõù·“\î\ßc½òð€­@K‘ƒ\Ç!h¤\Æ!‘\n”\Ò/n\ÜL:\ç;òS\ÔL\ë’R8¢^B B\Z\n*[\â\ë†ô\àó\Ó	\\Ù€-žü\Ú7¹úò«Œwv\Ó1ûe\rXB ±\Æ`”EF\Ì7Œ‚H œo¬@€\×  ðHœPhÀô \ì,xZ\ã\ß\ï\ÞO8¦½š„@\ß\Ø75q.shYCù4y€mÏi€\Ç1mfL¦›tM\Ø4Dµ\ÆÀõ]\ì3\Ïó\ìc\ß\å\â“\Ï\Ò\Ö,UŽ\ãI\Â\ÅÖ›?ö½\Õ\Ï\"³+\Z<\ZGˆ\íŽ;\á0$‚\Ð\Ë\rYŒ8h¢•\ã¼\å\ÂÎƒŸÓ¬p n=\ç‚g‚\n!<B¨[XR\Í	\ì\íD\ê\0\íi¶\Ýxj*l¥I‘O°uE\'	\é\nOW\Ø¶\Ø\Úfò\Äy\æ¿|§Ÿ\'«j\Î$=V\ZM\ÔxDU3” k\ÇÏ¾öU>ú{iN\Å\Æ	‹—­\\\ÄY”0(ióm¾)ö%\Ð>»3IÝ€\Ö\è+\Z\ÎÌµ‹|\Ç\Z\ã\"˜‰!\Ò+B´²\Å	¤\Ð8\"XqSg\Ì1»Áò¨…À ê’¨j\æ\Ú\ÔB^Ã¥\r\Þ|üqžú\Ú\×¿~\ÃRs÷`8/ñ{û\Ø\Ê HUŒV6Žx\á\ç/sõù\ç8rú\rƒEx7\ßA‡\Âk;—c\rBh\à…\Ç	»\Å\Z²\ÕöÎ·\Z\åR\Í5ºä¢w<\ÑÊ†X(„Ph¯À)ð-v.]­hFÂ£% ±L¦;„µ#\Íú ll±÷ø÷¹ø\ì\Ïx\ê[\Ñ\Å\Ñõ–µ\Î2IScó\ZcQ–ô\Ê8”U4£!vG!Š§ýG~ý‚žkw\ËûÖž2$$\Ò\ãA#^û¹)\â\Z¸i²$\ÔME^7x$*H\Â7‡!ä¦¶}G\à	$!A„$@ÁÁ„: ¸¹¬ñ8¼0Xe‘\Â!½…YÁ 1\à¼y™×¿õ8O|\é+\ì½y‰µ$!6o\Z&¦bfj”mP¶A{K\ä\n–”%­\àt\àˆ…\àH\Ö\'Y_\æ«?}–\êÕ·HV˜\Åv\Ê\\69À\n¤£\r7\ÔC\à-š\n\á(AXM™M&X™u—a«\ïQB ßÊ½žB¢¦ž4MÁŸ-{d\Èv£¥‚i1D‚Tk4\ïršªDMtÀÞ”§ž\åñ¯}ƒ×ŸyU\ZºQÂ´2t{}–®‘ö3zƒ.NX\Ê*\'5r²C°}+¿Àz\0IPN\'ø«’Õž\æ\é/~\Üý.\ê\Ù>\Ù\Ò\Zª\Z“O\ÑK=0†¢iqB„GaÐ®D\Û\ÌšÆ»\\x\é%j6\Ç5\ïùµ\ßF§K\\\Ù²¶0hu\Þ\ßÃ¾û%ðp\Æv\\£´Bd	(f3j×v#Fûû¬,ôXÆ»W ®H»9ù[W¹øü|\ïËòôž¤Ÿõ8w\æ,Ç\çø©“=}’ð\Ð\nZDC\'<\ÔDŒ·\Øú³ÿ…+?{ýI6ŠnØ¥\Óð\Êp‡‹ÿ-\ïùù«,<x?“\á>a\Ö%\ê&\èXT\äeA·»HƒEºŠP¸V#\Û\Z®_¢\Üx“ý«o¡ªŠ(\ì°ô¨¶®‘\r\Ö\éEÎµ:\ã,}\à	ù° $ ag„=ý~‡M‘\ï±ÜaªŠ^’Á¬f\ïK\ß\à\'?Á\Î\Î./¼r8\ír\ï\Þ\Ã\Ý÷¾‹{|\Ûnƒ^pX,jq\Ðj¸@ƒuPF\Æ`*6G9eBGøPR•aY±b=ÅµmžýòWy\à]÷!MI£R*JŒo¬$\ít\Îz+\Åp“r\ç\nv\ï*\ÍöE\Üð\Z‰(Y\Ê:Œ¦C¢X±ýüs,.Ÿ\æP\ï0E\åQ±˜;\ï\Ð\ÃðÒ…„º†F\ZºK}<\rž[ŒH‹L‡­¦¸¸Á\ä\éŸñ\ÒOŸ\áÅ§Ÿf{k\Ñ\é\Ð__\ã¿÷9|\è!B8z\Éh:Fv(•PS 	¬G8A pW^ß ™B°\ÔAyË¸Ü§WL8‡ ¯}û»<ð™O‘½\ënŒ´\äD¤$‚r¸‡¬-\Ó\Ñ.;W.2Ü¸„\ÌwXð9=³(¤t\'¯*vö¯2}ýe:÷.6\Æx9÷Kü\ß\r\â\r\Êó@‘W„q@\0\Ôf\ã}º\ÎCÃ³¯°ó½ñ³o~—\íK(¥\è%	k\'\ï@?\Âÿ\èŸŸ>«+\à<y]\Ó43|˜@‘)\rP;‹‚\È	”•­½5®YW}FJR\×\r\å<eŠ!\ØI|\É+_þ2\çn?‰%Ý¸C¤(§\\¹¾\Ãô\Ê5ªÉ„ñxF]da\È\Â\Ê	z¢\"®‡”ù.³­\ët»]\"%YLBö¯^ sò,J%\à^Ê»°÷t\Òê’\ÝM¼™±²¶iJó\Ø÷ùñ_|ù\Öu&¯]&¶’3ƒE.M\'©\à\ÝˆSðO\à\ÌñV	J	: \Í$\r‚‹\n\å \Ð\n­%j\ÆÑ©^¤A\à\Ð!4¢\Ä;HL\ÍR[?ù)\ç®o–™\Ùa¯1\ìn\î1\Ú\Ù\"ªr\Ò8ayu§¤qF7\ÈD®‡0\Ýbk4&\Ò!Îƒ\Ã\ál_„£g\0‹÷·ø\rB\à½ÿÿR¨²w})=ËƒTŠñ£ñƒ¿üOü-­g\ÃF¢\â„›\ÛL–R\Þÿ\Ï~Sø9L£…‡ \Æa\'\Î9‚(¢Fx…­P\Ê\ãò\n¶¾$®j\èzF¾½O\ä$0LµE\Æ`šƒ$$¯†×®\â¿ó=Ä»\ïe{:c«2HŽ,tX9¹\Ê`u–‚H°&˜\Ò(at…\æ\âF¾DIÏ¤\Ìñ:bc\ãUÖ\ßûÿ\ëW‚€5\ÈX\Âõ\ëL¿þ~øõ¯rñ\ég\èTçƒ„õ…Uù€KW¯»†\Î\ÑC,\ß}š¥÷\ÜGq}k#Y‚¨=¨•Em\ä\Äh,B	R\0\ïñ\ÜF°¤#Œ‚(\"o\Z„’$iŒh*š\Æ7Ž%\"ò\í?øü_qp+ú–WY;{\Â”‚\Z*,FF\è@c,\è²A\æSc\ÑÎ±\Ø\ë\Ð#¢4cs\ã\Z+»{\èø\Ð\\\å¶qEs ouùn_´RŠ¢((7¯ð\Ìÿý°tõ:Wþú{1Gs\Æ3hJ\n/‰BÇ†š±|\î¿ÿ\ï\à\Ü9(Žf\Ç(7\ÇÄ«KÌŠ™Fi	bE½·O¯“µZ©(ˆ…i#®¦„]˜\ìQŠš0ÉŠ€h\æ	Bt\Z\é\Z¨$²¬W6ö	g%§<Šx\ÏýL6v \ßÁ\æ%®\ÑÑ€HJ„mc”R;d`¢†|\ÊJ“M\Zl.@A/\è²}m—õu\rN\à„aPJµ@¶‘œw\à5N´û®x;RŠ¿}ü;tž‰÷\å\ëzq„\ê¥L\í§@5I7¥”\rw}\ìCphŸ\Ä—€\ÄK\Ô\Ó\ÕK˜63:qJ9\Úe¥0ÁtŠ»ü&¯¿þ2\Û[(×°˜&JzôœÀNv \ÎI¬%qi[WKxE(¾ªXº\\M¸ú\âÏ¹\í÷¶w\è^¡PY\é4®¨q\Æ#u€Šž(1õ„À7\èZ‚­	ši\rx\Çl¸C±³I¼¸Žˆ\ÕÜ‹´¾¹º|¢•™\0J)trÿû\ÞÏ‹/¾\ÆÒ‘£”o^\ÃOg\ÄV2,Æ€\'DÑ¨.¤)w=ü	\èt©\'²5ˆØ¯*::B{O\ì\nâº‚«[\Ô?yŠoýùÀMF“!M9AYË–÷\\²‚\ÌT,º†¨,É¬\"B\âk¯\æ~;5\Î\åD•\áòó\Ïs\Ûö>,/ñJ‘Wc²(E¤Y\Ðd\rõ>˜	M=$NZj\Â[ThÑ‘EÙ‚bz\á\åWY\Ï2H{8cp\è6Vci \ç1\ß6žho¥<\Ç|\ä\á\ß`\ã\Ñ\'\í\×ôµ .Æ¨&&\nJ‡¹aX9V\îºN\ß2\Ä®FjM\á$ñbŸ\ÂWôƒ\0FûP–¼õù/ð\ÚW¾Nº±I\Ï4”oeO\à=‘uha¨›\í ðŽÀ·¾u\Ç‹u–$¼˜°˜EŒ¶6¹ð¥¯pú_ÿkŠ­}’#‡(ò)Y\ä¡q\Øý=l]PW*3!Óžro‹H4LTIABI]ŠkoÀÑ£0ˆ\Âc\Úø\Ú\Ï|\ç\ÚX–p7†tH¸zŒ\å\ãgyý¥\ïð¾~ŒFiK”\r„#\ã¹\ë\Ýïƒ¤:A¤·\èP“›†‰gj¨+\Ê\'Ÿ\äÇŸÿ<é…·8›ô\è\ÕŠL…³M\Ñ\r4\"„B¶bñ`I¡	´¤Vµ5\Ä\n\"}\ZmÍ³_{”Ó¿ñ;$·ß…¯ËŒjo“\Ñ\ÅK\ì^¹ˆ™îƒ¯Ñ#	AY\Z‹g,¾)ñ…#%=S\ì_\Ý+°\ÜA&=‹F©yˆ¾¶@U*¤÷¾µ¦¥†#\'yð¡Gðý¹–4\Ú1«+Œ©\Èó)	¹\×ôœbuÔ¦‡dMu@%&\"ŸŒAIžüÁl\\x•Ó‡\á‹)\ÚVDÂ“\ÄQ\"´¤v–im¨#¨b¨C\ÅL„\ä:\"C¦aÀDÁ\ÐB\ÜU;–<\è\Í=^ú\Ò\× \îÂ¬o\\\à\ê+/R\ïl\äûôšY™S]»ŽŸ–\ÄDx€Q*\0\Ó\ÐÁ± fBµu\Æ\Û(i°8, •ÀÛ¹¶­Q¸\ÖHn\re	*dù½\ï\ç\Ø}÷±ñ\Ô9œH¨!\Íb¬„Qcqa\Â\Ìz²¤ƒf® µžP[\"$\ãqN\Ø\Ë‚fS¬\ÐK¨z\é²WSQ›#=:\rIƒ+v\ì>Zjº>E‰>Pä‘‡¨\Â\æŽN¾¾s,Ö–\×ÿ\ç7÷½fc¶ßº†\Î+/Ð… ’– Ø™YŸÈŒ¦4B¢ƒ\0\ì\áR\nz\Ú2Ù¾„\Û?B²~G„ )¡-‘iØ›\ày!y\r\'o\ã¶¼—\'žû‡\ã3¬)ªŠ\é\Ôb\â>q¿\Çþ¬$\ë/\àÔµ¡+%\Â{\" sœ!\Îz4\Û[¿÷~ºº\È3{›\r\"B‚ˆ0\ÂS;i<¢ñØ¢\ÆtR²(aAuñ²Ms¸\\&	„£VC\"ß²nŠ\"ž\æ;C\ÊÇŸ þ\Í_‡\Ê\Ó\ä-C¤\n\ÙŽlÃ¡C‡I\Ò\ê& ð1³:\'\"² \ÄÙŠ\ÈzB’T£=ôtD\âk„oó\è|]×„\ÎA\äÁyªªDýñÿñ¿uÎ\È$ƒ½=’^\ÂO|7\Ý#\Ó *‡ð\Z-ó\Æh\Ê=¿ý‚ã‡‘ƒ”Q>£†¨\Æ\á&Ž´“¢•hóqH§›\Ð_Xd;Ï¹R–l\0\ã¥%\â»\î¦{×½„§Ï±x\ç}œø\ÐG¸óS¿Á»>ýYŽ}ü·8~\çý¸•5†iÈ¦4\ì™O\'‰)vkš²¦\Û[¢p‚\Û;œþ\Ðû!\n‰³„\Æ\Zv‡{t(½ \ë¯RŽ,!ZYg&»eA(\Ð!\Öz¼iˆ{}®\Îr:\Ë\ë\èÎ€ý\á„A’ºM+6×®^\âÒ•+·(1/\èvá¶“\Ü÷Ÿ\àGÿ\×ÿ\Î ‰‘¶¤v(¼%T0\Ü\Ú Q’z<¦G:\0\ç‘I\Ü&fŒa\Ò\ä$±\"8s–{ÿ`{~¿?Á\Ô\r*K‘K+w ö\à\Èb\rL€÷w8§œnf\ä?ù?ûÁ£\\}\ê	þö\çoðð™5Ò©\ã\Ú\ÆýõŒÑ•7\á¥\ç\á\×a +§Ž\09v6\ÄV9a….B€\Ðtm¶€\éÃ‹¯±ý\Ö+¬F±\ìo\í]º\ÈÑµ\Ó\ég\Ôù”½\ÍlB\'K‰£Œõµ¤¥<!BH<©¤Š\Å~\Ì\Ó\ßz”žPD…¥#&¹!W\n·¾Ì¡G>†w%i\0N`G3d´•\ÂP\Ð0³\ï¡Ž ³„XXA­B,†\îD=º 3\Ð1dtû\Ð\é·iÁº\åŽ­s\ìû8~\ìe™³q\á\Z%F»V\×Vy\áõ—X>q˜\Þ\ÝwQ(A¸8€~‰Ò¨\Åtw:‹\Ðà¢Œ\"\Ô\ÔiD\Ø\ëC¯‡\Ñ\Épo”¦Acœ \n\êªb2FQ’\ÐÈº:qõ\'ò\'-xRH%¡C7Á\\¾\ÈÞ…Kjˆj‡Gb\ãMQsÛƒ÷!W! 2H\ã 4 <²\"\Ã¦X%N¡UQI\âD\Øú¡\ÝVø\0l.\Ò\0G\ÐM \Û!:s\'\Î\ß\ÇÅ§^\ä\âkorl\í©˜•Cöš‚S÷\ÝOtúÓº@¦Š(\Ä\é.V¦TŒ\Ð\ÔRQ)I!!\Ò\î½\ÕuÆ»\Û8+H’S\ÖÌ†û„Z\Ñô¬­e]T´žQÈ–\êZ¶µs\Úo*P‚÷ü\Î?b\×+J4\ÖIH/€ýK¯qñ‰\ÇÀ60™AQ·\ÓK †K\rm*Z\ÆQ\ÚG\æ	(¼£ð\ÚCÁ4wŒ§020U\àS SäŒ·w˜\íN\à\ì=|ü\ßül¦}ªN½mn?u„\ë¯>Ç‹}”&¬-\Ö{4}¦h\Z\"*¡)š6u¯‘D„*µ‹A÷8ù¾‡h:\Ø\ZRc¦#VcIg5Ã‘Sb\ÕM	Xyx@ij\ZÀ:Œðp\ï»\é;É¬ñhRÎ¦PŒIš’k¯<³14\r4sSGƒÓÛ’±\Ï»‚YU3›6˜™o)LA\ÆŸ\n\ê\0ˆ$\Ð\íI2ˆ58\Z\ÆÕ¼¢{)½#\'°ƒ\êI	÷>\ÈoýÑ¿\âµý}T–0™\ìr´ñô×¾o\\$´‚¨‰¦\È\Z!­Ñ®?Á3C\ã\ÐN\àjIUJX:‚\ì¯`­ Šžk`º¾`V)1\\\ßrñò&—/n·\àµ›E*hð(\ÝÁ\ÆD1|öwiD€\n\×¸|\Ì ’\ì]}‹ÝŸü¸uø…‚¢¦)¦\Ì\\ŽW%B2™Ð‰\"ºY€N\æ\n©ñ\Ô\ÆPÉš‚’#vØ§S¨À-e>\Âù1\ÝH¦”³É„¨¿ÀHÇ˜i\Îúü×¨\Õ‚…>{{3õ:›\×)¾ùTJ\è\0‹A—Š>Š\ØR\ï\èÙ†\ç\0]’\è˜(PŽ\åc§	£\í µ†\×_¦¾z(–”¶bo4ckk\ÈÖµÝ›\åi\ÂC\Z„T\Å‰B‡	TŽôw~—¼—±@¶¶‚ÖŠŽi˜¼ü\nO~\é¯\áÚµ6©\ë+‚T	š`^FÐ²)\åpGˆ$¤MH+<Jxp\rJK:qB*bLã°…!\Èz¶F#z«ôú\Z„š»>ö\ÛyÍ¡•>fs\Ì)\"\Þü\Öwa\ã\Z\ì\\Å÷hC\\\Î Ÿ\Â\ÖL„R‚1˜\é¸%€²BKEÿø	ži]g)\ÓÙ˜\É\î.!šD…t{«\Ç\Ö9~\î4\êOÿôOÿm[p(V\è\06Y\ÕPUœ][\å\ÑG¿JH\")\ÐMC\â[W¯q\â\Ð:\áú\ZØœ&ò˜@\nMd5º’ˆB Ì¼XIZ\àÅ»š\0G*5)!Š\0¤À*…·ŠH†T{Ž(\é·\Z°Ê‘™¦°%qš êš…(\á\ÙG¿\É\áÒ³T)Ôžg6\É9|\ÇQXÔŒ\ì./¿Dqý*\Í\Æu‚¢¦\ZM	:]ˆb†MN’Fˆ\È\ã&;¨ÀAS`g{4¶ ·ÃºF¦}ú\ë\':¥\ÛY\"[ˆ	;ªƒXim\Û–÷H97\n“lC±u\Ù\æ&‰±¨¼d5\ë‘O^~\åeú‘¢s÷9T\' -Xªh¢\nJ\ÓCú%\r\ÚTcE\r³fElc(gS	²4-¨òa7!(¨©­\'U\n¼güâ‹ˆ\rúg\àB†Ww_\'\\\Ô\\\Úyƒr²E½yfg—Ý«\×\'DK=¢AF9*Si\Ðó¨„\ÛW™Nöq¶ ij¢(!\rt”\Ñx!JË›\à!ÀË¶®¢q¡@¸y¡mÈ¦cžý\Î÷XK3R\ÓF˜\Ã$\áúö6Óª`²{\ÞB—@:„\"‡\áª\n0 + \0f`rÈ‡0\Âp\Æ˜µÁR™eD\Ý\Æ\Ö\èn€U°3\Ý&L,‚\nCm<Y …¡ºø\Z/ÿôût…cAk„6¼5¾È‘\Û\ÖX\\JHEM\ß;d•ÓB†“}š\0k+¨ @\ÌóÈ¤ÜºF¾w žº†HA]Vt ’RH¡o\É\Û\nh´h`\ë‘Bb½\'0²”…ó\ç	\Ö\Öf,§=ò­\Ö:]XZ\á\ÒÅ·ø\É\Å\×x\ìo¾\Âù¾Ÿø ‹K«@ŒC\ÒØšY9c2b\ê³ñû››\ìo\ìPÇˆZ`•dñ\äIN}\à}œÿ\ì?\Â ×Ž(Œ\È\Ö\Zp‘L	\"M\ÕÔ¤\Ê\Ò=¹\ÎSN\Å{[;4¦favž~Š;\Ö35IwÝ²!]X\"ñ!\Æ\Öô°Ja¬F:‹“\nm\r\ÎXD\ÓØšXyBW1Þ¾›—ð\Ù2…*\Ö\ê\í%f9u+\ÆDJ ’¤\Õ$K+pþ<~ú\Ó<ùgÿž\ÃQ,Š\Ã±¯\é\n\ËýkK\ãš\æ?\ä‡_|ŒýqŽT!i¿‹P‚¼š-\ÚZDc‰+Ï¢\é\Æ]|·Ë‹¯¾\Æ\Î\Å7¬®²þ\Ñ³[M‘¡ \Õa\Ëµ#‰\Zµ™‘’•\ÓG—ºT§d‰joH\Ø	\Ø|\ãMÌ«\Ç\Üy<$q\È[\×.‘ž8\Ë\áS§;ecÀkBò)M]¡¬!²†LY\"[S\ä5[\Ï=‰X=\Å0X`£o¯In¼#mœZ6n´$9ö‘ð\Ü\ß|“\Ñ\æ˜X(|>c!\0G\Ç;ö¶† .ˆQ:c1µ¡®*t\ímp»öø\Ê\â«\ZE\É4\Ø\çž\ÛñÔ›oñ\â÷gý‘‡\éJÁ¬*ñB\â%%\"@1mjÜ¬„8Bt{ôû}š\ë›lN\Z˜•\Ì\Ê“Â…Ÿ¾\Ä\íw\ÜC=6•¢\êvº§\î@Vi¬¦dXµ©HChÆ»Ô“QûœB@m@6hcÉ§c\ÒÅš4Ud^¿<-Z¶¾­\ÛUJP{‰­œ8Å?\Â\å¿øÏ¤Ö±\Z(\ÒDŠ’ýKÛœ\èe\à^\Z\ë˜Mv©l\Ã \n\èvbŠ\É)ZL¡2ÀG\n\ï4£@ñ\Ö\æ6¡ö¼ö“Ÿò\È\ÆÁ\Â\"Ý¤3¯AŽ\ÚÊ‚Z\"ˆD\ÆÁ\Æ>iå‘µ\ÇE˜¥¤\Ö\Ò\ëvñUª>\áÝ·s<KQwÞƒM™EY@šh\ê\É-\äCF\×/1\Û\ßdIyBP\ç3´H• dL¤tW\ÑOo±óh„”õ\ÐX¤€0ŠqA.Z\ç\ä\Þ\Ï\Ç&\ØÏµe)\0‘\Ï(Gc\Ê\á\ÈZ\Ö:VÂ˜¨ª±»cB\Ðq rp¹¥.K\Zg©¤§Ò­[wþ¾{\ètz\ÐxD\å\Ñ:i}\ÆÒÀC7Ž\évÀH†o^COjº>$‚\Ö=£\ëS†[%—~ô¬ŸG{…\ìQ}t²@\Ú\éHðµE\Ô½Àa\ÇL·¯PŒö‘\Ð	¥(EŠ‹l\rkv\Ç%Nh:ƒÁM÷L!\èÊ„HU@h„ms£\Ã\äû\ßM\çŽs»£@\â4„©n\ë}#\Ôb\îH8†“œi]\ÆR‡Ô¶-¿hI)fIÌ°³\ß\Ï.t˜­.ðÃ‹opÿ\Ã‡\Ã\Ç!\êÂ¸µõÑ‰f{gt³\æzsÙµ]\Ô~\ÙR_c(Gc\"¡X\í¯ø„W_½6š GCÐ–¨7\rz:$3CúnFoòú\ÓOÐŒ·tS\ê\Æ3m6YaÏ¥ìš”ôðY’\ÕcRÌ¦·V†¶\Ús_\íV›\ÑýEp’û~\ë\Ó|÷û_Y\Zô©f#úÎ¡4L\n\Óúþa\0a€‹S$’™‡\étL¿\Ûc’O(ÁE3¥\Øm**U°“…|\âþ9wþW¿BS\Ìr’\åeP8K=\Ñ_\é#€ü\Ò©Ž¹þ\Ú„\Æ\Ó	T›\ê\'”\Ã	“I_;D>š0|úiŸú$I2¶5¢n\è+±3¦ºü2\×.½‚™\ìAE¸F2ó\â-g\Ö\Î\ÞA°¼\î<ÀÀ-€\Ýhñmpt\Þ\î <H?·v;\Öý\×\Ðó_¸rõ2+Y\Än^\ÑS@¤2¤p0ij\\“(Êš\íªb!\É\Ø3#=ñòMš°\ï&M‘G\Öx\ïo>ÄŸþ¬¬PŒj\äBBm,r)\Ã7BP5¤Q¯\\\à\Ê+¯±\â,\ÊY†»\'W\"š„u”\ÚRŒ6ù\îW¿\È\ï¼ÿ>‚å”¾­Ñ®Ñ´¡·\Í7_|‘üú„L(}@\íQ<`qù8ý•c4*e¯¨Ù³‚½7/3k*¤ú… e\î\nœpó|A\è\Ú\Üù´¬\è¤	=\Êûþ\É\ïñÿùbqi…òò:BQK\ÃX)v¤\ä\È]\ç8óÑ‡ \ë\â\ëŠW^{¹»\Íl:$\Ïbº‡\×9\Ûi\Î\Þ÷.¢{\ÎÁ\Ê\0\â£aU1½N@\éa¯ž°õˆCE1\Zš²¯}ýopû{m\Æ\× %\ÞV˜Š|nrñ\Õ\ç¾ú,ƒ•¨6”6\Úaô\Ö+\\¿ò\nŽ	\Ýn#»¨°OtI»k\0\0hIDAT»\Ëô\ßK‡ŒDo\ír\áú^K\ÒN\Æ\ÒRÿWt\0I°\ÒS\Ï»\n^!¤a§eð@süSŸ\Âþ‡?gg{›C½\\‘S\Ú¥„‘VlÅšC\çoƒ\Ï|F$	wL\Ç\Ü*\ÐóöE›\ÇsŽ©3€\"$\"hš6B\ïDÝ€Q³\Ãb¡\';Pw\àÙ—yõ›\ßd\É;\"m\Û&–\Ð\ã\Æ%\ÆC\Ô\ä‘\ã\è\á¯\í\\\æñg|\îŽcwv\Ù\ÚÚ¦®+„\Ëq*F†š™\ÎX9|K«\'ý°²¹¶=,\ZtIò\ëG1XXD¾mo±EøŽˆ9\Ëz‰Œ4už\ã…$Z\\\à\Ô{\ßÇ¥ÿô—\Ü÷±£œ4ˆ* P¥õLƒâ´½l q+Ë¨Pa\ëŠY>EY\ÖA\èˆ\Ï\Ì\ZšR\Ý\î\Ñ,wˆ\"$:ðÄ¶‚8‚7®ð\ä¿û?	®^g9pHSµ\Ï\\@Œ\'ô±gg<A,Ž\é°\éužþ\â\ç‰\×\ÓY\\ƒ(eof J8y\î‡Î\Ç\å\Z‘-¶•\ë\\\íB‚–DAÈ‰“‡I;Ö–\ì&·v\0Í±kSµTóPQ8±\ÝðBaÂˆ¨q¼÷\×a÷\Û?`¼1b\Õ(´­	„¥I$‹Ha²„Dw‰\Ò4(­ˆuF/\îµý^¾\rß‹\Æ\Ò\ëtiJK±[$	ýL2«Jªh\Â²-\ë}ù2;ùmªŸ½À\á¢f\Ñ×”®5\ÝešZ²k{\Ó	KYÂ‡¸‹\äôI’¥e¶k\ÃöÎˆ\î\ÑS\Ü÷Þ‘¬\Æ \Ø.JúKT¡)-ZH\ât\ÞJ!\Z\ç	\ÂhJ²¸¸ð”7oð\Èù?÷v\í[4D\ÝÖ™&òd¼›“÷\Þ\ÃÕ·¾\ÅB·‹*r<\Ò%m÷mûU)U«‚€\Ú9|S“¨yp´ªAd\ÔÁ\ì\å\ÝA.%$¾¦\Þ\ÝAD.\\\ãÍ¿ú+~ò\ïþ#?v–ñÖŒ0”ø$`Z7\ì\n\Í\È:\êÀ³t\æ,¾\ïv²ÛŽ`ö¤¤Š3Nœº\ì\Ä\íø¬\ËL†\ìW%¨d±±„R§ºm\î´PUZy‚PRS¬÷¨ l+[\ß\Þ<\Z¢M/Ü \Ì,ÀW³¶òH˜\îñ\à\ïÿ.ý£Ÿr\Ù\ä,dm\é\ÖLT”&b\æ\ZHB\ÔB‡Íº &DR\n¬\Åcp‘jc‰€V-‹‚£B\Ê\Z)rŠ	<û\Z\Ïýûÿ\Äþ\Ïp\ÄN±{—9vd½\Ù>»y@\Ó\ïòó­\ëœy\à\î|ïƒ¤‡\Ö‚ý4!X[%\\[c\åü\Ý¥m\ë‚\Ò\ÄH‚yÛ•¤Žzv\ÚWV “„ƒ\ß(Yx¥ý2xó“\Û^G\æ]\×lmq@ \ì¼\æ!õ5ú÷ž\ã\ê3OHAOÆˆB\"\'ñS\ZzYB\Ù\Ôx¡ð\áqJ\âð8\'\Ð\Ö\Í{\Ê44%\Í\Þ‘¨¡š°ó\í\'x\ê_dü\ÔS‡Ž¥´Žg&›Œ%>´N\ç\Ä:Ÿø§·#l¯\Ï0\ë WX?~u\â$ôØ¦Á!Bi\ÜA“ò¼—S\Ñ\Ìû9Y\r\Ì­é·¿Ra\Ü\èGö\Ü\ì\Å\àœChAƒ_õ{`<·}\äü\è\ç\ÏS\\¬F®Y1·w)Á~E\Å\ÄAˆŒ-±\ÎR;B\nE $t­\ËP\"¼ø:/}\á?ó³o}‡\Ù\ÆU\î9{Ž¢\Îù\Ñh\ÈÃ±\î\ä\Ý˜•\ÛNQz\Ëþ\î&AŸ\Þ\Ú!\Äò\Z,¯´9`©¨GE[ü(\ÚTš7\ìzL»i¨w\Ô.ú+Àk›fÕ¼wVP\Üü0\Îcñ”8¼wDiU\Â\Ñ<@ö\ØYf»C2˜9B\ãÁH(-z:leA,\çõm–@b©Zÿ¯ªÀÔ\ïR_¹\ÂÎ³/sù\Ç\Ïpý§\Ï\Òllr¤\Û\ç\Ð{\æ›?ý!‡\ßu§þ0\Ëw\ßÎ©÷Ü¼\ë<”%\ÃW_&]_¥¿¸Ø†Ð’T\Ø&Ó•&Œ5Vi„\Ô7¤¹\Ã ]\Ð\Î!xmS¿\Z¼¹m\'(/n6!\ÏA”aÀ\Ä\ä\Ô\Úá•¦¤5³\ã;\Ïq\âc\ä¹gŸbYAœ…È©…­«°qz\\—¯·²RµŒB^ÁÖ\ê\Ê“\á6Ï¾ð#.¿ú“WYp!\Ç\Òzq­ñˆ\çžOþ·ÿŠÁ}\ç\Üu;v©K\Ó\ëe=\è-p¨»€´U;“@k0`ŠË£”Ö€ý…\Òl§\ç®\í\\W\ïŒò\Ä\Í\éŽyÚ)\ÂÃQ²mI\ß7S\Ì<\âœ\à°ÅˆÅ¸\Ãô?\ä\ËÿÃ¿aecŸcM‡\Ñ\Æ>¾Ó¥ö4˜a{1\ÇOÁƒ«ê²¡\É\r\å~Ž\ÛÉ™\ÍF«1û\ã]R³\Ð\ÐT–F„\Üù¡qî³¿	wœ‚ARõ\ÞFpS\ã|\ÐM\0\È9‹jª\ÒR%ý^!\æ©óó1	\ï”ø\ÞFy\î\Æ\Én.\ë\äM?w¾$Šh\Þ@oiÚ–#\Ð9sŒ\îýgøùÅ¯\È%N\ï“\ï\ÐW_aY”\×-;/ü„º®©k‹ò’(JY\Ò]>\Æ*Å«W®\ÑtÉ•\à*\Ç?ø Ÿø\ìg‰\Þý\0$i\Ûr\Ðé‚ŠÁX¦ùciš\Æ	UÛ @Iƒ­-‘\Ò\ÄJ\Æ\Z­:7üô·­\íøÎ¹ö\í\Í\ÊmÇ»G!Pbnß²šÆ Q8,µoU‡\Z‡Š3þ\Ü?\æµ\ï|›\í=\Îb’bB\ÜL	BOnk\â$¦	M\à‚§|\ÝPf\ìG¸ºÄ¡»\ï\â\ÜG?\Ä\à}\Âmg \×/)Oœ,0\ï\Ç\Ð\Í-GË°žQIO cBBT(¾­Z¿1\r\Ã\Íu\Â\\¦{Á¶€[­Œwž£­ƒ_¡”\çWŒµ&?*b·½	Ö£“•£|\ì\ãŸb\ãÿù*vÜ¶\É÷œ&õžŽ’TE4¹†}S2ò*IYY[¥³¸\ÂGÿð_À±#pò¬,Cœ`¤Fˆ”\ÙN9˜b=Ž*hÈ…Ç„IŸžû¨ƒŠõTn‘\ßŸ\Ëx;wÂ›V\í;\ï\0Àh„—o\îÀ€\ÖJd>!±¾­ñ\åðY\Þõ¡O2ýÆ‹L¯8½|3\Ù\ÇØšIYR\'!¥ˆG1»\ÚS.ô8q\×y\ÞûÑ‡\à÷C˜\Â\âzhJ4¹÷¨y_Fx\ë½\ç\Ó8¬tTX< 9°Q]«ðÚœ\êü%\ì-Öƒ›¿óÁhšþ!\Ó-8 \çù…üÍ‹xy#\à\ÂÁ\ì\æ\Ã¥\0™@] \îû0½Û¾Ã…u\"¦¥§³¼†\íD\ì\Õ\rû@=\Â=}ˆ³Ÿy\Î\ßMƒ\Ý¢ƒ0 \Ä\Ò´\Ñ‚ƒdË\å <F{\Ìüõ\'´s«^Ü²\ën\æ-†\ëM\Ñw\à¼\ÃNe~\ÉÎ“­µ}\ëË›£5üÁ³;Z¶¹4x\à\Òj)\å\äGf\ã•+Lã€ªŸ±iJ®\î9÷\îy\ï>\È\Â#\Ãù3yòºD\Æñ™5\Ê\Âá„y\ÕE$%Z\Ïg·Ø›\à¥k{@8¨PS7)ó\Æòoãœƒ?¶ƒgÜ÷ý‡®¦\ÊÁ\Ø\"D³R\æR´lM$4øÖµ¥q-I\ê¶±Q0œLYµ±9\â\Ïÿ\åñ\Ô\ß\äŽ\Ógxð\ã\á]\ïÿ úü}t\çµ|).“\ä\Ã\ÊV»÷Tz# \ë6l.Ô½ðH\Ñ\n.7§69}{<Êƒvrýq7µÀ\nntq\ßüzS=´\Üü”ys³µ¥º\í4xC{\Øù³„‚¹±Ôždd|§\Ûa\ç\ê+k<ð™GX=„Oþ\Ó‡× ·\0\"•´\Ï!Ø¸}W\ç\r\Òù[ß¢µ\Þ<Xg±x– h[O[ù/^\"ý\\>ß ºù¦Ê›9„f7·_oqüý/ˆ¨wh²¼=\ä\æ\"\á–`À·\Ü¹!%­§0g\ëZ€Á“\â\é€\Éñ	ì«œ<,	\ã\n\Ýk•K«\Ò\r\Æ)*\×nB(d[h^Ï£8óAe\Æ[<%<¡(\\K†n~\ã[\æY\Ý@\å .)\Úþ0nÀÝ¦üv\å¹9\ë€\Ë\ïhý¿Cƒú0)ýº\0\0\0\0IEND®B`‚',1,'superadmin','2013-05-23 13:50:19','admin','2016-11-06 16:37:22','CRM','SalesForce',' http://www.salesforce.com',0,NULL,NULL,NULL,'0',NULL,10,0,1,0,'',NULL,NULL),('78917a82-1c86-4020-b86a-3b1b350357e3','JWT Demo','http://oauth.demo.connsec.com:8080/oauthdemo','COMMUNICATION','985e805bd49770e7e797209db3cc2767','Token_Based',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0\0\0\0T\0\0\0»šd\0\0\0	pHYs\0\0\Ä\0\0\Ä•+\0\0\0tIME\à\n\rÆS\×\0\0\0tEXtAuthor\0©®\ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬\Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0À\æ‡\0\0\0tEXtSource\0õÿƒ\ë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨\î\Ò\'\0\0 \0IDATxœ\íù“Gv\ß?\ïeVõ1÷=ƒ‚\ä\î’\Ü#$Y+\Ù\ÞPH–\ì°°C’C¿;üƒÿ9+B!\ÛúAa)dGè²­•´\Ú{¹$\â`f0W\Ïtwe>ÿY\ÕÕƒ	€ Ü‡hLŸUY™\ß|÷{%\ãq4QCUÁ\0!p€‚pI—ô©(ˆ\\¢ã’žŸü%p.\éEIE¸K—ôB¤\r3ûYã’¾€\ä£T”\Z>BR™ýYŽ\ë†>i\ã¾\Êj…W\r$\ËJ@C.e\Ù\çIföJƒ\äi¤H6º!X\æ@\Òz\\\Ò%]LjQ0“†\Û\\\Âå’ž•4\é7—:\Î%=\'yÍºN\â9\Ò<«\é’]\Ò\Å\äE†t*4‰]‚\ç³$³w•¼ÊŠ´73\"’/\È/¡ø«;þ/<‰\È+	\Z\Ð\ç-Áúµˆ\à#\Ö\ØX\éC\É\à\É\Ü\çÕ»®Ÿ+z\Ó\à¹\è}Á\Ë\âI,¿j$\\\èÚ >ðúµNR0`\ÊT½tõ\\\ÒÇ’‰I\ç!‚S\Û1‹Kú\Å\";÷÷üû¾~eõ¿¬ó`–­6˜\ÚG’\'@u‘\Íð|¸³Ž!­ÿ[ºÙ…g0\ä\ÜLòw¬ý\Íöyr\àr\î:Ïñ\ÉQ_<\æ©WÍ—\ì‰S>¯köy\Ã\Ø\Ó\ç†c˜Í¬\ç\ç\ÒúÀƒCPMQ-©Àp¤\æNù1\nu\"™™-‚\ÑRžG4C¥v=\Ö\Z{k@õlr&†›\ï™i\ÒÁD±hˆ$ñ*³e\è0\Én3\Äbv:L\\\r†#\nH\Ô\ÈÀˆ ‰\Ó\ZŠ™ Òœˆ˜Ÿöf\Z‡SÛ¯\Þk(XD•\Ø\\Wö\Ý[>µf¤\å÷‰\0j+®&Ò¬\ÄÔ†¶z\á%/¿¥€L¦u2\áS\'Hkny\\&’\çßŒh†HZs|Œ\'.½‘\'\rn:<Ú€\ÇZ¼\Ê\ÒÄ›¥Éªbd#e\á±)\\R\Â1C\\›JŽ¤\å	µ„\ÓžÀD‰w€#šaqœÀ¨«†@$j—Q,(¥\n.&`‰óA%h\Ä2\0e„YÄ‚t0óT&˜÷RaTˆ¹4“\Z1«& ‘\ä\\Miºi¾Bšl3O¬=öf¨\Z*b5F½.$ ˜Q:}&ð\ÔfrD\Ç@\r¹xbˆ \Ò\à»-D¤až&L/¦\Õ0g`!Hª:\Í?N\æ-ÿHò’N£\çõ\åúUš &H;M\"^\ÓÁTõ	QCs©\íg“ki\ØbÞ¯V\Çø-‘\06¡–~\í\0£QE¬5Î¼KA¤2¢\rQ?Bd˜=\ëƒuÅ‰atŠW\Ø§!Á“2Ÿ\Ùg=D*˜8lÞ¡f$ Q¥ó\é¢\êˆxT\nx\n\×9o\"7ÁµH\'Ê”/N8S\Z\Æ\Ý\æ2O£ú{-¯Œ‹\à4o\ì(]Z5D”cJk÷\à¶a8	N\\|ÞŒ23Ð´[ª`\Üþ\è&{‡G Ž*B¯Sò\Æõk\Ý–hV_\ç’\Ò:Q\â:–A“\'4&q‹Cö\ï38Þ¥_D<‘Š.\Åü&Ý¹-B¢€—Ç˜ø\Å_O²\Ø\Ù1{{·ˆ\á€n\é\ï\ç˜Y¼‚–ó„¨\à˜\âd\ÌølŸ“ƒ{\à\Æ	PV0;¿M·\\¦‚ˆ¡$ŽEtDÔ¹$\Â	(\'œ\Ýc<x„:a” ó\Ì/¿FÙ™{ú\ÚfN3%²b\ä\äÁ\ÇwaUDT°hx\ç!\ä\Ùmd0’%EPˆRë€‰4‚³m\Î\Ä\r–¸—*Q\à`a{‹\Þú2rs-“\ë9n³›©÷C4ˆ‘“\ÓSþ\èÿ˜¿üë¿¥\ìöŽ\Ç|ùKoñ_þób\îµ\í¬€‚²™õš\Ó$ž\å·ˆT„xÆ~Ä‡?ý\'\æ‹\n¨¤\Ï\ë_ýu®¿3K°\ÐÕœ%©yšP\ëdQu\Ì{?þ6ü˜…¾\ãôä”™\Ù5¾öË¿\É\Ü\æ—®³€\à °÷\á?ðƒ›±S\Ý\î:_ù\êo²}uƒÌ \Ò\å‰²W\Ä\ÎÆƒ=~øÝ¿`ÿ\ÑˆÂ°ê²´öomNgþ©œç‰°…\áýwü\ß?úS\â¨BTˆ\ã€j\â\nI¯›Q,f¬5#¿¯-\à8KzQ­f(B©\Âh<&8\á\Ì¡\çù?ø}¾ñÛ¿µµ\Õ\Ö\Û\Zu:IýAœ(Pªx)<¾ª\Ø{|\È\Ý{\è\Í\Ìp62\Ó\ï3\Z¨BL|Dd\n”\Óó‘”\ä$\Éke?I$V8g\Ìö=Žv\é„Ãª\ä\àþ2\áõ·p½5Œ‚(\é÷Z§Ö©0bO¯£ŒŽw9‰£!ƒ\á!G;W˜]\ÝÀ»%¢Dˆ§û\ì\ÞþÃƒ‰Å˜\Â\Æ\Æ‹ó³™\å\'\í²UHÌ‹“_‡1\ÂGû·\Ø{øcF§w‰@\èn¿K·Û°9?/’ÀAQ¿g!TwögCœs„qÕŒ!2QÒ§7hF8H\Ê\æ\n@°‰R¯Y\Ù>#P©a‚¡3\Î8=:iÄŸŒ–‰lƒ§9}#wò…¤\Å	f¨/™™›G£Y\æ74\éH6‘\Û\ÓTƒ\'‰¬d9\Õ\Ì(_E õV–f9¼s‹\Ù\" 1p°ó>G»7Y¼:G  šk&:YY¨‹$\å´\Óckû\Z{\Í\Â\ée9pÿ\æX¹þùe‚%°÷\ïqøð\'”rŒ‚U\Ê\Ò\Ò<ý¹\Ù\É\\\ÕMôEEˆXbr\Ìñ\Þ-Æ§\è¸L<\Ò/\ØX[¡(\nªq¤(\ÝEûª™¯¶¾cfH0\\Œ\Ä*\à\Ðf±›ÿMhY\Úh„Á›$…ÀŸ¯\ÔcTY¤‰$^­–\Ö%8\Åc\Å\Ø	¾\Ó\Éõ}\éJ}\íòh»6DZssÁz\É<7ª˜ÿ†dš‡hø¢HƒVi·1.\ÏOQs\Ô\Éó\Én\ãˆú1¡;;\Ïö\Õ\×<ø1q|ˆC!³s\ë‡,n^G‹.%1\ï	_+[f„\ÎwÀ\â\Â&«\ì?‹ô¼p´·\Ãé£»tf¯#1q\Ù\Ýû\â\â1W16Y]\ÛB|•L†m\íkq‰\ë\Ù_F\Î?`÷þûh<Adsó,¬­¥oû‹Yr[ß©ÿ†:Kst®¬¢\'§e‰ˆª*‹gC$úó¡Kƒòp\ÄpgŸ²\ìÌ„\Èü\Ò\ÃB8+„Q²}s–—3b¬X\èw9\Zaý’\î\ÂL“l\Ñ8	‡@K|\Õbªq\í«±\é93”ZU£Ž7¢jz÷\\0M\ÔNÚˆ·–\ÇE\Å!jH,A{,,n13»Hµ·CYt8Þ¿\Ë\Ùñ#:s¨tˆ\â‰Ö²\Ü\Ô	•D\Åw—XY¿Áþ½Q‹#œö\î\ßda\ëm´œ\áôñcö|€\Åˆ\à´\Ã\âò6½þ*H‰©LR¿%OžyŸE}…1\àh\ï\'‡;	\ÈÁ¡®\Ç\Ú\êU\ÊnœNd\É9º(¶$\"\à”w~\ã×¹ñ•/†c|Y¤õˆd±eò\äC»\0?üo\Îwþ\ä\Ï98‚\n~¶\Ç7~\ç7¹ö/…ª\ë\Ép\r†š`bŒª18%	NX\ÚZ‡BAÁKk—·mþ\'ÁS#+/´Hv*Iv\Ôi*?ýDûðI\èh>v­ñÔ–W\í\Ò1f¯±¼v{û\ï\ãDˆÕÁ\ã‡=¸EonüŠ³#M…¼@B†§\0ayó-z?ù>\Ã\ã1Q	<¼û>›¯ß¤¿9\Ë\ãG?\áø\è¥\Z£\è(\ÊeVW¿Œ\ï¬`V`š&·\ÙW\æK‰u\ØtH¨y¸ó>§ƒ}ºê‰•\Ò\í®±²ñ\Îõ‰ªD&¹œOO­8;UPE\×q+ó \à4ùÁ\ÒtÉ”•\Ó\ì\Ý\nª¿ý•\à|‰zÇ‰Tk³t¿ñt•Q‘\êZ‰vQÈY$u\ÙnsŸúo-³¦ª[\àÉœÁ2\0Lž\ÌA|’Ú­™«6’\'.Mœ\Í\ès¸\îË›o¡½y†!R”ž0\Zð\èöT£#\Ä\Æ\É\ÒjŒ¼¤«à½€8b¥”KWXXq,çŠÓ“‡\ì\Ýÿñ\è&G;\ßG\ä¼0®\nz³\ÛÌ¯¾\rºB‚Q¨1‰ó‚a6¢:yÀ\ÑÁ=ˆc\\\ì ,²°ð&½ùkTV22cDh¼k\ç©\Öq\Úi¢JP{eä”¡\ÂHa\ä`èŒ‘K\Ï\Çù\ïHèŒ¡IDid`C1ªRÁU§\àL’YoÞ Ìƒ9£’Ša5¤\"fŽ\ÙDZ\àh¬«—zê™µ‰.\æ<O³Ô›·M’’X\Îd4\"Q,­‰%\îc\Öf˜[{ù•+T8\Ô9œ\Z»\îpò\è^OZ $ùj\ã(¹\Ýh—µ«oR\Î,P\ÌE\Ð\Þ\ãñG\ß\æp\ï=SFU:\Ã\ê\ê—(g®‚\Îa\Ú!ˆd„Éˆ\Ú\Æ![ˆf‚\Å3>ø€\Ó\Ó]J§X,\èk¬o¼‹k˜ô¨\Ä9,}p\Ú\0j“’_Ÿ‹z\ÖkÅ¹~(µ\á‘ü_N5!1R¨£PG=9Ù·›Ž—¼\ZÉ˜´@À+RxbV®cž†E¶ì¹©•Ÿ†\È\Å Å‹\În*\ÝE\Ýò\ÊÚ”}—¹µsK°¨„\à)gW™]¹Š+\çÃ‹qv²\Ç\ãû7±x†\È$\Õ06,M†úÄ£g×·˜_YgTED\r•1§\Çø\è\Ãd8¸2\"˜Ð›Yf~õu¼Ÿ\'˜\Ë\\Œ€eó|Â‘ó¿ñ€‡÷oR\ã\Ô‚§\Ó[eaý-Œ>*Lj\'\è“s\Û\æ:SŽB3œe„2\Ze0:º\ÊÖ£\à+(‚ QðdX\Ñ5e\Þu(Ç†\rF¡k0c\Ð7ðµ\íS<kŠóµ\nD5Æ”!(IŽ›¤À aX.¤yLX\Éj‰E\Ô\"’kÀj\à4±¸ó\àiû\Z\É(Yö\'keb‚\Z*¹¦L\rQAdŽÕoPt7W†ÈˆÒŸ²{ÿ\'œ\ÞEmˆT#Š¡\Ó#ODT\ÅTÑ¢\Ëúö\rœv‘\ÊQ˜#ž2x¼C¬ŽQU(™_º\Â\Ì\Ê\Z£8fL\ÅXª¤«X‰]$zHÔŠ tÀ\é\ã{œ=¼G·\n¸¨T”\Ìl^G—ÖˆXPºQèš ö\ä\îªS=Uõ\ÂTÕ†\Ã\Èä»’i\0Yç£§­\Zƒ\áƒ$Gx0¤,ÀK¾–ˆ·4G)\ê)¨S¼÷¸jR\'\Ú\Ä\Ô\ÔÄšS\Ô)©–cIù*ÎFž¼\0RÀ±ŽõN[b\ì«ž:ò[«U2Íó\ç\ÖpP“³7˜[¸†Q`:E\Å\àø>\ÇoBu‚ZH^\Ó\ì\áNó¡8—c`®Dµd~u‹ù…\rb\åQóh0N\"¾˜gqm›\Î\ì\Ñ¦\Æ8ß¡YQ6	˜1w†\Ø!÷o¡g§øÊ°\Ê\è\Ì-0¿µ\r.\æ\nÅ‡|¼HC˜Ä“\É\Î¦övóH›-\édi1Iò“[¶p)@«–”ÿ\ÆK\Ñ%=V\È Es4½‹.¿\ÑXN\Úz‘Ns1‰M\Ì\í\Zo\ëD-\Ô]\à\Ö™þ\'ù\Øù¢¬\Ékph§\Ï\Ö\Õ”½9b6O\Ãè”½{·Ÿ\ì\ã}@°,\Ò\Ó%kžø(š‚ºErn…\Õ\íTV|¦Š˜\áDVBw~…«\à\Ë\äÍµ1\ÞR²‡¶®\Ç$Å¸¼Žž\ì±s÷b)\n_cumƒ…•\Ìõj3½VêŸ‡\ä“\É\nn=HYi³\ÊDœ·Ö½žÿú3›,é…§\Ñf‘˜D‚!+°öŒU\ëOñ\á<É«\Î}ø,$“£\Ô­!˜ï²¸}ù\åMª \Ä\Êpxüðƒý»H<ªô}qI\éŽ1ÏŠÃ¤À¤ƒv™Ý¸†\ï\Í\'q‚ 1»´\Ã\ì\Ê6½¥5E!Ž*\Ôg±ñ„\×\'jCw\ïrðøU¨R\0\Ù,­oQ\Î\Ì\å\ïNüf¦\Âl/.Ä”<ùùù\ç\çûq¤V»ï³¯@¦ù2.\ã\åQZ(!D¡Š\í/³²u-f\Ñ(UŸ\ìsø\à&\ál\Ñ*û%òÇˆEK¹G(“.ý…\r–V·‰R`!G¢Qô\æXÜ¼†v\æˆÑ¡¢”\Þ\ã$\â²U\Ç\Î\"D\Âø€\ÇoªÓ¤h\É\ì\â\n³\Ë\ëˆ\ë$c\Ç,[,\Ó)0_4J[&žµ\Ä\ÃÏºmO\Û\âhLVSG\Ôè°ºuƒþü\Z\àS\ÒUòøÁ‡ŒŽ 6Kš\\›w\å\ì *!z:3K,_¹ž\Â ¢\Í÷\ÊNŸù\åuDŠ\ËJ-²´s;\Û*\Î°ÿ\à&*c´ðŒÍ±¼q\Þ\Â\Z&p)/†”<÷¼\ë\Ó\Î\ç\Ï?\Åù5\ÉAi&¬ždxz$üó¢\'EZÚµ#˜£\\\Ø`a\å*¸>˜\à38xÀñ\îG(U:\Z“3\ß¢)Qþ,ˆ\â´Hò\\•*\Æ\Ù\Ï\â\Îb:9›°´€\Å\ngœ\î\ß\åôøª1e=Ö¯¡\åf’³6k¿\Ó\ç\ÅtŒi“¿yþ)ÎŸtK\Ê$Rw\ç‘gzŸ17U%?7¢¢|1\ÇÚ•/Qö1õ˜ª\Ñ»w\Þ#œ\í#Œ\Ò\â‹5b‹FOI.}µ\È\à\è\Ñh„()B\àôøG÷o\'`XÈ©%’°\Z\Û4)\æqDñ\è\îûH8Å©r:,o¼\Æ\Â\ÚUŒN\Â\'\à\Ï\É\åŸ=†sŽ˜“«5§||:ðH­°M¢\Þ9\ÐùjR²P\Ós3t7™[¾\ÊY¥‹#\Ç\ì\ÞAm„\×Ä¦\ÅIzd+ŽpR1²s\ç&!Œq\ÞSUÞ†>¸\Éðpu-,¥¡ˆóˆ‚Ša\á”Ò9;\Ú\áñ\ÃÛˆU)Y¼˜a~õ\Z\Ú]Â´YŠö‹µµ\éù\ÙRml|\\1\ßó’NLÅ‰\í\ÜX¯ uM)žDIg~“¥\Í7¨\\s\ï…\á\Éc\Þ~$Ž!\Ì\ÅHyÀ)Y\ë\ä\à‡{÷q\Z1‰\É/Fag\î|Àñ\îm$Žr\âZJ,’³\ÌT§NØ¹óSFƒC¼Â¸Š”3‹,®¿†¹>\Ä2Å¾˜„\rZ\ËøùO\äK ­\ÄÛ‘\Ýq~>”Æ–’\ÊS\Ã#\Å<3«¯Ñ›_KFŸ²÷\à6\Õ\É>š+/ ›\Éb”ð2\"Ž9¼\Æ\'C°À(T FÁ)ñ\ä!\Ç;7	ƒƒ–\ÎU\Í,bšB\Ð\Õ\Ù{n\âcfŒƒ±¼v…¹•+ }\"%†\"Qš2©­µ/(\é\ÄQ­hÔ§<\ìs\Ì\Èsû\Ç2x¬Ê£ô%½ùUV·^#ª#\Ä\0Tœpp\ï6\ÄQ\ã–+\Î\í\ïð\è\îM\\!!•\ìø²He8qHO‡\ì=øˆ£\Ýû@ Âˆ8¢(\ê‹$5p°{—ÁÁbX\Ä=6¶_G\Ë9Lº E\0\Óz\Ø$\Í÷ó¢Nw\ÑžeTj\Öö\ç¼ð´]\ÇOR“÷Õ§^’\â©\Øªhø\Þ<‹\ë\×)\ÊDœ\ZO¹\ç\'\Øøaœ¢=dAaXu\Ì\àñ=G;\Éo#Wô\é/n|—`\Ðó\ÂñÁ=Žöoc\á‹ˆ0\Êó&6ÂªcvnSž¤÷\\Á\Ì\ìs+Û˜ua’nR»B\Ò\ÃÁ\ä@½(5–¡ÕŽJ\Z7Mý¼Žƒ\ë3 Gk\'•My.>\r	u-\Ó\Ç\Í\Îþg?~ˆEó•ˆY‡ùÕ·™Ÿ}‹3˜°ó\èœß„x”8Ž)Ž\Äg<xøS\Î\Îv1Azó¯sõ«¿A˜\Ùæ””3{\ì\íü\Õð%j‚#…*p§œ\Ýc\ï\æ•Q™1ŒŽ+\Û_¦\Ó\ÙDF}¼t“´ó‘\èFD7%¼I{û|À-\Õ\\\ÕU¡M†b\ë;\Ï\Ã>\çi\Ï\ê˜~º\È\á]»\å\Û\Ã|N\Ê’:\É[°\Ì9=¾X`y\íhõª\Ç\ì\î¼r†q\"\Ù\Ý9Ú½\Ç\Þ\ÞmŠ\Âp\Þ¥\Çü\Ú\rú\ëo1»ö&\æ\ç1W\àÜˆÁ\ÉmŽv\Þ\Ã\Ùo©²L,8Ù»\Ëhð˜BSGgv‘Å•«¨\Ì \Ò\Éæ¹‘ª+Æ˜$}\í\ål\Ög¤\Æ\ÇSE¹H¡½S\"õ\Â“þK%O?m+šò¢ó–#R\ç2¦ §‰C‹’\Å\ík”3‹TÑ	\Î*\Þþ€³“}b‚TXav\ÆÞ£»÷)½#(;s¬m¿A1³\ÊÚ•7ð\ÅfQ\ádpÀ\Û\ï\Ã\0OÀ…ÿŸ\rxxÿ£ñ¸A–·˜Y\ÛJy\â\Z	‹Šo-\Ò\çF2™÷©ô\ãö\Ü>ÿø\âµ@­7O£7$\Ö\ÅcR\Ð]Zei\ã*\Ã*\Í@\Ç	§\ïóx\ç1†8\Ø\é>ƒ½ûh!óË›tW®b±\Ç\â\Êu:3kŒƒKÙŒñŒÇnQ\ì q„†ˆ„Š\Ñ\Ñ÷\îbr„\É¸3+Wñýe’2_\×\ä×fÍ•\r“¸\Ø\ç¢x™ô\ÅO³]k-M0<A<Q=Ú™e\á\ê¸\ÎfžR²»ó\ã\ê(Õ‹\Û1\Ç;p²w‡R¡\nøNŸõ­7\Ðbh}:3¬o~	ü,!B\á#\ã³]ö\ïý¢Œ±\Ñû;w8;}LQŽ¨dDgv…ùµ\×A{\à\Êi‰m¹\ÖÜ¤®\êóñ~&ô\Å\0”d›j/š°¤*\æºÌ¯]c~õ*¢=\ÂxŒ\Æ\ï}\Ä\Éñ}°v>dt¼G¡F\Â\Âò³›7°\Ð\ÃttŽ\Õ×¾J\Ñ[¦Š‚s«#\Þý16\ÚGô”pòˆ{7ðe’R3‹›,,_Å‚\Ã\Ô1	\Çe‘a­tª¦:å³Ÿ±ó=_Z`ôEH­H©«¹=I.\n(Eo‘µ\í¯€¤r^/‘³“]\Üþ12\ìpôð#l< †ˆi\Éò\Ö\rŠ™UˆÌº„Ø¡˜\ß`iý:‘’#…FŽ÷\îp²\âcN\ïp°wU¨B@}«_?R¦\Þ\\D€¶\ÔDk\çó˜¶¬075\íuó§Pº¾x\à–\Ø\ÊA]“œ\ÍÅ´\Ë\â\Úktf\ÖV¦@\æè˜ƒ‡Ov8Ý½\Í\É\áD\"•¾7\Çü\Úk¨›K\Ñù\Ü>Eý,‹7ð\ÝEbP\nqŒy|\ç‡\Ø\Ù}ö\Þ\'T‡¥cT9f\æ¶X]»Q\"¾“‹Û®œ\Ú:i•g\Öÿ\ÏzÊ¤U}\Z-7¬út\Z»þ¬\Ó.^„&±·Ô\ÆE²ùp¢(ú³›¬n~…Q,	&¨Nr|\ëû~ôc\Â\è$u˜Ð’™\ÕmzKW0\×\Çr9ŠW¸3K[\Ì.^Á¬‡DÅ…3öü„Á½\ï°÷\àûˆ\0Eu™\Õõwp%\ÔõR-\×\0N/¹\Óz|~\Ü\çÉ¿/€/œ\ÎS\'\æ®d’=^j†Z@¢9¤»\Ìò\æ›hg‘±9œT\ÄÁC\îøv\ï\ÝL‘o„\à:,l½ŽŸ[!XSq>¥³°\Æ\Ò\ÚuD\çˆAé–ŽÓ“{Ü½õmŽŽ\ß\Ç8f\\+6X^õóˆ\änf¤@kŽ¶”š\Î‘ý·ÀÓ¾„ó\ä\ÓbŸt\Éo‰¾Ø¤¥_¥®¦á¸XD, 0<F\Ù\Å-Ö®]§\à\Æ\'\ï\Þctz€S0UÊ¹%×¯µ\Ç‡¨\à4¥k˜(\Ú]dy\ã\rúýUªQn\Ü\Ù\Û}\Ø\'2 ˜²°øÝ¹70:„˜‹\å¬\Émò¤U’T\Ç\Ù\ÄZueŸµÅžòþ³.K£óœóŸ{}þ\È\Ö8º\Z€\ÙjM±\Z\çYvŽ/\Ê2\Ó\"Lzþ\Õ#5WJ\Í0‡\ë/±qõM´3ƒ¡d„T§8X ž\Õõ«\Ì.­QQ`N9\0f“Iæ–·X^\Ù\Æi‡0¡:\"„}DŽñE¤(zln¿/\×1s\éú±\ì\È<—q˜/;\Õ\ÄJ®ý,9\å˜U{6\å\è\ÏN{\ìü\Â\ÂÐ¦tž–ÿmü(µRò#f`L¢bf±Q\ÈRRÓƒ>˜X§Fn\Äö\ÜSW­•\Ï\æ‚b%ˆO\å51 ¾d~\ã5\è­SEŸ*$\"N¨\Ì!nŽÅµ\ë 3\Ô%\Ø1¤‰ƒÊ ŠŠ\ë,1¿ú:E‰±	¾\ÐT\×5¨tû«Ì®\ß@|7ûrR“\Ô=…©8\Òô³\Ï:D‘’c˜B ä‹¥\é-4\Õv\î¦©vm\Ú\Ê5§”4\ÒTW¤=/¹V >K©@p\Âm\"\Î9\ê(\Ê\Ó2\écÁ\ç£TQ*\ÃÅ‘ª¥V‚ú\0\Z)\æ\×é­½\Í(\Î\â´\0§Ejrö\nË«o\áu†R…‚@QF\Ä+\æSZ†E¥ª:¬\\y›ba›±ë ¾ÁS0G5œau\íMº3‹\Ä$w\êP<M\ÓKžl\Þ4\é_ö9D\ÕÐˆ–Â©°B0\"Œ\ÆPE\ÄRQ\àˆÔ§¡.¨žó´|U“‡$=\"7:jx	\Å5D…	K¬ó{CH9-©$\Ér[´öK\Ï\Ûc|žyRúg»Â±®³­{ ¦˜Ÿ ¾\Ï\ê\æ\r:ýep}‚t¨¤\Ã`¤l\\y“bv¡ÈÀ€4µç©º\Ô\Ôuqý¥\äó\Ñ#\Ò\ë\Óï¯°´~×›!«,ErFŽf\ÕŒSÌ²öP}r\ÝOGI/¤\n8,ª1…ó”\ê X2:˜40°z¢\Û/ m¾ðÄ—¬Yf\0õ—,kAfT!2\Z\Óa\êRXZU\Úú\Ð—õ‚\ÓV—C·¶G\Í9¥>yZx§\ë\Û,¯_g:<cë±¸z•õ+7\Ð\èÁ<–{¶#öfFUUˆ:¤è±µý&½™\rFUŸ\Ê\æ9tgV™YZMm\Ùô\ÍÀ´”{\ã£Pª£ð>ml\Ñ\\¿=\ÑOký\çY.\äLõs\ÚS\r |`8<<\á\äd+}­Svð\Þ7ZQ\Óðñ¥Pc³\Âqú#©¥#¦\Þ ôº‹,o\Ü ”«ho!³\Ì-_¥œ[\×Šüp4©\é’À£N	1F‘\Îò\æWn0f‰±-#º\Ê\Æö\Ûô\çW©ªqR\å_Eôd}Q¼r:\Zr:JumO|ýU±O‹ó4µR4\\\'}\Ã\Ø\Ý\Ý\ãñÁaV˜Ó„÷gf\èt:M\ry\Ý~þ\åP\Û¨\ÇiSkV‹Ÿ|>Ò¡·p\íor4\ê!–7ß \è-bAj–P‡&úŸŠP.w\nñˆ›cmû\\w›Áx‘\î\Ük,¬\ÜÀ\è’\íª—x/—B\á¨úžC­\Ø\rgŒºz%\Öjª\Þf\ÏBþ\éµz\ÂHð™»`XN©488<\à\è\è‘úVF¿ß§,KBHM¦\'á„—E\çm\Éz\Å\ë\æJ \âpZf‹0»|·ù·x´³Gn……\ÕmŒñ\Â\é\0\0’IDAT1:$ûŒ\ê\rP‡\rT*†”\Ï\\öQ«X\Øx“7¾|J˜]˜¥¿|=¢ž±|m¾J$pý«_ÁýÁ \ãªPQ©°õµ/#^R\Ú™\ïf\ë«1Ž?f\Õ><L\n\Ä4£\Ñ\Ò\Ä\Äh3PewoŸ““\\\Ù„ª\Z²²¼D¿\ß\Ã	Í¢¾\Ü;Ú÷M\\\"\à\\VI¥VF#®³À\êµwX\Ú2D:©ôW:¨sLuœv^¡Që¾‹B1³Âµw¾™”kx‰\Ç$%‡½Jr«nÐŽƒ«¿òU¶¿ö\Ôyˆ¹}q™|[h\îøQ\Ûu\Æa ‹\É\×~©&ùc TUª\ÞÌs\ìò\ßù\'|Qb\"ŒFcº’7^Ç»ZK7Ÿ4g§ú@\ç{ÕŠú\ä$\Ò\Þ“	\ï\n\ÅÌ‘\îh“\îø\Ó­ž3IÍŽ\ÒY$\ëIÁT-pùT…J\Ã\æ2P_\èL5\Äb\×a´¦BiB\êPVKÿšó\ä\Ï^˜óLä· \Î7¥¿©f[øþ÷¾\Ï÷¾÷½¤P\Æ\Ô\încc“w\ß}7\r8€^ÜŸúS\ÐyÀ´E\Øùm¿.hj%\Ý\ÑyblÖ¢šV¾b´\ã¦\éµeñ,MD<Û²{\àU¤(0f\Ò:º¶„\Õ\ê\În\à4\æÖ¤ ÿ©øùXð\0„`8\'8ç¨ªŠ\rWó7ó7œžž¥{K\åþ¯ýk\\\Ù\ÚL\ßsIdXŒ¨Y1\Øó\\>™­ILšµ”\Æ1±ªjð™<\è9?Zb\Z³Jº•S¬9™*\Zk=\ì#‹mCô•¢Z{x\Üy\ß5\à\éX›Lýy‚ü\Ä?\å–\0¡\É\É\×ô\ÈsJˆoÿýwøö?|\'YT\ê	YQþ\Õö«ø\ÂC¤(ê¼•I.I[—a»\ä¼#B\Ú z\ZIÔ¯\Èb}\0k*¬‰Ø§#O¢P1·n9\Þx\ækw\Ä+ˆ1(,Ýœ¤v\Ö38\Åuò›\éfm\ß\ë\Ó\È7¿8?ÿ\ÙòI\é\ã*a4®ø\Ç\ï|—ÿú‡\ÈÃ‡\ï‰UD]Á·¾õ-\Þ}÷+‚sB1\Ý\Ò}§Ü„c´y\Å©COýQ[„Mf\ä‰2“öosúS\ÉQ9¸iµÕ™¹$‘\×:üD¸\'¡÷\ÙzŒŸŸš†U\î¦-u\'`²÷,Gûó\Ì=õ¸>D‹8Ÿ\îMeÑ°²Ë£¶4\0ŽN\Îø?÷÷üÿþ\'ü\è\Ç?!J\Íþ#W¶6ù\×ÿ\ê[¬\Ì÷RO?MaZwªÿž\ãŒ/™ž\á\è-\ãjÊ•_Œ¤\ØDÎ·DIÁx¥¥X¾Z°É”½”\Ñx\Ñ\Û\ÍýI\×SÛ©w=•&2d­7\í\Ò\È8&s=„À7oóÿ\ëó—ùW\Ü\ßy€ª\Ò\ív8<:dmmÿø{ÿžw¾|§21÷ò\ÝÿÈŠv{(Ÿ\Ê2y\ê?\á¨aª‰v\ëô/›C	©½¯›ò¨\Ö)¦õ!^5n3E\Ùorñg­§umÙ³w¹Y$®­N©\Æ#¾û½\ïò\Ñ\Ý]\î<\Ü\åýŸþ”;w\ïs\ï\Þ]b¬\è÷ûŒ«ŠÁñ1«kü\î\ïþ¿ù­ÁL·\Û\ïOvã´žó\ê’|\Ì\Ë ñ\nc\å	zŽ±>\Ïeùt\×Z\ZeXŽONø\Óÿùgü\Ù_ücSª|<3ý>1NNNP\à\Úö6¿ÿû¿\ÇoÿÎ¿B\Æ\ÃQ¾]\Òif/\éEÉ“­(ƒH@§gcF!PtKJ)Wcp¢,/.ð¯}÷oÿ\r¿ôK_Ç©\âc8%³\Ü}\áR£/\éÈ‡Pa8b¶*\Ëma]\ês3:=¡,\nfú}\Ö\×Vy÷\í·ù\ç¿öM¾þ\ÕwY_]b<ªp¢X)C5·\0oIªv\ÅKúù!/¹ý{½¬!Dœ÷\\»ö\Z\ßüµo²°¼\Ä\Ò\Ò\"×¯¾\Æ[o¾Á\æú\Zs3}\ÔÀ‚\Ñ)Št+\åüºv\Ì>q¯„K\àüÜ‘ŒªSI7v’\Ò<®\"§gg\àWxœ\nN\'ù–;…\æxHK¿iZ³>\'—\0úù\"©\âÐ¢)¨oü†c\Êg­ýŽ\ìC‹\Éu\ïNš¨j\ç‘Æ¼ý\âU„]\Òó’r[Ú¬§d Îk%~\Õï‹ž3¿\ÛEkM\0\å’\Ãü\"‡z©­q\ÃKŽCi]S¹QrP&\à4µ\\5»jn¶y\Ér~Q¨	Œ&µ‘\n\çb“Y.§r \ç€\Ó\ÐÁxI/‹¼5\é\ru—Ò˜L‰#Å¬h\Û{<¹‹\\‚M‚\à+\íª¿¤—F^,W561#ÝŠšúT¢Qƒg\níˆšq©%ÿb‘\Ä-EŽ\Ïò´_´_œS—\ç‰þ?\å\ã7¦SŽ\0\0\0\0IEND®B`‚',1,'admin','2016-10-13 14:29:10','admin','2016-10-17 23:10:00','','','',0,NULL,NULL,NULL,'0',NULL,1,0,1,1,'org.maxkey.authz.token.endpoint.adapter.TokenBasedJWTAdapter',NULL,NULL),('850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','http://email.163.com/','E-COMMERCE','57f4c775d02eb00e4f8c62ab79037d8f949a3ac684791843026cf902ec000e2a','Form_Based',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0}\0\0\0~\0\0\0	‹\0\0\0	pHYs\0\0\Ä\0\0\Ä•+\0\0\0tIME\Þ\Zq*Px\0\0\0tEXtAuthor\0©®\ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬\Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0À\æ‡\0\0\0tEXtSource\0õÿƒ\ë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨\î\Ò\'\0\0 \0IDATxœ\íy|Å™÷¿\Õ\Ý3#nK–uÙ–Á÷‰\Å}\ÄH \á0„%¼	²„#l°À	,X$‡+a	$$„BX®p\Øc[>dË²eÝšÑœ\Ýõ¼ô\è²u\Ì\È:\Æû\ÑO”\Õ\Ý\ÕO?O=UO=õ\ÔS*\Ðö\"{]\Å8R„Rj\È\ÏZ©> \"8\áñ¦fŸ®!¸f-‘šZœ@;:2!\ã\Ê40²³\É(+\Å?crZŒ·x\"Vv†i&]OJB6·Pÿ\äS4>û\"Ö¶:<!¼Z“©5®\í£´R´›ŠFŸ»¸ˆ\Ü\ã–R|Ú‰\äÎŸIô\0j°\î]Dˆ7·°ó\é\çhþ\Ýñl«%\Ó\Ñx\0• bcˆ(\å\å’s\êW(;\ïlü•S\ìþºÖš\Ð\æ\Zª¯½õþGˆ+\èq¤\'‚@ d\"•·ü„\Â%G`Z}w\ä\n½ñµ¿Ss\íM\ä\Ô71.ð}¶‚\Öl?y\ß9—)ž‡•™¹\Ç=}\n]kMó+o°\í\Ú\ÉklÁ3*\äŽc¸ V¯Eö·\ÏaÊ¥aefö\ê\î÷ºÖš\àg\ëX÷­)llÁ\Z\×\ï}M“¢ë¯¦\âÜ¯÷z¯N_Dˆ\Ô\ídÃ•×‘\ßÔ’¸8nª\í«È‹\Û4\Üy/™S\'3a\É†€\Ñó&­5;}\ß\Ú\rx\Çe½\Ï\Ãr[Ú©ûõ8¡p—­K\èZk«V\Óö\ÄS\ä  \ãeŸ/þù1u¿ÿ#¢õnB·mvþþOd´¶jk\Ç\È\Ã\ïOü™x[ZkW\èZk\â­m\ßx‹\Ìÿ£Ýºˆ¸.d­±µC\\;D¥»\Ä\Äý­5Zk·+ü?\âeô\0Ö¶:k>GD\\[MD¬úo[{\ïA~‡\0¶c²\ãD¼ñ	Y¨	É˜5\ß\ä¼\Ù9]V­¶\ãDZÚˆn¬!²yFs\Þ@¿\ã\á\ÉÀ4öm\Îd\Æ\â4½ø\Zù‡’ºÖ´þ\ã]|±ø>o«‹Q\Ç&\ä5`öþd.Z@ñŽ göl²KK\È((ÀðxP}	Q@;6N,FG}=\ÛjiûôS\Ú\ß~Ÿ\È\ÊUx¶\×\ãa\ísž*K \å£\Ä\Û\Û\Ýyz¤£ƒ\ÏÎ½ˆ¼Vî›š.‚\0Qqh\Ï\Ï&\ã„c\Øÿ»\çQ´p!–\Ï7,¯ˆ´¶±\í\Õ×¨}\è·ÿü„¼¸`*…RjŸQ”†ülfýþ!,­5NKö\Îú±¦i\È\Ù1S&Q~ñ\Ì^v\"Yeež\áõ#f\ä\ç1\ãŒÓ©ü\Ê	´­[Ï¦‡~KðO%¿#Ž§w:A\0jhÀt8Œt„Ø—1\"`;Á²	œw6\Îûþ\ââ¤–÷ž¬,ŠªSxÀ\"\Z\Ïÿ›îº—Ð‹¯“u\Ò~\ÜWŽƒ;\\¡£u\×zø>!v6Áƒ\æp\È\Ã[9u\ÔIP†ÁÄª\Å>¼œ\ÚW_£ú²«)\ØÞ„i˜i;\Þ+h\é9„\Ë>!p¡9\Ã ÷GW°\ä¯O‘;uÊ˜z?\Ã`\Êñ_¢\ê™\'‰žx!\ì±fQŸè”­v\ì}\Ëns´¦mb.\Ó~ó\æ\\q\Þì¬±&©ù3¦s\à\Ã÷\ã»ø_\é0\ÓwŠ/Zw/¸\Èn§Ú‹óXøü_(˜1}\ï\ê\ê9\îkCu_‚\Ô<,¾ù&Ö–”\Òð\ï?%/ÛŸ–]}\ïU6Ò’F´h\å…\Ì^þK\n¦\ï?45R\n‰^½–Ž÷? ¼\ê3b›«\ÑÁ \â8(C×‡gB1¾…s\É:ø@²;OQ¡û|\n\ïœy\áw0=š~r~;=Ô¨\'V¯\ßJ\Zjº\á|?3ï»‡I‡6¤*\ì\Ö6o½C\ão!ôþGH0¦	}LµÂ²^~ƒ\Ã\Â7»’\Âs\Ï!ÿôSð”•$­¦\×\Ãô\Î\'RWKøÞ‡ð¥t<2H\×\è\ãwi…°Šo¸nH¥\èøhO;‹šs¿Gð­¢cø|}\np»w¯LƒÈ†­\Ô]3¾x*\×\ßLi:hs¯½óô“G§Lûp#¡\Ó\0»r’VÅŽ\Ç\È:\çtö;\'ù‘‚U­#v\ÝñK6~õ,B+WCb¸v\å–\Äû;\ïU««gË·.¦þŽ_¢#‘¤i°2|Ìºþ\Zeyh­ÇœŸ\è5eK\'±kÂ“ò©üÁ¿uE|$\í\Ø\ì¼ó\×\Ôýøœ@c\ïXe(œ`˜º\ßL\Ó\îÁ¾A BÎ”\ÉL»ó6â¾½£axD\îþ_\Úvï¶©˜zË\ä\ì7-ùA;;\ïøõ·\Ýs\Ð.Y‹ %	…<u?ù·\ÞN‰\Ù\'\ç\Ø#ÐŽ3N?öP¡±n‚»¾¯Ì¤\ä+Ç“’³hõ\ï\Ô\ß}/‚F‹!{ö\"š6C\Ó<½‚ð\ÉK‰œv%Ñ”—AD\ÛýÓ¦Nkˆ\í?¹9¥n*.¹˜¨\ß\ã®\ëO{\Â\Ú]\Ð\é0e‹‹fÒ¹\ç`ùý)=g·´°ýúŸ¡Cqw\n\Ö­i.ò3\í?\Ê~§.\Ã\Ì\Èp/hMÛ¶Z\Ö\Üô3\ÂþX}\ï1\rÂŸ|F\Û\ëoRp\â	]m0v(u\'óç¿¡Æ€\Ë=eœ–šn\ÌÙŸ²¯.s\×R\Ðò†Gÿ@d]5ô§\áZ›3\Ã^y\égü¦\ÇŽ\ãò*\Ê9ø®ÿ&\ë’óq@#µPw\ë\Øí¤\é3ƒ²s¾ŽcöM\Ûhjz—\Ðûº8&p4ù\'‹7/7¥\Çt4Jó\Ë\Ógw%\"D}&•7]O\î\Ô)ý\êš\'3“Ù—_†ž\\ºŽ(Ed\Å\ç„>]“<\"\äÎŸ‡ž˜\ÏXl\í\îù\Æ^š®1ÿ‰\Ç:(8\æ\èn÷h’Z\ÞòòkDª7õ[/@\Î\Ë(;f\é uûòr\É?õx\'\Öwm\nPŠÀ[ïº†b’tzóò\È=ñKˆvÆˆ»»½“cXD:7ÿ´i]\ÂL:£ñ‰? ±x¿u;:F\é×¾štKE|\Þþ\é5\r‚«?M‰N¥ù_:mc\Çgúð½)DðÎœŽ·¨0¥.0\Ö\ÔLð`˜}ƒx3Éª¬LzÚ”5­ÓŸ‰\Âý\Þ^½\'\ÃH6rF)òX@­?	F’{f&Na\"±x¦×›\Òs\Ñ\Ú:tk+\Ê\èÿsLÓ¤\æ?~ŒJ2ŒJ‡£8³†Ý¼‹xs3¾\â\â\äMtñfYzý¦\äž&ô´\Ù\ÒKè†5c¿\îq2IDk¶~C)t$J\à…\×S F¹Ó¾œ;‹©oH^\è€\áõbfK’\áFzu\ï\n2§Tô2\Ð…¡ˆ\Ôn5ýJ¹+k)`0\nDN8”RE#+k\ÔyÝ¯õ>\Ö¥ðMH‰(\ìP;¢Æ¼\É&¤m€\ÂnšžŒIÉˆS\"\î6$÷á‘¢ªOh%\îbNŠô\Òk5:p\ß\æU{4¹1»h¢\rnW¬“[ƒ‘D·-\Èh»7M+/¯“¤@lgLùœV\áR\"Š\è\Ö\í)vï‚™™ƒˆ\ZpAM‹\Ð\"ô^²\ÛÐ‚r›ûsrÉ˜Tœò˜®Á½¢a(\è\Ózý§hˆlØˆH\ny\é4ø*\Ê\ç»a0\ç\åX…{E¢\át„\0…\å\Ïpù¤\âHŠÇ‰7Æˆ\×\î[÷ˆ‘KùøSt8‚™‘\ä4|“\Ë\ÑØ˜2ÀÜ‰0¡´„œ\r­C…\Ý\ÜB|ûŽQ\çµ\Ð\Ý6\Ój•\r¥¯Û€\Ý\ÔLOŸõ`%£¬—?\àZ5\ÊK\ãÿšø\È\ä\ë\Ö´¯\\…E\Æ\ÂûÚ…\Ý\"g\ÆR\ä\nœŽ\06ô\0\ÑWñN(ÀwTZ\Ûý\Þ%\r=N\Óó/ö»ô:\ÒE;­/¿Š\Øý\Ó9R¥\ç\à½G¸\ÔX\Ã\ë§\í­·SZ½2,‹Ig}\Çkö[/J¡£›.½†–7þžRý]E)w_û5\Ý	i}áµ½Ž\ÙJé‰´r\Î\0`(Z^x…xsKòÏˆP~\ÌÑ˜óf\ïù…=¡À\î\è`\Ý9²ökß¢ñ¹¿ojv€a\ìYÓxK+\í¯d\Çý\ËY{\ÞÅ´~øq\êß¥•Ÿ`7µŒø\ÎÚ¾Ð“-\é\å†M Z]C\ã3\ÏQú\Ío$ýŒRŠ’\ïþ+5ß¿\ZŸý3V)DCÛ›o\Óö\Ö;x&9}óf\â\É\ËG)\Ñv$D¼v\Ñ\êjbM\r\Ø\ÍAw7Œr«\"ÿ *R±\ÚÅ¶\Ùõø“ˆ–1:t\Ë7­\\:!\Ê`×£¿gÒ™§c$›IB„É§œDÝ‹/\á<û:\æ`Œ5MD„X}+±úhûƒ=«\Ü\í\ßÊ›vœ\àšÏ“£©\Ú?ø˜\Ö\ç_F_H\ÏoI»1]\0ƒÐºM\ìz\ê\ÙaÉ™¦i²\àG\×ž=…¸3@Tk\ç{:w?(\å\ZŽ»v+.m\áu›D\\]rV»¢\îþ\Ü\Ý5cdDö|\ï1}¬¦2}‰k¶þ\ç\Ïé¨®N\é\ã²+\Ê9ø¡\å\Ä\Î\"¦d¸iSŠX\Ý6œh49š\Z_z…¶7\ßqí„±\âg¡(-5]\0\ÄÚ©½ó—)`\î”)ò\Èo\Ð\'CÀŽ0¼Š§9€\ÓÚš-‘º\íl¼üjœH|lù\Ù½¢a\Ó\r\Ê4izö%\ê|´+\Åe²ÈšTÌ‘w\ÞA\Å\í7œ9…€\Ã\Þ-T*F	‰C\ì­&´n«”\'f\ëþvK\Ç^´3\èù=ii½÷‚-l½õN²\æ\Ì \ïðCSzÔ“‘Á\Üs\Îfú©\ËXûäŸ¨}òiôú\rdF`HÂ¨\ê%\éüQn\îô˜\"^…L,b\ÂqG3\ë”eW-vµ¹ˆ\Û\îù%\rO?‹2¼c\ÎÛž\rYµ4·Hh\ãfVþM2ZR˜&D°òüÌ¼ÿn\nŽ:r@f;\Z%\Ô\ÐHË†jšV¯¦ñý‰\îÜD£\Ý7)P^V~¹³gP0y••\äNBF~>–/£\ß\Ý3]\äjÍ¶_\ÞGí¿F\âzÌ¦h»#bL¹\ë\æôZZ\íJoQ}\åuLÿ¯›\É?ü\Ð!1\Òôz\É)/#§¢œ)\Ç.EN4Š\í±¦Àôú°|npf_9b\nš ú‘ß²\í–;ñ\Zž”wÜŽzR¼[v©ôýAA´¶žµ\ç]H\ÓË¯ub(Ö¬\Ö\à8ˆ\ã`X\Þ,wñû1-I\\OiÓ…¸K\ÂEK\Æüú2š\nüD8\"z\Ìù\'H_B\ß N\Øf\Ý%W±\é¦[ˆG?!TV²\äö\Û8\î\åg)¼\îr\Z2 f§\ÏA…i;e¨D\"a\Ö=õ,;?úxhš>\nE\Ù\Å\Å,¼\è–¼ð,\æ©_$¨º÷Âv¡\Çß»9gÒ»\Äm›&\'Fü+Gs\ä_Ÿd\Ê\ÑKH{ˆ0aZ%Kî½—©·\Þ@«¥]ÿûXIž4Ÿ²‰¶vˆ ‰M, d\ÙI\Ì9\íŠ\æ\ÎÁôx\Ò&³Crfž~\Z\Ùå¬¾òZ2·\í\ZU#¯§l\Ç&y ¸ÝœF\Ós¾ \ZÁ…\ÎÉ€\â‰d,˜Å´ãŽ£ò\è%dô\îF“E_–þ§}{¥\å‡†\çWw²ò{—“Q»¥Fß¬\ê¡é®…7\ÒS6h“(ó\É?ø t$Š\nƒRøJ‹É›V\É\Ä3)˜5›\ì\ÒIx22×£•¢WÀi~ûröŸŽ·¼33\Ã\r†0\Ý\\ž©zúH<7Z\0Ðš\â¨Z~/~\ã\\²\Ú\"£8wú¨Æ½k\ÑD¦–2÷\êË™¼\äX™¸Í…Jc÷ôan°)SŠ\è®\Ö]öCZ\ßyÃ›‰§(oqž	“°‹ó˜øµ\Ó)«Zœ\\CWŠ¶VRÿô_\É(/!{\Î<²\æ\Ì\è\ÞÇ–\"}…³g1ý†\ë\ØüÃŸâ‹\î05j\ÎÁ\Î\Ë\æû\îa\Â\ìYý3)Õ®»tl¨\æó+Hp\å\ç(\ÓB\Ûq¢;›ˆ\ìh\"\æ¬&óŒ“X0^\Ò\ßÝ±“µ?¸’HMˆF™ž	Y\äxÿþ}\nf\ÏJ‰>L?um¯¤ýwOº)\ÃG{X\ï#=\Â9ŽÍ¤ožIþŒ\éH\â4¤‘*õ\ÏýUgOp\Õz\Ôn\Ì\ì\Ð6\Ùž\Ã!7Ý€\áñ$UŸc;lüùDk\ë1<¯e\Ä[\Â\Ô}¼3+kÈ´Î¸\à\ÛD\'\ä 2²Y%{\Zñ£f½YY”w\ìÈ­6)E¸¶–m÷=Dý“O£c6*€nOô)*.¹„…_\Ð÷Á=}A„\Ú¦á¹—º\Z;\ßñ*¦]{\Ù\åeC&;»¬Œ\ÊK¿G\ÝM·cŽ \0ú´\Þûº8œP^oNÎ°[\Í\èp˜\Æ7\Þdó\Ï\ï ²­eY½¤µ&à·˜ýó™yòI)!Bó[\ïRó‹û\é\ÑXS\Éü3Ne\Æ\é_Ý«!I•\ËNb\Çò#4)‰?º„®\ÄÕ†Óƒ!\"5[\È®£7”\Â	‡©{ü	vü\á\Â· Zº¬òN\Ä›øü\éq\ë-\ÎÀ–\è£þ\æ¼Ãš‹.Ç‰\ÄzõPŽhœ\Ã`ÁUWK#ö\ææ’»ôhZÿ\çO˜\æÈ„-ºe\ë]\âqv>ñ\n?|\è\'))…\n\ÑQ½‘\Æ7Þ¤\áùW	WoºC–{jw\Èg\Æ2»\ìûä–”Ê©Š­¯d\Ýõ7\âD¢{\ÔŸZ\Ê·\ßJfAþ°]e\'}…–?=‹\ÄGÞ’5\ë]™Mo¼Eý/SrÊ‰)ucb\ÛD\ëw\Ñøú›\Ô?ýŸ–«Õ»\å™\Ñ&\æ3\ëº+™sò\É\0‰,\Ì\Éªm\ÝÆš»Œ\ØÎ–^c¿ˆ\Ê÷³ø¶›\È.M4¢AêŠ·µƒ\Öxj J1aÁ|<SJ\Ð·\'GgŠ\Ð#7¢ˆk\Ö_÷SZ\Þ~Ÿ²3O#£²\Ó\ç\íj\0¢5N8L|W#\Ñ\Û\éØº\àª\Ï\èX¿\è\Îfœ`q4\Êðõ™n\ÄÑš \'\Ï\Ñ\ßÿ\Ó*S£O)\ÚV|\ÂgWþh}\Ën† &\è·Xp\ëM”x`R\Z®cq6\Ü|+\Ó*™vÁ·û7bEðø3Éš7Ÿö\êm0\Â^º\ÑMJ N\Äf\ç“OSÿÔ³xŠ0½þ®\\©Z\â8‘0NK‡›\Ë\ÅP(eõ\È\ã\æ\éI£\Âv\ÄRX\Îg\ÑE\ßa\ê¾\Ð\í\àI´¼ó>ë®»È¶½A!Z\ÍÜ›\Â\ÔcIª^QPûû\'¨ÿ\ÓóxJ‹¨øú\×ð\æöŸS)E\î\ì\é´>%¨ž²M\äŒi!±úV µ\Ï[”7#©ªñ(‘ý*Xx\Õ\å\Ì<þKC›*E\Óo±ú¢+\Ð\ÚÙ£Ko\Ï4˜wÓ™þ\Å/Þ¥\'Ð±®š-w\Ý†A¬v\'‘\Ú:¼sûºh¿´,aŒ¦þ	ƒ¡\çB›µû¥t[i\ÛŽvy@\í7\çŸË”cŽ&kÂ„!\Ô\å:H¶>ò;¶üb¹»\ëU©..h­\é\Èó³ð–Ÿ2\í‹\Ç%-ð\Ð\Öm|z\éØ0¢…PM\r¹sgH‹·pˆƒ0²ª¾O\Ä\Èuz¯\âJˆz½x/bæ™§3\ãø/aù|]\×Sb\Í\Ílº\ëW\Ô=ù”\Ûw\Ú	c0VRL\ÕýŒÉ‡\ìjJ\ïpBa6\Ü|;¡ê­®¡	`*\"-Í‰}tý”fv\ÚpFD\ä:g\Ò\r1\Û& 6zZSO]\Æü3¿†\âÄ®-\Ã)gTqw®¯fõ® ´±\Ö]u\ë\Ñ\ÚÑ„\çOg\É]ÿMþ”\É\î;’6Ä¶Y{\íõ4¾ò”\Õ[tŽm÷z_t)\ËB1ò‡üô²\Þ\Ç`‰\Ù}wBxZ4q­±½ñœ,ü³fPTu\0s:‚¢3\É\È\Ë\ë\æW\'±)\í„#\Ô=ý5¿~˜\è\Î]]¾ù\Îj:p\È>ñ8Ž½\æ*²‹‹“~Ž\Ç\Ùt\ßo\Øõ\Ê?›#{~½½v,6i\\G\Ùp£\Ï1}Tƒ(/„ˆ	Ñœ,(\È\Ç[RH\î\ÌL:ð \Ê-$gR1Fgž6‘Î‡RGÂo­e\ãÿ‚ú\ç^BY\Þ^	„µ¡“ò‹¿Ã|·;ù@2Ÿ\"\Â\Ö\Ç§\æžûAyö\ÐdwC¤{ó@\á&`YŒÙ˜\îˆ&\\Y\Â\Ü+.¥´ª\no–\Ó\ë\Å\è\\O\ï¤i¨Á\n= #Qjÿø5÷=H´©\å\é½ý9f\Ç	\ïWÆ¡7\ßH\é¤4\Ý­\Ùò\ÛG\Ù|w\ßw\ï<YYƒ~‹\Óm2ƒú\0cú\è\è¹A\ïWÁÑ¿¾‹ü\ÊÊ‘{m\Óô¿\ïPóð£´¾÷1(#Ñ \Ü\ït´¦\Ãk0ñÌ¯r\ä\Å[R’Zýñ8[{‚\Íwß‡Ž\Ø=\Î|\Û\í>2K\ÊmHvsˆ\ÑgÃ>§l£Õµk¯É¼ÿùS§¦nˆ\r…«}›·°ñ\Þ\å\ìz\áeWØ‰¹w§M¤²”~t-ÓŽ:Â½–\n-ŽÃ¦\å²ù¿\åA©¾»eÁ\É6ÈœR>hý\á];Á™\î}À—Ñ€¯¬”ÒªG\ä\ã\Ú\×o \æ7\Ðô\Ö{\Ä[;@õ.lÇ¡\Ý\ßü\ÏÿY\'¦ü\'a\íu7Pÿ\ê›]\ï\"‚¯ªŠŒÂ¢+5‚\ÛjG%^n·À\ÈÑoR!¦×³÷Ó…ƒ\ìŽ\Ú>]M\ÝS¥ñµ·N‘\Þ\Ú\íhM\ÈRd/=‚#/ü¥¤\ìª\×\í`\Ã\íwQÿ\Âk\î<¼\r\ï„ö˜\Ìú\×sý^‹ød5(c\ä4@dl;M\íh\ÛZ\ÜwO­\r…ix\ã\Ô<ò;\ÚW®I¬º™]¾zIX\Ã‰*/\æ ]\ÃþK\ÚtO)\Â\Ûw°\â\Ò\Ë\èX³q5€>!å“˜ºôhw_\Ü\0u‡\ê¶Ú¼KzLº÷p\ív\Ú\Ö|FÁ‚ù\É?Ô¥\Õ!ZV|Â®—^¦ù½‰\ìhpjõ¶œE„°Ž\ã\ì?…\ç|ƒ\é_ù2yyC\ê]a\Çó/°\î\æ;ˆ·´w{\ÚzF„Âœ‹/X\à‰{\ß~‡xSÛ€G’\ì\rz\Î\Ìzr£Ô¿K(\Êú[\ï\à€»\ïÀW8°\Ï\\G£D›[n\ÞBó»\ï\Óø¿\ïÚ°	wPV\"#±)\âúË£&\èÉ“(9\å\Ë,üú\×\É.*\Z²Áhƒl~ðQjyÓ€18ŸDˆ+\ÍÄ³\Î`ÿ¿<\è;$g\×¯‚$Q÷0`L¦lŠÖ\ÖðñE—2÷§?\"\Þ°L\Ä\ÑnÀDS3M›hx\ë]Z\ß—À\êj´Ã°:(7®½\"BÀŽ›\\\Æ\Â\Ë/a\Î	\'¸\',’¢U\ÞE£A`ý>¹ü*‚j0RcŠH\ãØ£8\èŠ`$\Ñ+„\ëvÐ¾ú30º§“Ã´ð½+¥h_½Ž\Îý.þ\Êr¬¬,7€\"$\Þ\Ä\é\"v‚Te`z3÷¨CDˆh›X–ŸœC«Xt\ê)Tr0ùy]×‡\'¡\æ‘\Ç\Øúøˆ5´&-p-B‡!”6U—\\Œ•\á”q6-\'…-N}L\ÙF\Õõ®\0e\à„c\Ön\îû†i>zºKm\Ñ\Ä}º¸¢¥K˜u\ê2&Í™½\×\á\Õ\"BûçŸ³\ážûiúûÛ®_\ÞØšv3O1¥p\Ê\'2û’™}òIfrlû|;žq6”u#\íW\ÙÀe¬­:´M¬¨€’c–0ÿ\ä)[¸\0\Ï\×ô0\Ô1Yûùo\ä³Gÿ‡hG¿¾~´Ü¥IÓ¡\ã8SK™u\ÎY\Ì=õT|9\Ù\ÉOE\Øü\Ð\ï\Ð1gÄ³OuR£\Ø}•mD_›<l\í\Ûc¡\nóÉ˜RAÑU\Ì[¼ˆ¢9s\È*(\Ø#\Ôy¨\Ðñ8u/½\Â\ÚgŸ\'¿jG,¿_A‘\Æ&¶¿ý6µ/¾‚nowô4\Ê\ãÁ\È\É!³¼œ\Âs™·¸ŠI\ç‘\Ùy–K’­\Ùò»\Ç\ØõÚ›ƒ\Îõ‡Bšiº!\ì10\æÍ¡d\ÉQLœ=‹\âÙ³\É-.\îN\ÕÝ‰½¸vŸ¯g\Ã=÷³£ú3ªn¿òªº\ë:…Šƒª8\à\Û\çhn&\ÒÖ†\éõ\â\Ë\ÊÂŸ—GFNŽ\ëò\"mk>c\ã}A\\F=r%}’Ds³¨ºõF*>\Ó\ç\ë\æ\Å^\ì \é¡õTÿ\ê~\ê_|h È¬[®§|ñ¢=·‹\àóûñùýPQ\Ñûšú@m\ëÖ³ò\ÊkˆÃ£šT°\ÏyúX‰]D°½&ó®¹‚\Ê\Ä\âÇ°¿Ck7±\í\ÏO±\ão/onwbòýT\ìEnºÔˆÚ««Yu\í	\×\Ö\'l‘\Ñ\çy\Úhzn\Õ\"öûòñ\Ã^¯8šP\ÝjžøµO>Š¸®Z\Ã\0-øü¹øM6:Dh]½†—]I´¾e\ÐYÁH\"-\ÆtQP´x!¦e\r›Æ‰£iY½†-þž¦÷>$\Þ\0T¯­Ë¢@;Q´mw9rF\"Â¦\ß>Æ¦!\ÞÞ‘üŽ\Ùa%¢›µc\â†\í¦Ç³w-_¹*\Ñ\Æ&Z>ø˜\í/¾Hó{uk6Ý®\Ú.Ø­\ítl\ÙJÞœÔ’\n$ÑšÀ†j6ü\ê\Zþñ¶\ëlRjLù\é¢\é\ík\×#Z§lØˆ\â8k¶²\í/O³ý/\Ïc·¶ƒe¹u\r”\áA)$®\Ùü?±\è¦†ME„hs3\Õ\Ëf\ÛŸB\ì\Äw¥I|y\ÃøÆ¶ù5¿ýM­ è ªþo\ê6\ìŽÁ-›\Ùù\Ò\ë4½÷>Á-[q:\"€;—N\Ê0\Ùñü« 5³.»”\Ì\ÒI\à$¬ød\Z H×ŽY;\ØAËªUl\ê9\Z\ß}ŸXK QMšH»·Vzü9P\n\'\á³\Û\î`ñ-7‘]9u­E:\Z%\Ö\ÒJ`\ãFš>ø˜¦?\"°vC\"ã„™8r£;h\"%h¨{öšW|JÅ²“(^rþŠ2,¿eZ½\â\â;#sµ\í £b-­7m¦i\ÅJ\Z\ßÿ\'Áõ›‘˜\Ó`1$zF‰\Ôß›øè´³56Ž)1\"\Z3\Ó\Ë\Ôsþ\ÅG•\å\'´u++V\ÒòÁ*BÛ¶\à£½<lZ”\ÉF\ÜTÝ¾\Â|ü3§“=¹ŒŒ\Ò,¿»·.\Þ\ÖN¸¾Ž\í;ˆlª!\ÖÚŠŽ\Ä]z”;K\Í\î\rË²˜}÷m®¦»&#·¬—,”R8\á8›–ÿ–\Í?Ž\á1p\Â\ÑFwþ·^&š;‡\ÜDcŠ6µ}\çZú­\ßpŸ\éEÓ¨\ïH	‰@¢„\Ð\r…3\Ä}\Ã(7õ¶\ã8	\r\ê¾<j4&VCZð,IˆG)\Ã0pP\Ä÷­$\à\ã4\n•˜¼\ZŠ ¤Ï±\ã\Ä´¡°\ÃÀ0:\ÈT\"ûRf\åq$¥\í\â ,\Ë\Õt_v6dfa§\Éy#\ã&\íh|99	¡O( «¼„fôi5Ç±\ïC)\×\Ý{ô\0\0\nIDATEX)”ß¿h‚k½›–EÉ‚ùlz÷=\n/¦vö-³tB™&u\Ú&¯t\n\Ùe¥\Ý	§»\Ãò°C\Ì\n¸\Ç\èC-\nhÊ«“YX\Ø-ô¢ó\È/*¤Yl\ÚL56\Ë\ã^(…cšl\×q2=f,;\è‘ú;³ ŸýŽ:\Å:\'J\Ü4	ú\Æö\Õ\Ãò°^b\Äò\n˜Tµ¸·Ð•i²\è\Â\ï0¡°X«c\Ä-Ï˜œ12Ž½‡²¼TKœ Y†\Å\â\ïžO\æ„`·#ºŠ\Îcþ¿œN&Š\Â\ç:FÔ²\Æ-ú}Jˆ\åe#6Mâ‰bò\Âù\Ì?\ï›]Î·=\Ôx\á…\çS2y\n™(¢Ÿ\è(¦ey\Ó&`}\Ã4-\"–\Å\'¥I|(²L‹C¯º\ÌÍµŸÀBÏ©(\çK÷\ÝMqi)(L›tŒ5\'dzP–§+Q\îxû‚R¦E\Ü\ãe“Ò¬\ÖQÀ‡\"\Ç\ë\ãˆ+~@Å’/ô’±\n´úœ‘o~þ%^¾\âjš[[‰!\Ø‹Á$\Ã\"…G\Üe¼ŸÜ,:\×\r0\Ü³Š]b\Ó\"˜(¼\n²=^:ûl»ñ:L¯·w-ý	]´fË‹¯ðò%—\Ñ\nCˆšD‚;ŠL2•\ÂRj;­wßƒ\0¶šh—,\Ü.\ÛB\á2Qz\Ñw9øÚ«°|¾=\ê\éW\è\à\n¾þÃx\çg·QóÁ‡D´\ÆV‚# \éœ\Îõ\èj\Æ1\â\è4«@%–I\r¦(¼@Qy‡|ÿ{\Ì<ót<YY}\×1\Ð;Xußƒ¬|\àa\í\íDµ\Æ´\ê)ðq±T\"C¾7Ï ded°ÿqK9ø\êg\Â\ì™?ŸŒ\ÐÁ\Õú`\ÝªŸy–õy††õ\Õ\Äb1\\q÷\Ú6n\å/zHH%Š	xƒ¼’*¿¸”¹gŸÅ„931û\è\ÎwG\ÒB\ï‰h[\Í\ë\ÖÓ°b-\Õ	\î¬\'\ÚÞŽ\Ä\ìT«\ZG*°¼þ,²&“SQFq\Õ\"\ng\Í\"{r\Å\à\ÏöÀ„\Þ†’s}©c8Ž\ß¶\å4¥TÚ†þŽ£7þ?y	U¢xºŠŠ\0\0\0\0IEND®B`‚',1,'admin','2014-12-15 14:42:48','admin','2015-01-20 15:58:21','','ç½‘æ˜“','http://www.163.com/',3,'','','username','0',NULL,6,0,1,1,'org.maxkey.authz.formbased.endpoint.adapter.FormBasedNetease163EmailAdapter',NULL,NULL),('a40388d23cea4c5ba93bed865b81d255','Basic_Demo','http://www.baidu.com','E-COMMERCE','a3ac51c6653ec2eb0afa9ebd0ccb966f539d16e64c7450775399330aa19a8dc81e698f87c64032dc548d6ec7dc3c4863','Basic',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0G\0\0\0G\0\0\0U°Z\0\0\0	pHYs\0\0\Ä\0\0\Ä•+\0\0\0tIME\Þ0-Ú…5>\0\0\0tEXtAuthor\0©®\ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬\Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0À\æ‡\0\0\0tEXtSource\0õÿƒ\ë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨\î\Ò\'\0\0IDATxœ\íœkeUu\Çk\ï}\î¹÷ö{^\Ì0\Ó`!LÍŒ	(F¥ ‰ Xñ$‰¦¢±ðC$V>h%¦|$)-c™\ÒP1)+”šG©ñAA\ê †‡  \Ì0\Ì{úu\ï9{\ï•ûœ\Ûwšž\é\é¦[¨\ÂU5\Ó÷œ{\î\Ù\ë¬ó_½\Ö\Ú[n¼ñ\ë\n`ŒÁ{O§\ÓADŒ1ˆ1\Æy\ës!\èQÇªÚ»¶ÿz\0UEU{Ÿ\ë±û\ÏÏ½n\î÷\"²¨\ï!5\æ\Ü\ëUk-FƒF£\ÑûÎ¥…n·`Ó¦MlÞ¼™V«\Å3‰bŒEÁŽ;Ø¹s\'ƒƒƒE7	§\Ó)Ø¶m«W¯ÀZû³û\Ë%k-\"Â–-[8é¤“Ø¾};###¸²,Ù¸q#\ëÖ­#\Æp¼Ÿ	T«UŒ‘\Ñ\ÑQ\Æ\Ç\ÇÙ³g¦\Ó\é0>>NŒ±\'œg\"…0\Æ`Œa||\ï=\ÎZK³\Ù\ìP ÷÷™Bµ#©?\çyNŒóóõ´%ù•pŽG\îÄŒ¯‚\nH\ß1 Ì¯~ýgû\ï.zŒd¥€Vƒ\Ê\ÑL\É\ì7ýT\Çg\Î·€Qˆ\Ä‘4D\é¼8¼ª³C\ícFR!¬¦û©H’µ*B„õŽ\ntA2Tòô † `™ó¾\ê p\á{ˆE«$‚D¢¤s’¤¤Q\â„¼”\06‰Y\"ñ\Ò`%\Å\ã0™55‰n!Ï¤BO0½cµ †ú¡KB‰!¨EŒƒ\"1&	¨Xz/NQA±þ˜L>y\nr2‹ZXó[\Ëda\äT”DpH-e¥¯p\Ò ø\êœZÀJ\ÃD¦\Ô\ÒA˜ð€\Ñ2%.×•5;b \r¤IÊŸlhz¹O‘ˆ,,A“\nID±R\Ë\ïú(\Çº\êp6¢\Z	bbÈ´`0Lò\Æ\ß|òš\ß@Tù¿GðÎ¿ù$\Ù\Þ\æ\ØX`µ$ˆ¥”œHÆ±þ\äÈ©g³<\Æ\ç>ú´«gAL²€Á“02;~=Y^P­\Ò\Õ\nKW`·o±“Õ”\ê°(@,ˆ ±\Ëhi83<‚ˆcJrö\ê(…5”\ÚÀj—L»”’\áM3\æBN¦%\Í\è˜Qd5 8BŸ’\ÏK®ŽEŠ\àqdD¨ü·\r<9¨ñDc* m%R\Ð@©PJuƒø8…D!˜L\ë\'°¬\Ì\Ô\Å”n€`\ÒÓ $\ïI`.rj:!\äD„€¥ŽlŒDŒO“.JJ\Í(LŽb $*¦\Z²IÀ©M^\ÎjHZ¯ŠjÄ›6~…ü•QO!“•\èk+\ÙGu2-\nJ®‘ROµ Š	%\Ónug¨\Éñ’a£\Çt¼BGš¨i€võtM°HœBq°Bó9%\à%cHÁ\n”v€Lyô\ã@šÌ•N=\×ZP­\ê!ªŸ!P\Ù÷Z_mŸL‘ƒŠ\ÌÆ£Õ¸QL\ß5T\Â0	=+\Z\å$ªG½Ç©Õ¸/ržû›\Î3“~%œ\ã	!<\Õ<<\í¨—¸&gÿ¢_\é\Ô<Ô—²ø\å\ÈG+Oej/Q\Í\îµöZ_\Õ?Í½´b{öú~S‡\×G%\è]·_(\"\'š\ìz²$t¥\êi\Æ)f4Ã¨¢a\Z\É\Û\Øhõ˜rš\\»X-qU^(\ÄH° \Æ\Å´A ¶Eˆ†€ 6BôC!bÀ€\ZsÌ¤Ü±¨65\'<+_2)8¤)°\ÃKŽ\Ú&\Z•\ÜA˜>\Äj™aÀ¶þ\Úi<ÿŒµœq\ÊzÖŽ²z4\Çf0=\rûNòð£{¸o\Ç^¾\ß\Ã<¸g\Óv\è\ÚtŠ„\Óh\ã»X”ˆD=\"K³«+Žœ\ê\Å*±•QJD\Ô\â\\A~d7gl>™7ŸwŸ¿•†¡)‘\\À1Z¡¡\í \Ñ ž¼™Î‹\Ç9\"/\áöŸ\ï\åK·\Ý\Ë×¾s/\ÇQ4Ä—%\Òh¡Z\"\nY,p\Ú\Å,+ N„A$DKNi®¹ê•¼\ê\Ü-œ\ì<™DT‚P†\0\ÆDÀ:T\rÆÕ‚Amu\\0¾šsO{W¿ò>ñ¥\ÛùÚ­?¢h¯cªô	™8Y•¶-—\Äõ‰¥,ž)P\Z[¥T\'Y]\ì\æ¥[O\ã]W¼œ\ç­2d:‰HN\r…X•ðÐ£Ù½ÿ¦¦P\ãh7-§=k§­fMCi\ÆÈ F\Ä#£M>|\Õù¼\â\ìgñ‘\ëÿ‡G:-&Q¼6ˆv2”(‡\Ås\í¤V9\Î\Z2?Å¨Nð¶W\ÅU¯»ˆQr¢ÀñxÌ¸ížŸó\åo\ßÉ<Ê®P˜\n\ãð\nb»\ØX°*\Î\ßr\Z¿s\î.|\î)%e­‰\\¶m›\Ö]\É{ÿ\é‹Ü·\ï\0G\ì(ÑŽ‚,\Ú ×´ò\ÞJ!ú‚™\äŸÏŸ_öŒ–da€\ï\Û\Ã\Çþ{;÷>´“\Ø\Z¡ˆ«	\íJ¯¨‚i8ˆpŽiÿ~\Ïa¾v\Ç7ØºqˆwüÞ…œûœ“X+6°mó¼\æJ\Þý‘\Ïò\àÁ\Ã<R`W/)‘¶LE=­3\æ½X&Í´g³û\ÍXò\Ú~?»ü¤³ûJøÀ\çn\á¿\ï\í*™\Z>•	³Š™l„B3\Ôf`±\ìIF,->\Òu\'±¿y2\Û÷\Þö·ÿÆ‡>ÿmxC4–8sM›¿~û\ëXo§Y\ÓWL\"„£c™ýs<¹-“pBr\Ñ\Ò-pqo\r%\Ù<6\Æû¯¼u:ƒoŒq_·\É;þù[|ü–G\Ø\ë†	¦E”H\0-Ò¿X¤ú½\Í\éj‰ ˜´9ûÛ›¸\î[{¸úo\â‚¬\n¹p}›k_	f\æ0\Ñx¬t\èDµX…R,jC‰Ÿ\'HTM\ÍXf9\ÔJzÿ›\Ù·WMŒ¬j\Ò4ipˆœþ\ËW¹\éöŸ\àM³\Ê\rI})rÓ„š\èQb*(ŠMy\r)\Õ	xi\Ð5mnýñ£|àº¯°¯pD“cb\ä\Õ/9•—¿p\Íp\ÓCN\â4V|¡a^t\ÔNjY„s<\'­aB,\×}\åû\Ü|÷.|>Š‹1AÔ’g¬K)U\"‹tSM\nDKˆ)\åY˜ŒI\Ú\Üx\ç®ÿ\æ8›`s2…·_ñ\"66¦p=!Ì©CkLe¢ùøþ¥4¨\"ZRˆ\áŽGó\é\î\à°[M´ƒˆi 1÷\ÔR\ÙMbÄ†.kk[R•ŸcJ\æKÀÆ€‹Uý\Þd”!gkøÔ—¿\Ï;0£\Ð\åô±o{6Y,ž(­y›_0\0\æ—Ñ‹\"L)|ê‹·±W‡\éš!‚f\ÄHª`ôk½Fœv\Ði.Ùº‘\×_x#aŠ,Nc³¤¢£U)NRÍ¾”\Ø5\\÷¥0\r8š1ð»/\ÛJ®”ÈŽ¡¿:Å±Ò£!„ÔŸ³\â®\\„`3~ºwš›ô\0…\"…W±ª»¢QT$\"\êiøi6¸o¸\àL®¾\à9œ–O3\È±˜D\Å%«Ô°ª¿kY‹3Ì·\î\ÚÁ};U0Ê–\ÍØ¼f˜a¢>™,Ri:©\ë|,\Ëò\ä\ã‘\"\"|õ–»	ùHU\ÏJB0šR±2-h\Ç\Þò[/\åE†8½ey\×/§]\ìÇºH4–HŽª\Ã\Æ\0t\Èüt*\Öiƒ2\ãx?¥\n¡\Zÿ¼œ…ú\"Un{\í3\Äeþ&e³9\É\Ñ\Ôý;•¿š\í £p\çý?§K­J=B_>‡ˆPÐˆ3\\r\ÞY¼\éUÏ§\å=m:\\r\Î\é¼þ\âói„\éT=G1Ú¥\é\Ê.¸34ø\Þ]*^Lðœ=¾ž\Ü\0\Þ÷•€$¡¶ú8W0°,6§Ž¨‡šŒh„˜\nña\Ê\Ã\Ïö<NGr°\r„ˆ\Õ.Pc\È#d\å$£\áq®ºp3z\Ó¬[ˆYÆ€(\ïyÍ¹¼\ç¢3X;³\çš^Y\ì0¥+ø\Ã¶Á\Ýû¦9¤\á1p\Ö\êQˆmT=9U‘I+ó[žeJv)F=Qb-±¡K&U¤,‘ÇŽt™*\êH\ÂHb-\Éu/sÏµo¾„W<oœAª°H°X+j\àš\Ë\Î\ã\Ìgo\æoÿó;Ü»\ãf˜X:Ô‘ÕŒ\ì?\Üa\ã*A¬\"sY\Ån<JÇƒÆ²\Ç\â‘	qZ\Ð `\ÔË¡™i¼\äUS$†HPAŒÁ\Z\åM¯=«\Ï?“\Ó²²ƒs/‚h†	\é	r93üö\ÙØ²\åJ>}\ëƒ|ò«0\é 1eþª\Ò]Pejz\Z&Š¡\ÙjõŒ\ìb4elŽ¨l\0°xi°û go„ý?\Ù?\Íd°Ø¬‘¯µ¨q©5!nú\æ¸õ¶3h–a1d¦šœY\"QSØ”\Z¾{\ÇO¹\é\æ»(;\Ý\Ù\Î3©Eq\ÆbP\"’\Î\ël+\ÛbÀ°\È¢iPw\Öy,]_¿\ã!FZlkpý\Í÷Óµ-bœm?\âª\Þe÷~\Ë_}\æ\Û|eû½¼\ï-ó\Ü\áA†B,\ÑL z:6\çþƒž}\æn¹gWJ‘š&\Ð“W¼Ä”V‹%«\Æ\ÆI3{\ëlO}N9½\êÃ“”LE©7\'“´\ßZþõ\Æ{\È$2#M$!„\Êe‹M=˜f!\ä\Æx\ä\ÃÿÁ\'þôr^ºi	–R:ˆmr×¾)\Þú÷_\æÁÉœ²¹	\Ôc\Ôc´$’aª\é(kF‡\Ì\r\Ä.\Ö,~G°eˆs´7‹N3jE\Å2SÀL¾Š#\Ù(] SV)§^²½*\íKÄ»4)a”“-®ù\Øø\ái&lš?=4Yò\îø?›0´Ñº\È\ÑT\ê±\êq\ZxÎ©Ï¢\åS!s)Âe*\êY-qZ\àb‰\Õ2A»\Ù$ªI-m®	®‘\\§™\í\ÆH1‘¢¦‹ø’¦i\Ñõ\r~2\íyÿ\ç¾Á~…fü\Ýgo\âž_LPˆ«r—Ó¤\ÎT›TªŽiT\ÉbÁ9\Ï;|‰˜¥\æ\0“€œ,C¿Y,=°@ê”Š\éO¯\ØV\0Zµ¡\ÐSµ\æFu	m:\âÁ8D¹õ\î_ð_w\ïa\í`‹/\Üþ\Æ(Q\Äi’\Z\çI£^[˜8\Å\æÀ¥\ÛNg\ÐyT=ˆ9\Æ4\áXÔ³9\ËaŸ\Øxô„ôÑœŸô‹\ÌvWU·QuHc„\Ï\ßpƒÍŒ;ˆ7³™\ÆdoBJ]¨£\Å\å–\Æt‡\Ë/\Ú\ÆÉ­„f\Ô K°9‰‡i\Ò^q\ÒJ¹b5C7,U\îø1o\ÇP\Òt!q«“w\n®SûÙ²®Á.:›¶L`ªŽõE\êV¯ƒý\éµð¬z\ã(1¨™Û§d˜¢”QQœZqš7\É{ÿ\è÷YŸAN	\Z\rò$®x\Ýjq¤ ‚jòdee¼]ôD\"Q2\Ä\"¢¦ô\ç˜?\Ì_þñe¼ø”1†´¨Ò´“’\\‹|\Ä^\Ý\ê©GN\ê!\ìQ\í\êI‘o\Ê\Ô\Ú-«GB«F,Y\ÕP\Þ÷‡—s\é\Ö\ri \Å\Ý\r¢1Uvzñzõ4S«:¢\è/øKš\Z¢iŒ¬vhú	†\ãgoZÃµo½”­\ë†ˆ\'ž\è•Ð°x\ÒJ§eB\Û\"T\Ë\ä\r²öýŸt±D5 š ü\n\ÐB”\ê\èuø?;lÕ™ª\ÉL;-i\Æ	\Î\Ú0À¼ú%\\ú\Â-ŒY%!5yGdM<i…c7a÷‰“·Z(T\Ð.N9œjö\Ó*\ã³º2C0Ù¢žg„c².D\ÖxNY?Æ–Í§pþ\Ö\Ó\Ù:¾–!…–‚WOz\"\Ð<\êž9‹u{«fV+\Ó\0ƒ\Ð®ÿ\èµt¢Ioh¤\ê¡X>Ò¾OBÐ„§¦€ShP\Å ¢ö\ÍQV÷\è\Ôù\Ò9;µ”`:€¦¥9F{FòXÙ´¥Rÿû‚CcLù^\ã±¢D\ê>XK\Î|Ë‚–>þ\"r\Ðö\ê…\Zf+b)h/ë²Ž~\çe!{’¿\à=b3LšV®\Èr’£ö²8þ•Ì¢\\ A\ØT\Ùû”Gi\à{žey¸›s˜¥\æ\'Áb¤:»|À°„@f\ê­}8± °~?¦*¥@\èó+yLe••!¡‡\"½D¨­\ç¸u}½^Ø°Ì´°Í©Ð¢}\Ç\ÕB®DÞ¸•\à­GõXO(\é\n,iòt‚´ðX È¬H\0÷õ7•õl.;´b@Cª£÷a@«ôˆ,!Ž9r)K(„ð„\íaJú…YÀ¹^¢i¥\Þ^ºµ=Ú¦Õ¥²¾ŠÃ“¡º\ï¸S\"UME½N§“|:7\ïN(\Â\ÜÇŸ5‡+¦ðs9x\"óX\Ú\Ý\çlpR©q\Ê4›Mv\ìØ1&u<—Õ\\õ61?ü0ƒƒƒ˜v»\Í\îÝ»Ù·o\ßS\Í\ãSFµ:cØ»w/»w\ï\Æ9—Œ\Çðð0·\ß~;7nd||¼·gWK]p\ècÜ¿\áY}<\ßuý\ß÷C{\îxý\ç\æÛl¾\ã¹4wó²¹š\Í\ÌÌ°k\×.v\í\ÚÅªU«Ò¸Û·Wc¤Úµ-011q\Ô\Ã‹\Ù9®B˜w\ã¢þû/F8sws›{<÷÷\Ç\ÚM®_Ø@\"\"=§ôÿ\\\ÂZFN\0\0\0\0IEND®B`‚',1,'admin','2013-07-18 15:51:46','admin','2016-11-06 15:49:25','sdf','baidu','http://www.baidu.com',0,NULL,NULL,NULL,'0',NULL,12,0,1,0,'',NULL,NULL),('ae20330a-ef0b-4dad-9f10-d5e3485ca2ad','OpenID Connect 1.0  Demo','http://oauth.demo.connsec.com:8080/oauthdemo','CRM','ade8aeb8b9513880baa804887ff89571e7fbe584acdbeff154519a5a39f6a567','OAuth_v2.0',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0P\0\0\0J\0\0\0,\\\ÓP\0\0\0	pHYs\0\0\Ä\0\0\Ä•+\0\0\0tIME\ß47\Þa~÷\0\0\0tEXtAuthor\0©®\ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬\Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0À\æ‡\0\0\0tEXtSource\0õÿƒ\ë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨\î\Ò\'\0\0#IDATxœÝœ\Ùs\É}\Ç?Ýƒ)’©ƒ%je«DÞ¬½q\\•¼¸*@*Uyq9þ’¿\Æ/~IU^ò–¤*oÙªM\ÙY¯–Z\í\Ú:¬ƒEq\Å\0qsu3œ\Ì\0\ÃŠUC€˜\î_w\çww\Âq¥”BA”’>?y<£¯‡g\ä€R µ\ßaI8Ž£€žE(¥BïµØ“$å¸¯RCž\ç0Hzo”R¡+HhCO9\î%5„Ô°7a|ó¯À\àK\à\Z\ÎöZÿñO´~õ3\Ì\Õ!cúYQf(£¦¡€ªØ›0þ\Z\ë\É¢\Z%Pbd\n†lV>L\0\ÎÁ\Þ|„ù\å¯\\\àZD6‡›Fµ*\à\Ø 8\èþ9SJ\r\àI:D^J öúo1¿ú5Ö³ÿr™@ŒMƒ²]\àP.xC¦¾\0½oðÿ\Ó ,«I\ç\ßÿ\ë\ÛC™\í^\àA®N$\è}½\ë$(Ž\èJ”\ê4\\©S \Æfº*j{†BAŠRj	<©80*\Õ	<þ12‰2\Z)€¨À\í\Óò\Äý\Ö\ÞÀhüw˜\È\Ë\\‹\ê+q\î-j2Œ¬Iz\r“‡Tš(‰\È\ë\éP\Z\Ê$\Ý\è÷ù©ÿ#ú:¾;Doœ*\égDOŠÂ-[Eòv\Ü\è4)Î¡\n!\\	\ä\Î\"JpôQ\"Hyöòô\æ—$D‰\ìpöxØ¾§/I$!!.|vöÕ˜\á\Èii`*wV ‰p@’¢}\äýüÈ‡S\Î\n0X¡{½ðp\ì`\Ôöª\rT*`\ÈDJ8z\íôYÓ™g\"\ÅV½»Ñ¾bp \ã2	\Ï+©’~štfõÀ\àâ¤”=‹³m)»ñU\n~®\Ýsy:Žƒ){\Ê\Ýò9½*\Ò@\0[@PJõ\0fõzƒj­F­Z§\Ñh\Ðlµø\á_|B^—¨n*\×\'2õC\në¬¿}K~*O.7\ÎT~ŠÉ‰	r¹q4M\ÃÏœª~R€f\ÒØŽ\ÃVc¼v\Þe\Ù•ý*\Åb‰b©De¿J«\ÕÂ´,ARJl\ÛF\è~m¥\ßþ8\nh·\Ûlm\íP,–\Ýb¶d³Yr¹q¦§ó\Ì\Í\Î2;;\Ã\ä\ädH\Ý­\'ˆM\Òú3iŸD\ÚÁ<is‡½b‘÷\ï·\Ù\ÙÞ¡Z«cš\0š&‘B¢\ëz·t\àVcº’&•ëª»”.¯l6\Û,Ë¢\\Þ§X,³¶ö†‘‘ff¦YX8\Ï\â\Â\Ì$©®§\ßúS\ÛÀ$ð¢µZm\Þmn²±±I¹¼eYH)\Ñ4lVõ	Wºq«ô~¡ª_=\Æù\à\ßè–¬‚L\æ`y–e±µµ\Ãû÷\Û<}Áü\Ü,W¯-±p\á<š¦…$\Ò3;\0õzƒB\á\r7V®36:\Úä±œH\Ü@o7\Þ\Ñh4BtA\Ëúm“Ì…ðþŠÃ§t\ê€A\ìüü1„@\×\Ý\åZ–Å»wß³ùý\çfò,/_ei\éŠ¸\Ó4y½V\à\å\Ë×´\Û._¹\Äø\ØX\Ï\Z2GqA©\ët:¼zU`­P \Ùl‘\Ñ2ª9 >ó\íQ m\Ú°;@`œxŒ›·½«\r¥ò>{¥2¯\×\Þpë£›,.^\àý\Ö6ÏŸ¿ \\® e44M\Ã4\Ìø’þ »w\äÃ“º·o7xú\ì•J…LWÚ”R(Ç‰gQ¥°,¥¤”ŒŽdÑ¤Da\'\Î\'\Ä*À/“É i\Ó4q—Ÿ&5„®z\ÇHµFFË€€ýý\n_=\\erb‚Z½ŽR\Ê79¦ibf\ìVDªrVpP)%\ív›\ï~ÿ„7\ëH\áz<E@Ú¢¼„·\ç\á`Z.@£££\Ì\Ï\ç™=7Ã¹s3LL\ä\ÈM\äP\Íi2x½~ý\Z\ç\Ï\ÏS­\Õ(K”Jejµ:†a\"¥$£i®yPÁž^Êµ—JA­^G“\ZJ„£v§\ÞÃ«o\ÝR²·Wduõ1û•\nzVxÀ\ÇóÈ¦e’\Í\ê,..r\é\Ò\ç\ç\ç\É\år!¬®d\n	2e\î.\"“affš™™i®.]Á¶m*•*[[\Û|ÿ~‹r¹‚RŽo\ãz³…Ó)¥&»i¥,n¨\Ô4\Ô3Q…ƒRé·±ñŽ\ÕG\ßbšæºöN)L\Ó`|lŒ\ë×¯q\í\ê33Ó¡1º‡\Ãp³\Ù\re\Ü\âÀ @Z\Ä\Ô,4M\ã\\W²oÝº\Éöö.…\Â¶¶w\Ü\Þ]\Õsô§úÌ›E§cø\ÑB*/l$¥¤PXgõ\ÑcÀ÷þRgYš¦qs\åÝ¼Á\Ä\Ä\à¦]Ávþ8\n_¢Ò§röšVP)%/.pñ\â»»{<üú­V!$\éëŽ‚N\ÇÅ¾\n÷\ë”¼¯W#¥D\Ê^Cz0Ž\Ì¦\Éü\Ü9>¾w—ùù9\à\0¸t\ßK\æú\Ý\ïO\ÑÌ¡T.Sxó\ÖwiÁó¤\Î0ŒX_‘Æ„lÞ£o\ÝD_ö±w”rpl‡[­p\ï\îm2™\Ì!;`p’–6‹”’V«Å‹¯)\Ö\éôŒ~\èÒ¡\Ã0±m»Gûbm j§\Óaõ\ÑcL\Ót;:N\â¢r\\\Ñ~ð\ç÷YY¹ÞµoNz\à\"U\Ï%7Ü‹Ã¶\Ö\Ö\Öxú\ìFƒl6‹®\ë‘ù¨T{).€¶mûYK¢\Öþž>û#\åý\n#]‡\ÑoÿU)\Å\Ü\çúõ\å¾R—¸\ÛrƒT,r/vWN199\Å\íÛ·¨V«T*U\Z&†a„\Ê_\ÑJQœ†	!°m›N\Ç`dd$´¶L´³§º¥R™Ba\ì\0‡njôñ\Çw|ð\Òn‘†\Ï\É.…ó ö{Eb<M\ÓX\\¼À\â\â\0lÛ¦\ÙlR©\ÔØ¯T\Øß¯P«\ÕÝªiúU)\ÝBGTøm\Û\Æ0\rw¨ \n\'Mð\Õ\ëµ@¸\â¸\Õ\ß\\–e²¸¸Àn}ò°©@VºU\0‡\ë=J\Þ#a/¥drr’\É\ÉI._¾¸Ž¡V«S©TÙ¯T¨\ìW©7\êt:¶\í ¾”:Ž›\Îõ8)%µz÷\ï·3<\×id³:ß»“º^˜¤\Âa¯{8ö„¯\'É€d³Yfg\Ï1;{Î¿\ßjµ¨\Ö\ê\ì—÷Ù¯T©Vk´Z-Z\í6\ÍV»\'ó%0X\Ú\ÞÚ¡\Ýi»+AÜŠ…\Å\Ò\Òe¦§ó‡–¾Á\Ô_ROŽ\Ë\ë£›\\.G.—cqÁU}\Ó4i6›”Jûnª©Â€d‚ˆzm±X\n¶0^Á\Îë¬š\à\Ê\åK‡*“\'mTE½\ã 	\Þ=Ny¾WJ\Ãc\ëºN>Ÿ\'ŸÏ»#{µ\Ë.õ¨°e\Û\Ô\êu\×;õ„³¶\Êvr\ã\ãL\ÏLŸ\ÌþB\î/\á¾q^øh$\"|\í£\È`#!¶e\Ñ\étººÞ\\°ô\Ñ\Åqr¹œ\â¤\Ù[\éG½¹\Å\ál\à1‡\ïK¡”3B~´\Ê	õ¼\ï¾tg«iÀQ\'mÿ‚O\ì\è™\ÈYPB*7`r>\èÝ½‹P\áø³1\é\Ôø„´÷H$£§°¤”h™\à7 ã«¹^ª\çyß´*·¡\äŽ\ì?˜W\Ë’@†¶ò\ÇA\×u\Æ\Ç\ÇÝŒHz´RJ\Zõ\ÍfóP&z\á	H‡ZK¡w÷*Ÿ\Ï\Ì}¥”´;;;{GV\á^)<¿“¼‚<ƒó’ô\Ô1Â…ósî¾€ç£^W\å¤¬¿\ÝÀ¶m O0BRJvÒ«ðAa5|vù$.onñ±ªK¡0\Æ{?77\ËT~\Ò=j!£³=X£¦i\ì\íY»\áZ…4qO8ø\Ú;PŸž\Ã ÿˆ¯‡®g¯^]\ê:ˆ\îq_–Rò\ä\É3jµz¨V\ÖO-\â\èhÉ™\×wˆ60ê…½\Å/_]b:ŸÇ²,wq÷$}„4M£\Õ\êðð\ëG†1­Š@š\"}˜\Î\Ò´¡‰uÌŽd¹{÷v*æºžao¯È—¿{ˆa~ù\ç4\é,¶¯\rLò Ž\ãpùòEVn,c˜¦o\è\ã\ÈQgkk‡\ßüöw\Ô\r_SO²W¦ú´\î\É\Ð?œ0j¥÷\î\Ýaqñ†aô\rW<ww÷ø\â‹ß°¹ù\Þ/—§RõFv}Z‹ž°{¨t0\Þ\Âu]\çGŸ~\Â\ì\ì¹Ô’\Øl¶ø\ß/¿buõ\êF\0\Èð\Ã\ê\ìQ\ï,\rzø=60H¢[u\å\'ùc\æ\çf]I\ì·c«š¦!¥\ä\Õ\ë7|þùÿðô\ésšÍ¦¿\ç7±^>P\ä¸+\Ü÷ôTxP’0ðkˆ\ã\ãcüô¯>\ãÊ•K†Ñ—¹N6«ct~ÿ‡§ü÷\ç_ð\Í\ã\ï(Kþ\ÆUP\Å¥ü­Qå¾\ïs…\Æcx*œd£‚ f³Y>ûñ§ä§¦xþÇ—X–…®\ë~œ\ÛWdµ,\íŽÁ‹¯X+¼afz†…ó\Ì\ÏÏ‘\ÏOù‡0\Ýiqp\Ø\'‰zj7§\ìD’ö|¿/µUˆB\îÞ½\Í\Üü\ß}÷Š¥2z&ƒ”2þAtp)Z6‹£{\Å\";»»\è™\ã\ãcä§¦\Èç§˜\Ì\ç§EV‰þ*©<?,@©\ÄM¥“¤¤X0\Õ\á¢\ègŽ\ãp\áü<ó\×?\å\Å\Ë×¼~U \Õn‘\é\é/*fÐ½cfJQ¯7©V\ël¼\Û©1*>s$ÿ¨¥€@5÷7e”ƒ²\Ú\ÞÁ;]:\Ò_O\Z3™w\ïü€¥¥Ë¼z¹\ÆÛw´\Ûm4MC\ëþpNJ\Õ¯Ø£iný\ÑÝ¾’(«Ó­©p„\ÇBµ\Ê %\Ú\Âñ\æ\ã¬h \r„þaŽRŠÉ‰	<¸\Ï\Ê\Ê2…\Â:ï¾§\Ñh\0ø9š\Û?ÀL¹§±¥y\â`gPH°M”QEŒ\å\Éü\Ùß¡ÿ\è—d®ýŽ \0\'I}3Á£\è&¸GA•˜œœ\äþý{Ü¼¹\Â\Ö\Öï¾§T*\Ó1:Rjþ×¹ð>·È¨\ÂUU«ƒ2\Zˆ‰yôþ\é/\Ð.}\âM\ì˜\Ë?>ùg¤\ã(\nZ\ÐQ \Ç\ÆFY^¾\Æòò5*•*;»»\ì\ì\ì²_®\Ðj·±mp\ãE¿¸\àr\ê\êle4ù‹d?û%ú§ÿˆœ¿\å\èÿž\Ìð¿­›xF:\íQh¡\È\ç]{s\å\Íf‹JµB©´O¥\â\îi·;˜¦…\ãX(»ƒÓ®\"ò‹d?ù9úƒ@\Î\\s™\Ù\ÞO?-pI\Zü‚\åiñ{«¦iºgNš-Z\Í&V³Qf\Åy\Î\ÄÝ¿…‰·¡m\Ø\Â3¦AB”`u8šD\ÆñR\Ü\×Sý¶0T\àÒ’°m[Á`Gr\Z”\èý•s`‡Di(•\n÷³Ç™\ØIñ&…Ž·)¸¸è‚Ci=ûÿòU8öæŸ€„œ\Ûÿ©¨\Ûi\Ðÿ½Pv‘ùYI\0\0\0\0IEND®B`‚',0,NULL,'2015-01-08 15:17:35','admin','2016-09-27 23:02:48','','For Test','For Test',0,NULL,NULL,NULL,'0',NULL,8,0,0,0,'',NULL,NULL),('b32834accb544ea7a9a09dcae4a36403','OAuth v2.0_Demo','http://oauth.demo.connsec.com:8080/oauthdemo/','HR','4e1d7eb7b14ad658e8d9066c95902c852ff6494512a742a8392d1d16adc5af551e698f87c64032dc548d6ec7dc3c4863','OAuth_v2.0',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0|\0\0\0{\0\0\0¶\ä\0\04IDATx\Ú\í\Ý°œU\Ùð°ŒÈ€¢\ÂXÀˆ \Ò¬ˆmì‚½¢‚`ö\Ø\n\ì;ö†(`öŠ‘(\Ö5\Éû=¿ùÏœ\ï\Íî½»—‚\Ü3ó\Ì\î¾ý<ÿ§Ÿs\ÞÌ·ù6\ß\æ\ÛU§-\\¸p“g>ó™\ÛpÀ{=ù\ÉO\Þÿ‰O|\â\Â\Ç>ö±\Ç?\âX¼\ß~û]ø\à?x\åý\îw¿\Õ÷¹\Ï}V\Þóž÷¼ð®w½\ë\â½ö\Ú\ëø\Ýw\ß}á®»\îºÿ\îp‡½n}\ë[o³÷\Þ{o2˜o\ëW;\âˆ#6{Ó›Þ´ûk_û\Ú/ùË~Ñ‹^tòóŸÿü%\Ï~ö³Wt\ÐA]\ß-X° {\Âž\Ð=úÑ\îþð‡wxwÿûß¿+À»}öÙ§»û\Ý\ï\Þ\è]\Ü\í±\Ç\Ý\ïx\Çn\Çw\\±ýö\Û/¹\ímo{ò-oyË£ov³›-¸ño¼{\Ýr³Á|[wí“Ÿü\ä†û\Ø\ÇvüÐ‡>t\È{\ÞóžŽ<ò\Èó\nô\Õo}\ë[»×½\îu\Ý\Ë^ö²®\0\ï9\ä\îY\ÏzVtŸ%\0\Ýóž÷<û†ô\Ü\ç>·;ø\àƒ»§?ý\éCa\Øÿý»{\ßû\ÞC\ÐKÃ»m·Ý¶»Õ­n\Õm³\Í6]>ozÓ›®¾\ÉMnr\Þ[lq\Â\rox\ÃC6\Þx\ã\ë‘6Ì·µ\ßN9å”¿þõ¯/ú\â¿x\Úg>ó™•Ÿþô§»ø\ÃÝ»\ßý\î\î\ío{÷¶·½­;ê¨£º|\à\Ý	\'œ\Ð}\ë[\ß\ê~ü\ãw¿ü\å/»ó\Ï?¿[¶lY÷×¿þµû\Ç?þ\Ñ]|ñ\ÅÝŠ+†´|ùò\î\ïÿ{÷\ç?ÿ¹»\à‚ºŸÿü\ç\Ý÷¾÷½î³Ÿý¬k…¢\Ì·\Ýv\Ûur·\Ùf›u›o¾ùðûõ¯ý•›l²\Éi×½\îu]óš\×\Üy0\ß._û\ÉO~²\ÅOú\ÓN?ýôS¾ÿý\ï¯Ä‰\'ž\Ø}\îsŸ\ë\nô\îóŸÿü\Ø3\Î8£ûý\ï´!€ÿú×¿ºÿûß¡\á\ïÿü\ç?]û}\åÊ•CZµjUw\É%—t—^zi÷\ßÿþ·[½zu\×o\ã7¿ùM÷¥/}©{Õ«^5€­¶Úª+»\ë\\\ç:]izw\ík_{\ÕFmtÊ†nx@=úƒù6y+Ð¶½ð\Â;÷\Üs/ø\Ý\ï~7Ôº½+Ð»þð‡\Ý\ÙgŸ\Ý-Y²dð?ÿù\Ï\íõ\Ù \Ñ-°¸\í²\Ë.ù\Z!\è7ûñ‹_t\ï{\ßû\ÄCm¯\Ç\î\n\ì¡l°Á\Ô\ïÃŠ¶Ì·ñ­\Ì\îû\ÛßŽ-\à–©@j\î¯ý\ëî·¿ým÷‡?ü\ÈLó\ZT\ç1\Ík€\î:úÓŸ†\æþ-oyKwô\ÑG»\Îü² \Ìö\Ð%”« ¶@Œk´\ß5*\Ò<º\Ï\åE\Ç\í0˜oÿ\Ïto]€Y¾öbÀÀe\ÔÿøGþwtè¢‹.\êþò—¿Œp\æø¼ó\Î\ëøÀ²û\Þ÷¾Cß®}\å+_•J\çü\0\ZþŽ–/]ºt\ì\Ï~ö³‘¦Ÿ‹ø\Æ7¾! \í\0]\\td\Ñ\ÖWw 7þÑ~th™\èeL7-.3N³\ì|~€µ\Ýg€ŸQ\Ó¸(ü^÷ºW÷‡<¤[¼xq§}\ík_\ë\îq{tû\î»o÷‚¼À¹@^ƒ8^~Z\Ä.²\Ø\rŸaTs§=\íi\ÑôÐ²¢C‹6¾Ú]\Ì\Þ÷\ä“O>oFgžy&\Ó-Rfri`£\á\Ã\ß\ßù\Îw†QùK^ò’\î\ÔSO,†Ï¨\é\0\'D|m8\Ýþ\Â¾0€\'˜kýþP£?üp)\Ú0`«œ|è»«X\Ó-Z´H?\nø\Òø\Çð\Ð\éEû^-€~ÿûß¿YEÙ‡—9½ô\Û\ßþö0\Ê>\í´\ÓøA@£5\0\çƒ\Ï9\çœa\åIOzR÷”§<…	\ÆL Ž\Õt$h\àð‡>ô¡¢úYG¼\â	OUð†€o½õ\ÖÝnt#©\Úüªt\ã\Ú7¿ù\ÍŽ;µ _Ztøÿt!\ç½\ï}\ïžý\èGK«˜C>h˜H³£\Ý-œ\ß\æw1\Z\è\è©O}jW%Ty6 \Ö\0½õ\åLz•UÎ—Ç‡\Ë\×UÙ†€W…\Îñkhx:À\ÏGo¹\å–CÀö°‡u»\í¶[W•¸!¨34A¡ ‘[h_\\´\çÿ\ØU;¨4`¹bIUÉ€-\ÝRˆO4t€3\á@¶\êðiú«_ýj\çô\é/·\Ýñ\0G¯ý\ë;\Ö\å\Ïx†\ß\Ãòj•gEôI\ÙF‚®UÌ¡â†˜s–‡b-†÷› é·ª^\0O4\ÐÿÐ‡v\Ø\ÆUö<¦\0\ï\Ð1\Ç34\ã‚4@\Ñn4t¦\àUeð0ªRj÷ŠW¼bb\rŽÐšT\ÒFšv~œ \Ýù\Îw\Æh\Z=4\å€<\à\02°\à\×\0]ã¯™o&]¹V!§m|ý$¥x\ã\ß\Ø÷\íÇ”‹¸\êt5 ±Õ›\ßü\æRÀ®ü\à|jØ³‚Nƒ¸þ\àri‚\ã;m\'\"h\0÷Ay·\Íy‚6\Z€ÿ¨G=ªû\à?\è¸T\âF‚ ƒ4Žù©{&-…`‡&m„?…tk\\\ã\Äú\Ü\ê*vi\Âö¯y\Ík\Îf*ø\î¸\ãŽ\ë\Î:\ë¬\0\r\È|§\Å~ô5Ìº”¬L\0\àøp4=Á\Ü\Ç?þqÀ®x[Cžt\ÒIÊ²CŸû«_ý\Êv@³>G‚®}\ä#\éªvÎœmó¬À\å\Û\'ýŸø‹\Õn\Òo£u-\èg_\ëZ\×\Úþª4½k\rU.)À™^\Zü–ª™\ÎIaT½†\æøq{\\W\Çv•¢¾=À\Íõ±\ÇK“\Î÷\n²lc\Îýv€°\ÑrÄ¤·E˜T\ÓÍ¥Á¶\Ç\Ýq\êø¢}i\Ù\Þð†h>À\']P¢´iu¿xÑ‚¾¤>w]\ïÁ.¿¶G¾ô¥/}\é”Š\Êi\Í\Zjó—¿üe~s˜\Ï\ÖXs—aÈšx\ÐU\Ê\à˜ö€Î”ªpZ\ÍZ(\Ò\Ð\ÇK³Ê¨`\Í~÷f]Œ‚\ÑnþZ)Õ³øN\Ø!Ü‹\Æx€¤\á@÷›\08Vv\àw\"ø±€#\í_ø‚\è\\­ †hû\á\\™Gú\Òú\Üc½»&\ìR€/Uµ2ölL\Äp\0‰pùN \ßþö·\ïö\Üs\ÏaJ³\Ã;ø\rtÁ\Ù\Z û-²fº_ù\ÊW\Øm¸R8ƒ\Z\ÃÍ§>õ©\î\ï|§\Ñ-\Ïb\"!¥’J1\É|x&?(¯ú–^ÿø\ÇWg1€\ëúIk?½·¹ú\Z k\â–\Ü\à\0Gâˆ¡€Ži2>è»¬w`“¶;ðÀ—(ˆ˜t€YÀ¦Ýˆvó\ç´¸\"l>Tz$p:A`’ª…ü––\ÑP\×lÊ®„»¨ñò\Î9\íg\r¸±Kp®€\ÉTb}Ðƒd¶‹h=€‹\àN\è<‹ L$\Î_\Ûg\Â¿\ï~r\r°\ã*¢\íX©\éRJ¬†R]s(\0i‡z\è\Ð5õ[\"ø˜÷ò\éÛ­7`—\ælYt_*ˆ13y´\rÅ¤\Ó0 \î¼ó\Î,\Ú\Ëo‹0·»\Ý\í\à‹ ´ ;.\0\'À³\ß=X˜iQóð\Þb¦_\ê…Ù›nº)¦aÞŒ\ä \ÐF… #\ßk\Åtnˆ\0±\"\Ì<l\0À\Û!U\ÖÇ€\ë:7\Z.\Ð\ée\î\í™sž–°}¶s\ês\ËõA³7~\Ìcs0i\Ð;\ÞñŽ\0Mó`€3Gh\á\Êd\"W`\Û\'\0#I\Õúi\Z&\"ûg\ä\nØ‚(U4\Z„Ak•ø]B“/þd%X>?ÀÇ¬ú˜n\Ç[UŽ¥\0>\"8\"¿¹\çôš\\¿}–“®ô<½¤õ@?ò‘\ä{Œ1y\rRha\Òi\ÜN;\íÄ”2¿ü<JsÌ¤l<©œ€Œf\ZMÈ€\ëŒjJ“Y.ù\Í(Ó¦8ÿ.ƒ0L:´\ZUV>»ø&\Ú°\Å„¸\ßr-n¨-\Î\\i`\×\ì\Ï=I~\Îsž#À‚[Ó’h^8³n¶Š@‰&ó\ã>£ñ>KRRó\É2˜\á\â\Z´\Üv99s}y@\ã\Çù\ëË­ù@\Ëo;<H\Å-y·x\"Ú«ŽO³	°\Å,\ÕMJi&m{\ï\×9Ø•Z\íQ\\ÎŒ–I\çGv\0ïƒ®c\Ì/\áË™µøtA•\0.Ú¨\Þ6ŸR/\Õ5Qý¤€Œó\Û|(p\0N\Ð&<w<\Ñ\Öv\ÂKg”.-+§¯Ž\ç|\' 4¼#$m\í}÷uvI\å¦Eg™\Ì\'¡q\0e\Î\r& ™@—«mTøÞšzL‹Sju±‰Šf±»\Ý\ín“0¿?\á@\0\Öþ\æz€ß¦$\ËZ­íŸƒ¦¾·\È_?ZÀ+‹\É=øô¡ðO\Ñ\äò\íý\Î,\Út\0~—»\Ü\åˆ\\4\à\ê\Ð\Ð8\ÐÛ¨]\Õ\r\ÅW“~¿¥W*RªsR;Å–h\Ï8J¬\Ö\ß\ç\\V\Äw¾0`·$`t.¿L\0&u\"¢‘LzšX¤Ü \íb—˜ýij\ï\Ò\ÖöG\\\á`—/Ù§RKM˜r~Ø´v\è¨z€OaÆ§ Ì‚‚w½\ë]rh@L\ÂX1€‰\r3	3kZÒ¸c„\Ú4hœ+\È3MEŠF¢wM‡\ÕR±Ó¦p´þü’¢}®0°+\à\Úx—]v9#ÿ˜b šžð@I\ïƒhŸ\Æ\ÈY¹)N\Û\êˆb²=\ÍŒ:Gep2©\n)ýcl³\à@Z\ç:£®A3Y“<mü ¬Ö¯\Ïu”’µ\×>\ã\n›#W‘õB\ÒE£^ü\âU0…€=\è}\r\'™§F«ta–Ï–0:&µ;&?‘\ËpU<¹\ï\Ò7Ù„\á\Í\\\ÃwÛ’\Ú	\ä\âr\r³e\äÜ¾\ËÃ¹²ôTú\Ï\Û7ÿ,Œœ°)\Ð\Ìtüj¯½p­ƒ]Œ¼y\å\Ï›ÿ=\ÓlS”|¢q ÷#÷c|—w+[*2«:fõ	S;\0¶\\Þ¶–¹r|f·vö;6ûDJÁ>\ÛcD\Ïú\é»Y`\Ú\ì—V4¿	€gò}õA§\é}\Ðýžt®Á}sÝ‹Šn¾¶}÷Q¤Ÿ\ÙTP\0¨œ{\èhl\Ôl\"4LL¦\ÎD\0–`\àû\ÂŸNˆøuû„€£hbâ¡ŠœO”BŠO•\ë\è«a[&¼÷\\	™xQÿ$\Ï\Ý7ï©»_M—µ\×=j­]>{û¢ü¨øh9¤O&}\è#£v¦œWhQR¬k\'¢n	£\Ú\ß\ÑÐŒh¥F\Þ?O\í›\Ï°c€h{4ÔÀ\Ûõb2µôö92\ÑvV\ÉsDH’$ûúœp4Ê§û>¾¼¹´8±¢h\íLš(Ÿuœó\ßÊ¡@vˆYzø‘¾\Ø\Ç¼ˆ\×@‰Á…0«%LV\ÖkG-[\í·Ï¸º\Ï\0`\Z\ë8\ç\ÑB96ðù<Sœd\ÜQH.,`Tö5‚\Ç\Ò\0»_Fõ<ñ\Û\Ê\Ã,ˆ{»\'Wd€\Ëq|Û§>\èI\Ùhû\ÈIù>Cóü\í5[`ß¦|\å\ÅQoQ \Ý@V†# £5\0¥<jH”)†A>Ce³K“tŠ/\0ˆ¶&€\Â\\û\ÆË©\r\Â`†ùpS4\Ú&sP\ÕSÎ½:°\rh¸gÜ‰\å\Ã1\åR£gK¿Æ\îø\â\ÍY=€Oº}	(³¤\é6—7;,\åD€+-\à!Û£\é£|9\í¶\Ïö€®É¯`\Ú\Ø\'û0XpX`\ç\Ø\ì\Ïüp\0\Ðl\n\0¶6\Z†+‹R!Á\ÄX•\'\ÐM[–e\Ü\â·ˆe\ZG\íÀ‹\Èps\ÕtË˜Û¬\â°9ƒ]U©Í«³K™NÑª1`um€ûô8\Ðvˆv3›:\èz€Š\éG)s\nr²N\Ð\ÙO£\å\ÐL+\íÿ\îw¿\Û]\r\0ŠA´\Ü3$¸‹\à@y>ßœ\Ù-ö£t³x¢\å}\Ðx¾£QË—ñµ!³ùœ\0¯”\æ€Lëµ®\n¨L:À£\å£@G­¦\Óns\ÈX	¦O¤¯\Ã‡úQr4™i¦Ý¾‹–§cdÀ¦IÀ6D*M¹‚›²¯g¢½y^\à\Ï\Æ\Ðt\Ñ;\Ð#œ\ã¨-\ã]@¹ûh†_SðyÀ\\¦oPo<:Uj£ü!-\Ê:\rG“švšY¤fõgšù>Ms\åû\Ì~¢`•0Œ\Å\Ôkâ…¼v5}\ä:aüºg\"´+nF&1	\èI\ÙKfÁ}V\à{\Íü\0E¤\ÄD§\Ö57˜\nðz§\n¬V‰pU\Õv(f=Ô\Ú:\í6ULA£\Í{[²\r€2\Ç—ö¶\Ç\Ò¾_ŽŒ\é˜l¬x·\Ì@E,–™v\È@”F`õcµ ›D-ðQ4nva\\UŸ;MxÕž\É1\å\Ý\Ò šDH»C\ã@oÍº\ß\ê\Ø)\ÚÀQ”©@ü K-jIÀD’]K\\p%5ó\à17Z\ÞöÈ´] É¯OºkI	p£´\ÍfÞ¥™x–©S‹&»ü\æFU\É:o\Ì\ìR`#\Ú=\è\\*F»ùnD+\Ã”\ïa i?©$JK0$\Ç:_^Í¤Ë©¯\Ìf‚¡¤\å\Öô…%\â¾Lv”¢	R™÷¶/}Á\Ï\Ìs\ä´\Þ\èQ ç³#Sp×·}£I\'&\îPU¦UònÃŠ€¡ôqšÎŸ÷ósù°Àp€\ÑX“ Œù\è\â˜g^\Û4\ri€\é¿LŸzºJ™I\æ´Ï¡³Ï€\n‹“TQ_-4\Ð¿	°g&¨\Ø9>[r-)k^8\Ðg[\ì\àw;^ž¢Õª\ë]\ïz“½[¦†\'Vò,\àuŠV›ô±š°CÌ¹\é\Çy\ã!\í\Õ)~\Ü6y6ùlIõ\Ê~\æ\è\Ãøv\ÛLqž¦I\×R\rkóø\ä\ÐÀ2!a\Úfu‹_Ž<£‰Š‰UôG¿ž \Ôýr_’ö\á³6ôqÀ§òe\ÂÇƒ\']Ar‚Ü—ÿ±Ž;\ZŽ0¯}¬–\Üp\' \å\È\Ñ\âh2&yƒ\" c{(\çX=RBH@ø\Åi¢rÏù©\Æe©/\00\Ý6Œ\Ú\\Ü„Ñ¶\Ô\á\Õ\Þ\Å;7Zœ>$þ°”È¼¼\ì¥\ï®\Å\0\ØS®™¿\Åð\Æ\ÈYÁ®JØ¦e\î\Î¶\å6\Ì`´»zØ¡øq9«tE\Í\Üd´\n3¢\É4š—5\Û\étŽ!¬Œ‘1#`\0\"“6A!	\Èü€\Ý\'«J”n})\ä4Í’& y¶âš€\çõ\ì\îŒ\ÒZC¶\â\Ï\Ôðð…°\à\"\'5\ï)µzýôL\ç\×\Û\"gž÷V¾g·\Z¸\à†A\n¼@†‡ønfWt.‡w\ã‘`3õ\Ñ0•\"\ÚkŸc0ˆ\Æ`mJš\Îq\íIs.@rª|\Ñz‘1€Rù3T;M˜r–¦{fÏ®[,#\Å\åF¸0Bž÷\Ã4€{F\×3‰3Z>•¦W3kˆ@\é÷e%Ð»\ÍxÕ¢püL•I„€øL Ç´p\Z®ŒªØ’\ê“@ña*x˜œ}:\ÐuXA…À¤ p`\éô$\Í0¬\ë3\Ý\È=¾g²\ÏD	ÁU\Þ\Ñ\â\ÙX÷˜´œq}\ZÝŽ¹t}¡<À¶]tù{_2™²”.+Wû€Ï˜§k–5\ë§ø\È[ g¼À>ZôZÀ“2 {$\è¼º\í\Îg\â0PG¢Q|¶¡Ê¼ö\Êv”ý\n/\îah{´\ÛöI›R0€‰©\î\ç9\\\Ó\'­\æ+ó6&ÁWf¯L1\Â&ov.Àõ\'\\\îŒO,û}\Íô*©[Î‹€|\ë\ÛÇ½dˆ¹©\é™ÿ.SÀ7¯þžðòÛ§™Áié®¥¹¨:P\Ñ\Z Ø¬»f!ø,R`uJÉ‘0\Ø\îw\Ìk\ì‚\Ö1µ˜þ;¯ï˜¤a¶\ëb^®\ï9D\0ÄŠ±2˜žµ\ê\îç»¥\Ès\0<`Æ’ð113Ë¡²_ŸbM¼Ú›6\Æz\Îh¦\Ò4¥ ³žU«©2‚„Nv™\áM\n\èsM\ía’l¡>\è\ÑðP|¹\è\\ŠCr™´hW(’­Fa\ØYHH¸üP4K“‚@\Î;c¤ \æ´\ëx?]RqŸ­@	\Ò\ä\áñ\éb3nl·\Í}]o*“0€GK]GZ«|¶>%ž\Ø\îI°Û›rE3	ª%J\ÞZ_øQ ‡€rÿ,\ã:w\ì?;\ØÛ”D® \Ý\Óº:\íµffóõþ‡\Ù6\É\Ö\0]§’{\Ó\0cH¦-Å·3ÿ]iñ»\ÎÁ(¤S‚?÷ôrQ¶grýTî¤…˜\Ì]Ð¨\Ð>‚D\Ó]‹¿¥ù\Ó4Ó¶2O.}á³\Ý¬ˆ¥jI\0­u\áM\n\à€¢&‹¨±\âqAÜ¨—\0[ÑƒW\Æ/Vø;‘€—\ÉÜ»H\Þk¸\rÀ\Þ2Ô‚“³\Îg\Ëu\éJ±\ÜS¹ÁQ\0O\0\Ê\Ä\é\Ñ\0´\Ç8s0¿ev“5iÀö?\íxß˜2°]0\è\Z\\‰\í€E	ž\ëc\Ðg*À\Å9\Ü ~g>]úœÀx¬\'°õ#–\'\ÅÐ§¼˜\0?õ—‹r¯LsžA\ÓS\ä\Ç¤®µ÷HÀK;÷³òƒß±ö+¯\ÆD\Ñðh9ó\rh`=”\ä—h·’¬\í:\Z\Ô\'\Z ]q,\èpöEú]S\0¦¶ž(6 x¬ó\rPD;\0\é\Z¶»†ª_*Z\0f¦|Kð¦lú*s]BIQ<³g\Õ\Ï\é“0a¾YC#}Ež ³H@C„\ÛvŠ@~$\è\Õ\ÄQ€\Îÿ¸\ì7ðò\å\ÞY\à\×\×n`3\ß|\'+€\ÉB=\ÍJ\åˆn`ú¤Ã‚\ZfC²¢4Ç“tÃ©†fm\'<e{j\ê®ÁL2—À¥\Å	ð€L\ÏÀL\Û\ï\Ù\Ü¸){º_\âk¿¦i)\ÎLgd1³Y\Ý73r\\\ãõE\ßôÃ³¤øÁÈ§g\Õ_~ž°\âk(\ÖMPl\è9Å–t\rN,wv§;\Ýi\á¸\×c\Ïwó\Ã*H@\èüµù],€|\Ð\ÌMû0~+(6\0\îw´9\Ç\ï\0%\"Stùo‘¤g\0Œ©\ã\Ï\Ý;Q­,\0\Ø2u.¦\ç\Z4ŠbPJ¹™\âL\ËcF\í£Ó¶¯~õ«À\ÖÀ$ør÷uÿ À\ÖÿÜ“+\è\Ñj}/°ZÀñ\"D°\ì\Ã\ë\á\ä­\Ñp¾{\ä>Ç¼\Ì\Íbõd•\Z­†›G–·0õIG\ãµ|:iÔ‰PŽ¡¢qE™o\Ù\ïx\éaXÀ\Î1ƒöÀlû1‰0“À°<ŽIm^°&È£‰\Ü	ù\\š¾&‹†\Æ\Íxv–E\à\Êg\ã›cÒ—\Ì\èaº‘\çlûA°Q&„ >ðúÊš9Î¨‹“V+{ð\n\\{µõ2óÎ½\Å4š\Õ\r\Ò+ \Ç|#€©Žñc\0w¼\naDÞ‚ŒI\ÙŒ\Î{Ÿ	\ìFø¤¹&`¦ï¶…\Ñ1\ã\Î5é†9/#S(>;1„ôs\Íüö[\ëLÏ”VŠ¾~\ç<–•\"Ù§„’ð\ë`Gñ\Ûv”`–‹\ÌX€·gpq@\'\ËF^ùóJ©øj\áòD~4š3˜\æ\ÊÃ”\åÿ¾\ÒnšÅ·`F@Bf_L¡HW§\"ñ‰jy”\åFk\"P\îA hŒ´ :/6\Ïä¾¢æ¹‚\í},ß§„˜°¶‚3®/,\r!\Ì`MžUT¯×…ll€9åœ‰\ïö#\ç\ã\rx\Ê\'\Ø¬®	xÍ¶X\r\è\ÝA1³.6yPR\Æ(Œø\Â\è<Tòc \ÇTcŒ\n¿„1€â™0\Çlf<]F\Ú&K‹\â\n<;Áq\ÏC\î\çZ‚\Ó94RFÃ’;@…Ž\Ä\Zq\'úk\á\Í\0/‘8°¥i±bŽi„»}†¤ ³Q^£‚x“—\âÁê‘€\×ä¼•À¢¡†€=\r\à)\é\Z?\î| ù}\ÂxÅe\ákb{:ž \ÌñÀf=¢%(Ì¹7B\ã²?¢crO\ÛôoŽ\Ís\ÆoÇ•tc„.`§/”+ºô\r\Ø\Â>¥\àB¸E÷-\àOqh¸\ë\Ô\ì\ã\Ñ\Z^@-­Š\Ð1	Ó€6!\é `J°Ç\ÇÌ=žt3[ü­Ä˜V(bþd\r4\Èõ“†9\Ï,\n¼0\Õus\ßŠ$x´ŸOk\ã¤sñ\Ùñ\Û\î\ÅE\0’Â¸g¯/1·öË¥™\"}L9\à\éÃ¤\ä\ZŸ%r ö\á5Yq±ò¨€¹\àn¢\Ç5\Ð\Ø\ä=r¬<“6ð3¹__ Xn‚`ð\Ïñ[1]®!€\ã¯(®À#\í®#p1d:W3N; |¢…¬ŒhœRÀ‰u	\0~3\ã\0\Ð_®*OK)`QBÎ¤\Ïp.’À¸YŸ\Å\ãþ\ßóxù6- 5‘\Þ	(€;Ï¬9*\éL§ZsX¦¿eŠ1#ÿL sS‡1c1ˆO0†\ä\ZI½Ÿ$\Ú\ä~\ÎejZsÁ\ÚLP\Ï\ìJc\\^F\ã³	\Zð	½òrø\ç“öó©Î§ÒµRPx\ä»ý&Yº!™Ÿ”ð\ï=w\Þ%?:/ \Zzü¥›MB\é\àò·‘´.š†<¦lRØš`€H´D¾\îša³\Ï\'a\É\äaN\îŸ9o¶¥\ã\nE*Psh%z~\Ìw‚A÷f6‘\Z}«±±t@\Ó\Ù°	¢¾:\Æyú’”K¢ø\ÄZ\Õkû‡&Á\Â}X\ç\Zš>º\ÒVC›û	\çi¨\áÍ¼`vÊt”\Æ\ZPa>\Ý\Èö6}`^”^v?­Ki–Àyð5\"PÛ˜E\Ze[,‚s\Û*\Í`e\æ\Ú\Ä2@\Ìpn²À§¼+ýñ¬Í»ß£©Ž\ÕWkÀ\r|¤/-/bº	ƒ{E\ä\àJ×™\Ñt6<Ü›‹\Ì\ßjxG\×\Òk¨oo€#y4À\'\å·8@\â/j¶-E¦—/ÁH§C˜\ÂgË£i{ü1JJÇ¤‹”13Zl\íw}\Ç` WÍ¡™ÄŒM»fá£Œ\â¥\0\ä{ž£Í‰i>ZýŠ/ý¾\ê£ý2‘hº>ã´\Öù³ió8À	™g#4\0=ZVogØ¦fz®ð†%Õ®T\Æ\ÐÈ›‘~#m&÷ûÄ¬”™±´ð\Ç	\Â\ìƒX…¸ÁM¬€\Î1uIß’c\Ú\ï”v-40‘ \ËhRþôL6\áØžƒ…IÉ§³Lm­vI‹\\CŸ˜\×ð\"•4“A\'ýH\Ü\á\\\ÕNÚŸ\â\ÔL\Ô\Ç\Å9‚µüyþŠ}ôxx\Õ_7)\ÍX\â=§\"õœ¬ó#h¤`ˆj™™ Flh²¥cùR‚0\×¶h<¦\È\Ì\é·?E\×k‡bO+ø,S²\æ\Ð8¶toK•þðµ´T\ê•\nZR=€zV}ö~[ˆ\ß.¡möë—¾\àA |¡†r)-\Ò\ÍF}\\Ü‹…õ¬K\Êbn2\×\ê]§§xMµ\àm†\áÍ±‚€a´„IEL8©M§BÝ±D0± ð[=@‚fóû)*\0sü\ZÚœC3\ÄœÔºL\"\Ì\Ó\'ý`~vöE)€N\ë\Í%  m_\âôƒÒ—>\Ø\Èošo¶°5y2¤~“P+€À6\Ì\í^\'fj5G\ìh)-g21bR°Q:o\à˜»O™_\æÁ2Ã£\í|Ì¸\×{a\"Áq­\è#Ož²1û\n>®™)½®Mû3$¸Ù§\ä\ÌL·\ÕÀDÎ®!öÈ‹€	Mú’·DrSþî‚›pÛ³¹\'\á0\ZlG\'%®‡{a%d(„t\ÆY«üø‚üÝ„\Ô@gu¦Gcµ>~\ÎKqL0Ì¿\Ä×†tF)O¤´Lj\â{„q$•f\'\ÇN\Î\Üñ¹sif\ë\Ð\ØQ€7B\Ã\Ü#©`‚ &\Ý6¥\àŒ\Â\Å\"¸Ž4L°¨o\"l\à:>}56a»\ß\îÁr\Ûo–*\Zox\ÚD–\"C¿S•\ã\åm\ÕÁ™\ç¥×œ\æ\Ý\Ê_tó¢2Ï¬G@K\Îa¢¥frr\Ô9‹–£Š\ÈuRg1\è4\ZƒøC`c¾ý\"a\Z\í;\Ój*\î´MŸJ1\ÃMn¸loýb4,ý\Î\ÐjŸúŸí™KH]\'`\ëc´™ð\ê«þ°>o\î\Ø/2Mj\Z¢l5–\Ï_^VƒD3¯<©¥<›V¤~~5˜D\Þ:\Ú\'IIg\Ô\Ô\ëZ\Ò\Ú\ìü£QÀ¶M‡}”t*vj¢i4aÈœf¦d5†\0\Ð5	bABù\Í†h@i)\ÏB\0‘mž=ƒ:€\Ów\Çð\ÕúJp#¬Ž\r\åo¼þˆª\ËÂšY\ä\Z™\r3\rN.PÀÊž_1\Ã\ì\ïT¯@\ææ¸¢vL&iƒ¢\åüˆ \Èl \ê†U‡lCm§™B~\Ð}\ã\n2A\ÑdmŽ%\Òü\å\ä\å\"¾¿%³LL#\Î{g\Ò/\Z\Ï\íX\àO(<úi?\È\ï/˜™uÁ”`\'e\äF\ÍId¡OL\Ò\Ê7,\Âö\Âz~<kµ{ø±pÁIæ¬‹PuZg( $\å\é,\Í\ç×˜>\Ú&8¢a-\à»6o–“ž¦¬”¾\èƒB\no…\à!}¬yÀ¨™\æ<\r™Á#&Ra4¥™‚L¶>¼^¤·C\Ñ*µd£^¤w\Z°\Û\åC\â\0s½\å¹ñÕ€6²E0“h¶@%÷X‚\Ìl	ð†\×Ç¦H T¿DúÀf²õ™\Ö	\êô#V\0pý7‡@£ ™H1%\àpò,\\©­*~Möˆ\n6*_~:ÿ+\èR³ ùž\ß‚\ì\Ëþ¬¸0\\j.;É¯öip\Å\è\Z°bÀ\Îþ¤_4]d¼>\î\Ù1[°)@•Š4š\ì\Ø@÷]ÇŠ\éŸþ²~4›‹P“\È\ÈŽ¢ð{\ÔBLnA\ìe*ù\ém4˜´Ð‹ø_ KŸ2ð?e²=-—\n	\"d&\Úó\ç\Ò`\Ç\ÇcR@ODKS\ÖWÀó¢]š<;–L\Ó×€O|¶³¢©L&­\â4™T•’Šö\r¦i%%;Õ¨\Ù*\Þ=P¬OAyi\Ó\Í\Ì\È/¥k€L”\Ì\Ì\ßÂ0	1u,\0­Á\ÄõÕ‡g£D_ŒôP”B‘(œ;\Îv| $@Rè’¹d&\ì\ÔÁ1-\ë¼\n~S^ýØ $\îT«Aó¯ÀLtOƒû\ßûði1³.Àa\Ît8) :\æ\Øl–EÀ\Ã²ë«†Jš™U®\07\àÐ\Í\Ì‚›~³j”	\à¬¾»¥\é\í1­\ë7\ä_œ-B8~ƒi[t@E\Ø. ü9³Ž˜;‘¶È‘©Iõ¬\Ío\Ðó&$‘+-  \0_5\Ü\Ê %kƒR\Új@\Øòq`Ç—#ýµ?«{òŽ\0NK¬‚ Øºv\ålZ>õ»V3‹uó})\r(”$xk5Ÿ}\êK\')4‚cú”€B B³\×\0]:c€!`\Û\Î\ìc¢\Éÿ\ëY“\Ûg}yŠ)\0¹–‡ªlÁvŒ\0–v\ãk0.õ\í¯\ï\ï\ÏRjelÿm*ø[ZmNoSN\Ä~X^ŠO\ë<\Ø©BM¸ …ð\è¬\Ô,oXF¾\Ë\Ï1J0—?q§A¶>št\å\Ú\Ô\Ëj\07²¦Ê¨ˆ¤L¸}²E)<0?_æ’†!V\×ú2C¼Š@‚¿\Ã—§U)ó6\Ð\Å\"\È\Òx$\Ú;\r\Ø!\ÖA‡U‚”:\ÕzS\ÂC€Kcò_c\Ñpû™ûL\î3˜2ÿEh\É,ó\Z\"L!\ã\ïL-ò½ýmüšf†øe”\ï!)ß\"¼™gŸw\Üda`úH˜`ÂŸW›\ÌlJ\Ä\ê2\áR:o‹¸¸*Š·\\\ÞVŽ“Û™˜höe–\ÙNJý\nœQ%\ÂS\×5(Œj™b[@§\éù\'\ß\Åcfò\Üvñ;\ÊTc26úó2\ß}9t(,Yö\Ô¸ \ÇgekÀ\Îÿ§ØŽòüyšyô¬YR\×9k«À\ã\å?ÀfÒ¬V¾sûò½+¤Uf\Â`\éš\ìN\Z7Â‡IW\0Æ„h¾@Ž–\Z\"-E\0ò\Ê–À9¡\Ä„\0„\Ç÷dmŽ,\Ï\äøV ò	H\Z-¸\Ê²/fÚ«A÷r}\Ïò¼¬ƒ\à—9Ã„‡s¡¬Ÿ\çjùm®eE•f·¬­VõÙ£²N\Üc\Æ\É\' ±B\à:@¯k3sy‘O\Ë(\0`4ó8J(0™\ÇhÀ»F+	\Í0\ÍJŽ¾\à\Äßª˜.1…}I!	ž‰…¬“\ßýg¼þxŽ\Ä¡Œ\r\ØÏ¢yŽð\"ü™”\í4á‘Š‰Ì{¬µÿ-‹/¿ys‘¡;Z©“™\r3õ>\ë¾1\èy}f¹v\Ë0\Ì\Ä>û\Í4¸\Ãg;†–\æÀ(\ßb²\ßò\å\Ô\ç#0®A\Ø”ó\\ƒ\à\0;û\í\Z~‹#hg@\Í}\0\í\Z\ÎË«D‘cD\ä,”\\<s\í3‰d.„\ïSe.óyQùñ›\Öv+s¾P°E\ÓM\êMy\Ê\ç8\êÏ˜\É4h ûgšNj1\n#}†¢%\0Ál\Z(\0s<s*ú§\Ù|¤w»@\çÅœ\\C.L`b¤k\×`¦]ˆž!\Ï\á\Z®™\é\ÊB\×t~ž\Ùw÷ã·¹Â–E\Ó\ÌS\ëÿF‚>£Š6§®ˆVoÜ¸‚­3[Š$Z\Ä>\Å\Ã÷µ>³21M|Àz&`ah:†òƒ\î+weÊKÀX¦—V\ì\ã;]3\ãÀ\0v{\r\àº€\"0öõ…\Ïu<·#`‡\\\Ã=h4a 8\í,\à¹ß›¥¸\Ø3jds\ãÁ\Õ*Þ§´ñÀ(„`Öˆ™•#§\ÎöÁ·¾IaF5Ž@\ÉQi3\æF[û±\ßBŽ€\Ìú\å\çü\ìÏœ4üTüF\\CÀL“\é\Ûö–­e\Ü\Ã=[0¡cþ]w6 Ã¯	ŸX”lÊ½^R)ó=Wt«\çn¨jF›)½³QL»´	 \0\"\01ñ´J‡\\›‰§¥Lt4\ÅTò­­0\ä»\Ñ(\ç\Ð[XÖ¤\Ñl\Z\Èbd¿k³¶s-\Î˜keˆ\É8·:û<Ð¹…ð\æòPRBÏ¥\0%\î‘\Ú1X­¦\ÏlZ\Ú}¦@0§	×¡ypy/°™dDC2y0ÿDhºx\0(°\Ô0-³\í˜ Œgni©ó2\Ï,÷_\Ì\ì\Æe\0[\åÊ˜u®a›\Ï\Ü\Çw\Ô~\Ïù\É\Ç]»}…˜Ï¬LIÎŸ}%\áÊ¼¬?\nƒ3\á\0uú\îe~—\'\Â\æÓ€5\Û:¨¬\n\Ø4,‹£	y\å‡È–‰—ÿ\ÞKJ€\ÃC€FùM(\0G›}W¤H¾\Þ\0\Å\"ð\Ù\"p®‚f÷¯m[\î\ÑRRÁŒógR#@Ûµw¶µ\éZ„ì›(ƒû›\ë†\æ,¯·X\ì>X×­\Þq ŠÐ\Å\æjcÀÎ«,RerMTŒvc˜ß¾#>PJ°ùg%C¬&9\0˜3:Ž€\Â5˜B…\Ñ}\áð\Ü_g\rvŽ#4>û\ä¼Dê™‚LPtH6@°ZJ\è¼?Š(\Ë\ä-J±¢sÀ®Ð‘R\Ý\âA e\ÎwCyñ°™\ç\0ŽŽ\Ç4 Ò¶¼9*\Ò\í;\ß.ˆ•ª.Y’,h\"\ëôh=0TÀ¢!|n_{L€ö{\Ôq®A r÷\Ìd\æ;ýlûLp\ææ¥¾\"\ä£\Þòi\ÏÜr,7g^Á1W\Z\ØI\Õ\nô“’G›Æ”eDM-\Zƒv\ÈoQ.&«>\åM‚\áü–qys2Ë¤\ÑvÿZ\ìm\ÆòQc\å´@\n”w>û\Ú:Ž\ìo¬g\á\ÃEÜˆ\Ù÷;\Ñ;@\Ò\Ç>`¶\Ñ\Î\ÔZ¿\Ýj=\Ó\Þ5Š>+asA*|*…\Ü\ÍIõ]\nv¥6µö-Ë§Ÿ#º\Ù-6RKú™L3X€œ\áB`Ù¶|ŠŠrË¸|&\ÃtR¯ò§\Úd¦G-„Tj¤ý‚\Z²\ÞK°\Ã\n8G~\È\Ððwþ_•»aI•sš‘7\ÖHdžH\í\0K°ü¦\Õ	\è²-k\íZÀû¯EÁ3÷\æŠ,2G\àœª\æm9X_ZEŽÛ•_Â§y÷XVR\n\Ö08Á&4\ÚN\ËÇš·¾\æ$¯\çBhµÉƒ‚;+7¬‹óþQ“C^l2\ë`Ý•\ç£9*¯\Ïv\ÅXeP\ÂBH=Mž~´\Ú\ìYd»\Ï>	\âb\Òv\Ýsðœ°“\Å(\êx&nkI\Ñvƒõ­U\ä¸K¾4 LÄš·94/°¡U4\Ñ\"\æ1œ•ZðS½\Ê,X>\ÏR^þD\Öølo\0¿žW¤H#ºR+\Å,@8\æ^-0´”i\'p\"Ã«-9€#€´\å\Õ\ãx“e\Õ\î-\Õ%tú²´h—Áú\Új’\â@—NCuª÷®š\ÞNfø$O„€·Ñ‚\àwÀ6š\Ägð…if®iPR±P€K\èœ7\Ê/\Û\Þ\Þ\Çy0º\0\×m\Õ7\àöI\ÆÐ¾÷%s\ÔU\Í.R>^ZŸ{\Öó&Ûµ¹˜w\ÅP:b\Ê1;û&!@\0M\Z8 Æ‚´\ÔNd\è¯E¾· ¶~4`!\Z\ÉÏ»gü1`\ÒDƒ8\"ü˜cÔŸ`‘<<‹óR£\ÜK\Ç\å\ÉTX /)e\Øupi\n3\Û\èg\äf\ä\é4\nƒóò\Z@Þ¶†A³Q\ÞN¤j„)Ìž‰h&OúÀ9¦òÜ´°4o\Ïoß.\n\\f\Õô\Éöb\ßJÁ¢Xk¬]\âöƒ«Z+0¶* Oº´\rð°t#˜ƒfz\'»\â‹\0LF\ÏvžT\Ë\è—\0\ÏúiŸ~Ûž\çõ,´\Ñp¾6\Û\Ð\é\Û4\Äbp+´;s\éN¬\ën5¸Š¶\ä\é\Çð\é@W{—«3{#úLÀ\Ådš0ˆi´o,\åŸ\Z_\Ï\"ecq|&r·?Ö£OY\ä\ÏÔº7&tb¥õYp ˜²t\Ø=®„\'c\ìÇ”—g_õ[1ö *Ž,§\åET\É00\Ç0&LdöG“Tœh¨#\Ó¡Gy\Ë°\r<(QšŒ\é|¿DÇ\É=˜mõ}\Ç\n¨\â\ÃG\Úÿ³\á\È[\'	Ž·//:hð?\Öøõ=\è\Å\Èe<—¶ˆ\ÂliLKy\Ñ=hó	l\Ú\êw¢Û™\ÈuaL°\ë‰\â³€ \ÈÉ³~\Ý9>ûdþY\Ð!ÀgÆ¬Ï™H&‰\Z©\è-.\à÷ü¯¶k³Š\Þ/¿~)óž9rr`þ,m”Û·öª\Üó›#•/\æ6‚2Š²jSõ\à\êý˜\íºrh\×¸ˆ\ßuf\Ì14`:™F\î3Ž²\à™siiù\áµo6¸\Z4&~\ßýts\äCEøxA]1ŒHSH\0m¬ƒ*\0úS‡C£\0\ÏK†˜\Óp‚Öž\×‘G\î!ª\Î\ê\Ò\Ôö\Ï\ê\Ñ\\/\ï…9½ú¸\ï\àj\Ö2G\î\Ð2ñËšÿE¼\éG&„Y\"Zû€M\ã˜\r>9O\Õ/€3ËŽ\'(-\àm¦\ë |§­\Ì|ürŽ³½Í¹lÀ—• Zš-0»ú¶}\ëý\È\n\æ.\Î_\\f#­Áfu\Þm\Æ,+\Ü(£€1„\áb\0‘¹rj4R„Ì‡\Ûn4*\ZØ§€Û§l\Ïg&2f>5€~q=Ã‘öÖƒùöÿ–4\íP~ý\Øò\ë\Ëóäµ¸ø@.3œ÷¢ \Ú5\È\Þ\0„‰€DÖ¤\Ó|ÑºŠhg\ê\Ý\Îk?[\Êq™P‘Q7\×ŠÀE\â\Ë\ë^\Ç\Ö\çƒù6#ð\ÛZµZ\Ú~AmÞµù\×Æ¾M~0ö­`\"oñ&²N\Ü\'Kt[?\Z[¿\Û?oØ´˜\Ö\nø¸Ï€2V.§¶ï‚Š;+Ð·Ì·©¦DoQ~}A\Ír9%¯ñvG\ï3\î\í5\ß,³,\Ê70ˆf\ÚQ\ë#c\Éþß™_\à)²\Øp¬Hõ=\Õ1 ¯*ÀO©\ã”Fo1˜o—¯°;\è‹\ÊÄŸVÚ¾Ò¸w½&Ô˜÷p1]-‚\',€4O@\'÷õg¬=“ ˜^&e:±\íö«›\Ó(ž‚Jj\å>U\Ôl“1\Ø/B_Yƒ7§U)tQ½ó`¾]!Å›\r\Ë\Ä\ïX R\Ú~B­©:¯\Þ\'·\Z\à&=\\pÁf¿X3\Í\rd\\I\×\ÊU5‘¹\ÔN-]\ÕN`È·‹\Ô`\Ô\ç3!BFÀ¿€¯.ÀÏ«B\Ë	%D‡\Ø;– l8˜o\ë®\Õûg6+`÷(xõwMs:¹`‰v\08\Ê÷T\Îoòƒ€¢%Ÿ\Ðx€\Ë\ïW˜iR³_N.°®\Ò\ìcÓ¥Ý›\r\æ\Ûú\Õü³ƒ¿ó(¿W½…jÿ\ÖT€_\Ñÿb9iº`\\]š¾²4}Yiú\âJÛŽ/°Ö°\ëþ¥\é{\Ð\Ûð›\æ\Û|›oó\í*\Õþ‰;÷\'¥5\0\0\0\0IEND®B`‚',0,'superadmin','2013-06-05 15:15:03','admin','2015-05-06 15:27:35','','For Test','sadf',0,NULL,NULL,NULL,'0',NULL,7,0,0,0,'',NULL,NULL),('c1cabfaeb9a448028ffab2148da9f65c','QQ Login','QQ.exe','COMMUNICATION','7a1e2f19c8f21ca9405690d2fedf8c4d0d9f57e9a797732a074689ab39238a2d1e698f87c64032dc548d6ec7dc3c4863','Desktop',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0B\0\0\0B\0\0\0\ãT\0\è\0\0\0	pHYs\0\0\Ä\0\0\Ä•+\0\0\0tIME\Þ\0,!C\0\0\0tEXtAuthor\0©®\ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬\Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0À\æ‡\0\0\0tEXtSource\0õÿƒ\ë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨\î\Ò\'\0\0+IDATxœ\í›yUÕ\Ç?\ç\Þûö\ÞW\ZºÙ›E`PP\ÅHR#1q’(5\Ñ\à’Q\ãdc‹¨Q‰\Ñ8\Æ]£ÅHD\ÜP¥¥¡Þ»_w¿\í\î÷žù\ã½f1N¤\é\Ö\ÌTõ·\êT¿Wõú\Þ\ï\ïó\Îù\ß9\ç>áº®dH(ÿhÿW4\"§!9\r\ÈiDNC r\Z‘\Óˆœ†@\ä4\"\'í‹¼Ygg\'¯¿¾ž\ron¤¡añx7Ž\í ¥\ïû\ÄbQ†Wg\æ\ÌøÚ¼yL›6\íó&¾ˆµFkk+÷ÿ\á^}u=é¤c;8¶\ï{ø¾R\"Iîµ øœtò,.¾\è\"N™=ûó3—»÷\ç\â‰\'Ÿbõ\ê„±M—d¢SO\ãy6 B\0’¬¬€€‡\Ãùÿô-~rÍµ\r®9\ÙwWñùð}Ÿ_\Þò+^~õm\n\n‡\Ñ\ÛÝžI\à9&Žm\â:&¾\Ï\ÐÁ÷}\"‹cÂ¤ñ\ÜuçŒ7nJ¤ñ9&ËŸ_#\ë7ì ¨d\ÉD\Z\ÏsQ%÷Mÿ}ƒ‡›•(R¡¾n.]JCC\Ã\à\ì»M\Î\Î\çb\å\Êß°a\ãvB\á’‰¤ô‰/Á0Lt=ƒ”\Û¥”¤\ÓR©T6w\ä\\J$A{s?¸ôR:;;\Ýó ƒx\çwyðÁ?’\ì\ã¹6¾g\ã:\ét/S§Žc\æ\Ì/\á86ž\çø?\Ïóp]‡gE\ß<E¸®û‰«ö\ïi\âg?¿þSADƒ\n\Âqn»\í‚ÁÒ©Žc\à\Ú®c‘Lvsù²yú\é‡x\ê\éG¹s\å­ø¾‡”>RJl\Ë\ä\æ[n\ä¾û\Ç=«Wq÷ªH\é\áû‡\àµW^\ã\égž˜Ù¾š»ü ‚x\é¥Wh¨ß‡iš8¶Žk8ŽI2\Ñ\Ëôi¸ò\Ê\ËP€E\ß<—\ÅK¾M*™\Æ0f\Ï9‘.ø\îkÍŸ&g/<‹L&ó7\îU¡q÷Ý«\Èdô¸žd?þž+±­®m\âXŽm’I\'˜7\ï4\0l\Û\âº_\Å\ÕW,c\î\ésˆD#Ø¶\Íü³Î¤n\ÛV–.Y\Ì\Û7ðÕ¯ž‘\Ë/}\Öªµ¥Ö¾80\Ã\â ŒA±w\ï>>Ø´\Ç\ÎN¶m\à\äš\çšVÀö­[y\ï\Ýw˜<e*õ\í ¤´EQ(--a\Õ]+8~\æ	\Üÿ»{ð<\á\Õ\Õƒ\Z\È\Ã!H\é£)\Z\Ï?ÿ\ÂÀbð@lþ`–™­=\×Æµ-\ÛÂµ-<\×Á4M\0\n\n±\î]M,Ë–RR\Ò3\ë¤\Ùü\è\Ê3l\Ø0\\\×C\×M¤\ïb\Æ\Í~\Þ÷<v\îl ½½}Pü\Zˆ\í;\ê8˜\È%\à\Ò\à\Ã·0z\\-—_}-‹¿{c\ÆO «#;\îü¨\ï,ý>ø×Ÿ^O(\äýM›p\Ãû!ð<\ÓrhØµkPü\Zˆ¦ýM‡¼;\Üx$\åùg_ q+BÀù‹—p\éWòøcO\â:.±XŒ\çž[C\ã¾&P£=ž\à©\'ž$sx‘••”U\Õ\Ø\ß\Ô2(þ\rDú@—|\Òx  \Ò\Ñ\Þ\ÉU?ºœ\Í[¶Q¿§…\ëq=ü<¾\ÈÃ°5\Z÷õ°dÉ¥¬ye#\ë6¼\Çe—ü\Ý7…¹Ò¡%¹@*\Z®iŠÿAYk$ZZø\î¢ó\ÙO¡RöIJ‰\åª$R:\Å\Zc‡k„qÆŠRT*\\Ÿ®Ž]=\nMq\ÆNK\äƒô‰}@H‰\Z¡•²ddÿöÈ£(ª: ¼!¥\äõe—QòÑ»¤FS\ã2\åel…€\'ŒNs\æt™S\ÆÖ†(Y…aP6¤tHˆ§°;-šº4\ÞmJ±vGŒõ\r1R– ’YÄŠ€`³£§þm¶=ö\Óg@q¸G˜\É$N?†cÊ¢\\º§—\î¼Jbª\Èõ…\Ùc2,›\ÛÉœ	:Ô”À\ÈqP\\	0h*H	¦\é$ôöBW7tt‚aB\0ða{sˆ»Ö•°fGE¢\ÃôX‹\Ü–Œ(£)¯„ó_úB9ú‘>\à±k\íZ\\Û¦²¸˜›J\\Hv`ø\ÝVù\Öq½<vi#sjsÕ¡\È¢\à+\ày\à8`[\à\Ø\àº\àz\Ù\æIppaJ¥\Å=ÿ\Ò\Ê3\è^v\Ã\à\é½\Ì÷S\æE¡¹‘t¿b\Ùi¸¯\rxh´¿µ‘šŠºº\ã\ÌBg\Åðb®i“ÒŠù¸+LCkˆ\Ú\n‹—·\Ç8‰òd\n\Â‰@ \×#lt’H\ê`Y\Û¼\\—\Ç\é3\ìi²q_”3\Í%y:—\äy4¦<ò|“H,Ï¶.€\ì¾\ÐÀAä——Q^ZÀþúzò=—3*\Â<:ªŠ\ë¶\ìaM]”ºßŒ\âÂ“’X6Ü±.\ÂO¿g\Îô°U@Ë–®Ž–\r\Â\Þ\Ù\áö—\ËY\âòöþ(«þ\ZDd2\Ü^˜\â¢Éµ¼½\Óc˜Öƒ\Þ\ÓKt\â\n««Ç€AôT§\Â\ÈP\êf\èv 0abª‡«Ur5þ\Ðnqý³aJb\Z*–¬\ZÁ©“œ`p\Ü›Š|MJ,Ó§5®±¥1\ÆKuy¼¾+BÂ”¸\ÒFX\çUÀÕ¥&µ–\Åþ^\â\â4£‡D<Îº\Ö^\Æ~¼‡q\ã\Çö?€\\†P²Üº­Ž%K—qNóv+]\Ø\Øø©\Ú\Z\ÐRJ«+\é=…\çö¶òb\ÂgGÚ¦\ËôI\Û* (\n{ç¦iCwZ’´4\áQò¨\rI\æE\Å<&\Zƒµg\'miÒ…\å”TUÐ¾u;u‚\Ü]4‰I\ÇN\á‘G\î%tX\Ýñ÷	H\ÉÀ‡F2™\â\ßò|\Ë\äI­”òT‚…\ÕQ‚šO&e $]I~GK{YZPDÛ¤±\ì´|\Z\â½4;\Ð!£–\r¶…\ZT(«1\ÂKS+,&—1.?Œ\ÒXºƒ™)\'¥\Åpý4E^\n”\Ô)Q~¬ÀV5>n\ØÅ½«\ï\çG—ÿð¨\â9jwþú7tö$Àq1´\0¿ŽÄµ\Îjk\'‚\0”H^v%jXŒvªbùŒ²\Ò|-‡µ\ÇBg$z!„šZØ½2:dl\ÛÁöÁ\ÑM\ÔHŒ‚tu›| »¸#XC‡T	™:J¤ˆÿø8\ÎgÔ¨‘G\È@6f¶l\Þ\Ê\Ú\×\ÞÈ®*\rƒ\0’Œ\Z\à\Ñ\ÂQlœp\nuºŠ\íC \ZE˜:B€\ÔÀ	1m\Ûrð3¤Ò\è\Éý\í…L&›4=™RU\rT$œZ0À\î8l|\"O?‘F\Ë#Œ2\ÉJ À}÷>Ð¿`²y{\ï\ï$\ì\îA\ÉÖ¾8†Î©sO\ãŒU«\ÉÜ¸’u”³³\Ë ™Ê€\0UUPU\\\'»¬V(JÖ‚’3$(Zöµ\ëd?£i86\ì\Ý\Û\Å\ÆL€†¥WQ½òA\æ_°” \Ì	‰c[Ø–\É_\Öo ½½ã³ƒ\èK’¹·ý\Zõ;wñþæ­˜†\ïùE ²gEL?n\Z£+J\È?g\Ï/ÿ\ï\×7T*UÁ\È<…® lš¨>(HP•\0 ¯,W\Ò\ÓtÈ´w³¿\Ëg_l$öYg1l\Ñùœ4m:a-„\çz‘J&PU\r!\éDUSy\í•uüó’ó„\Ãõ\Ä\Ú_\Åq]ôt&W\Òf+³P8Ä°ªª\ìE[[™¬I¤=Ÿ\íi—Ý½.U›wD(–*	“|jo\n\á\äŠMµ‹T\Ò#\é’®¬Æt\á\ï\Â\è“O¡¦v<\Ñ`Œn‘6lòòò)*)¦·\'ž]p	°-Ç²x\ã7?\Ä ò(Alzo3Ži\á{B¤\Ì^,‰\ÅðDýN\ÂJ€Ú±#\Ð\Â!bmITaÇ a\æ\×0\æ³y\Í\í­`\è\ÙQ‰¢¨¨$6¢†\á5\Õ——S˜#”ë¿º\å\â¸®ë£¨\Z\áHôÀ\Ùi\ßwl™\r\rf\âMý\Ñ\ÜÜ‚sh9›»ž”\Çqpts3Z[\'„óT˜òK\n)(¬®¤pÁ|¢\ÏÃ—}\éA ª‚€Ÿj\Êó%®\ë\áy>žŸm¾ŒCö@lË¤§;\Þß°ú\"J\ã:\ÎaEÁ2Mº»{q\0_7\ÈY‰^\0ªG©®#MZ{ð“=¨B\n„´#\ßCp=\Çóq=O‚eš¤RÙœÐ—_$\à\ØÎ§}¶Žj\Ö8ô”©¢²¸ŽËžw\ã¶¡c\ïn\Æß·¿³•0‘\âb¨¬ÂDÛ¢?ù¾\Äv<l7\Û\n]ôt\Ç	…Cœ6÷´ƒ½BJþ—GýQXXp\àõÄ‰µ¬¸\ëWD\"!Uaó¦0mPŽŸ…6¡†`õ”\Â°Mh\ß»v!¦ùm%`9–\ãa;Ž\ë£i>Ú¶•L:\Å\ÈQ#¹ý\×Ë™:\å˜\ì’\Z\ÉðUý\r«ÿ ú6ùÜ¼üf?9§\Î&\n±y\Ó&>\ÞQOÑ¼3i9÷;´¥L,\ÃAÆŠð+jH¯ANÁ‘®\0\Ç\Ãt\\,\Ç\År²G€®\çòÆºW\Ð4s. ¬¼Œ[~u3ùù1@r\Ò\É\'ö7,\Ôn¸\á\ÆþüC8¢·7ÁÍ·\\\Ïô\é\Ç0z\Ì(žù\ÓstÇ»‰\æ\åq\ê\Ü\ÓÌ˜EüË³‰W¥§f<=SO$½\èûTž>\è,Œ¤”8®a¹¶‹a¹X¶K8e\Ëû›ø\ã\ï\ïaDõp–\ßúK\"‘•\Ã*™qü—I&“\\|\ÉE”””ôÄ =(r\Û\í«Xy\çQ˜a\åý÷1iòU †‚hš ¬B˜#ë‚ž\ï\ã8†\íb\Ú†\í ›.Šª •\ë–ý\r\Þ\ä\Þ\Õw³p\áYƒað¶ó¯½\æ2\Î:m&-m	V\ÞzÉ´Ž©[\Ø\É~Z\Ç\Õ-,\Û\Åq}|)«\ì$\àK‰\ëù˜¶K\ÆtH›º•k¦ƒP ??Ÿ\Çÿøß¬[·ž«.<o@Ý¦“Röh|š<Û m\Ír\\\áó4´¸¼ü\æ>,=\É\Ì9§\âù\Çñ²u€\'ñ|\Ï\Ë\Ý\×l\×\Çv<,\';¬=!;$šJaQ!\Ï=ógþ\ãú›X4%\Ã5³öÒ»¿‰Põ—\ÑB\Ñ~{¹3Ï¾ºjÀCÃµt\êWŸ\Ú	Ož\Û+Xv[9Ï¾k²\ä‚ó¸âº«‰F\Âø®C@U\ÐrM(^%\Ù)\Òó³`\×\Çr=¤”\äE\ÃC}\äqnú\Ù\r,ž\ÖÎ­\ßÎ?œT€Þš\ÅØ‹Ÿ \\T~”d7hb\ßÆµ¸ÏœK\í¹“`\ì\éò0\ë7±|\Å~V®Q˜:s6Wÿô*&OŠ\ç8H\ßAE\Ê\\)%¾®Ÿ$\É\ÏË£¥­ƒ+~\Ë\ëO\ÝËµs{ùÁ‚|S+!¿ö¶\Óøjôñ¿fò9ÿcAlú\íO˜|‚³¿Š\æû\àl‚x;^Ž²ü\Év\ç+gŸË¹\ç-d\Üø\Ñ4\r\Ïs³…U®8BASUA\r!Tš[;y\á…Wxñ±û˜\à¿\Çu_w˜ö%¿¶\n¥\êT \n\ÛH¬\Ý\È\Ý˜s\ÃC1\à\Í[+\ÞLA­	]¿¼6ðMPUf\Å\ä\Ïc-^{+\Å\Ã\ï\ÝÁò¿>AÙ„YL™1‹	“j1¬œüXEQ0m‹ö\Îv\ï\ÞKý\æw\è¬{ql\ç\Î\'OP- ,€\âw@\ë«œ¢ˆÂ²<Ìû\Z\ÆÀAtUŸB\Ç\æg©\ì‡2‚PÁ\\Q&8\ã‡3&š´\ÄSl\Ù]G\Ãú‡\Ùød1\Û\Í\n\Ô\Ò2TUÃw15\Ø\Êqq–”˜L™\r•EQ¦A±\n	º\Ìô€ûôH\ï\ÐUs\ê?„”’	\'ž\Î\Þ]À\Ü5/ñ¥\É¡\n\"T>\áj¸iÁp_R9LPeú´fbL9f*‘\\\ác¦ªˆ\î6™\îbFE\0MH\ì„ŠTH\nH\Ð$H–ŠÓ¥P·^ò\ç0\ïŠo#¥ü›¥u4\à\Ñ\Ö\Ö\Æö\í\Ûy\ïõXu\é4R\ì¥4\â\0tSÒ•”\ì7Ø£Â®9–\Êq\ã‰D\"\Ù\í;²O\êZ–EÛž=ˆ\Æ\ã4R£õP–\ç‹ª¨ªÀt%=¦J³]\Ä^µ†À¤S8a\îs\Ì1TVVeƒ”,¥”tvv\Ò\Ô\ÔDKK­­­t·w`gRX†Žô}´P„XQ1•\Õ#¨®®¦¤¤„H$B,##„À²,2™†a\Ð\Û\ÛKSS\íM\Í$\ã]Ø¦Ž¢(\ÃBù——3bDöZUUU”——¨7À –\ØRJ2™™L]\×\ì	!ƒD\"¢\Ñ(\ápø@/ø\ß\äû>¦ibº®c\Ûv\îI\Ü\ÜNX$B^^\ÑhtÀ\0úô…üL\áÿƒ†~Á“\Óˆœ†@\ä4\"§!9\r\È\é\0¿ùANØ†\rM\0\0\0\0IEND®B`‚',1,'superadmin','2013-05-25 09:37:51','admin','2016-09-27 23:03:17','QQç™»å½•','è…¾è®¯',' http://im.qq.com/pcqq/',3,'','','uid','0',NULL,11,0,1,1,'org.maxkey.authz.desktop.endpoint.adapter.DesktopQQAdapter',NULL,NULL),('c3d44bb1-e2c4-45dd-91ce-43e821f1321c','Liferay Portal','http://liferay.demo.connsec.com:8080/','OA','d9457a9a9017d2f92ce3d0b58e4328ea637dcc5a434d3bc900bd5f07cd1eda86','OAuth_v2.0',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\03\0\0\03\0\0\0:¡0*\0\0\0	pHYs\0\0\0\0\0šœ\0\0\nOiCCPPhotoshop ICC profile\0\0xÚSgTS\é=÷\ÞôBKˆ€”KoR RB‹€‘&*!	Jˆ!¡\ÙQÁEEÈ ˆŽŽ€ŒQ,Š\n\Ø\ä!¢Žƒ£ˆŠ\Êû\á{£kÖ¼÷\æ\Íþµ\×>\ç¬ó³\ÏÀ–H3Q5€©B\àƒ\Ç\Ä\Æ\á\ä.@\n$p\0³d!sý#\0ø~<<+\"À¾\0x\Ó\0ÀM›À0‡ÿ\êB™\\€„Àt‘8K€\0@zŽB¦\0@F€˜&S\0 \0`\Ëcb\ã\0P-\0`\'\æ\Ó\0€ø™{\0[”! ‘\0 eˆD\0h;\0¬\ÏVŠE\0X0\0fK\Ä9\0\Ø-\00IWfH\0°·\0À\Î²\0\00Qˆ…)\0{\0`\È##x\0„™\0FòW<ñ+®\ç*\0\0x™²<¹$9E[-qWW.(\ÎI+6aaš@.\Ây™24\àó\Ì\0\0 ‘\àƒóýx\Î®\Î\Î6Ž¶_-\ê¿ÿ\"bb\ãþ\åÏ«p@\0\0\át~\Ñþ,/³\Z€;€mþ¢%\îh^ u÷‹f²@µ\0 \é\ÚWópø~<<E¡¹\Ù\Ù\å\ä\ä\ØJ\ÄB[a\ÊW}þg\Â_ÀWýlù~<ü÷õ\à¾\â$2]Gø\à\Â\ÌôL¥Ï’	„b\Ü\æGü·ÿü\Ó\"\ÄIb¹X*\ãQqŽDšŒó2¥\"‰B’)\Å%\Òÿd\â\ß,û>\ß5\0°j>{‘-¨]cöK\'XtÀ\â÷\0\0ò»oÁ\Ô(€hƒ\á\Ïwÿ\ï?ýG %\0€fI’q\0\0^D$.TÊ³?\Ç\0\0D *°AôÁ,ÀÁ\ÜÁü`6„B$\Ä\ÂBB\nd€r`)¬‚B(†Í°*`/\Ô@4ÀQh†“p.\ÂU¸=púažÁ(¼	A\Èa!ÚˆbŠX#Ž™…ø!ÁH‹$ ÉˆQ\"K‘5H1RŠT UHò=r9‡\\Fº‘;\È\02‚ü†¼G1”²Q=\ÔµC¹¨7\Z„F¢\Ðdt1š ›\Ðr´\Z=Œ6¡\çÐ«hÚ>C\Ç0À\è3\Äl0.\Æ\ÃB±8,	“cË±\"¬«\Æ\Z°V¬»‰õcÏ±wEÀ	6wB aAHXLXN\ØH¨ $4\Ú	7	„Q\Â\'\"“¨K´&ºù\Äb21‡XH,#\Ö/{ˆC\Ä7$‰C2\'¹I±¤T\Ò\ÒF\ÒnR#\é,©›4H\Z#“\É\Údk²9”, +È…\ä\ä\Ã\ä3\ä\ä!ò[\nb@q¤øS\â(R\ÊjJ\å\å4\åe˜2AU£šRÝ¨¡T5ZB­¡¶R¯Q‡¨4uš9ÍƒIK¥­¢•\Ó\Zhh÷i¯\ètºÝ•N—\ÐW\Ò\Ë\éG\è—\èôw\r†ƒÇˆg(›gw¯˜L¦Ó‹\ÇT071\ë˜\ç™™oUX*¶*|‘\Ê\n•J•&•*/T©ª¦ªÞªUóU\ËT©^S}®FU3S\ã©	Ô–«UªP\ëSSg©;¨‡ªg¨oT?¤~Yý‰Y\ÃL\ÃOC¤Q ±_\ã¼\Æ c³x,!k\r«†u5\Ä&±\Í\Ù|v*»˜ý»‹=ª©¡9C3J3W³Ró”f?\ã˜qøœtN	\ç(§—ó~Š\Þ\ï)\â)¦4L¹1e\\kª–—–X«H«Q«G\ë½6®\í§¦½E»YûA\ÇJ\'\\\'Gg\Î\çS\ÙSÝ§\n§M=:õ®.ªk¥¡»Dw¿n§î˜ž¾^€žLo§\Þy½\çú}/ýTýmú§õGX³$\Û\Î<\Å5qo</\Ç\ÛñQC]\Ã@C¥a•a—á„‘¹\Ñ<£\ÕFFŒi\Æ\\\ã$\ãm\ÆmÆ£&&!&KM\êM\îšRM¹¦)¦;L;L\Ç\Í\ÌÍ¢\ÍÖ™5›=1\×2\ç›\ç›×›ß·`ZxZ,¶¨¶¸eI²\äZ¦Yî¶¼n…Z9Y¥XUZ]³F­­%Ö»­»§§¹N“N«ž\ÖgÃ°ñ¶É¶©·°\å\ØÛ®¶m¶}agbg·Å®\Ãî“½“}º}ý=\r‡\Ù«Z~s´r:V:ÞšÎœ\î?}\Åô–\é/gX\Ï\Ï\Ø3\ã¶\Ë)\ÄiS›\ÓGgg¹sƒóˆ‹‰K‚\Ë.—>.›\Æ\ÝÈ½\äJtõq]\áz\Òõ›³›\Â\í¨Û¯\î6\îi\î‡ÜŸ\Ì4Ÿ)žY3s\Ð\Ã\ÈC\àQ\å\Ñ?Ÿ•0kß¬~OCOgµ\ç#/c/‘W­×°·¥wª÷a\ï>ö>rŸ\ã>\ã<7\Þ2\ÞY_\Ì7À·È·\ËO\Ãož_…\ßC#ÿdÿzÿ\Ñ\0§€%g‰A[ûøz|!¿Ž?:\Ûeö²\Ù\íAŒ ¹AA‚­‚\åÁ­!h\Èì­!÷\ç˜Î‘\Îi…P~\è\Ö\Ða\æa‹\Ã~\'…‡…W†?ŽpˆX\Z\Ñ1—5w\Ñ\ÜCs\ßDúD–DÞ›g1O9¯-J5*>ª.j<\Ú7º4º?\Æ.fY\Ì\ÕXXIlK9.*®6nl¾\ßü\íó‡\â\â\ã{˜/\È]py¡\Î\Âô…§©.,:–@LˆN8”ðA*¨Œ%òw%Ž\ny\Â\Âg\"/\Ñ6Ñˆ\ØC\\*NòH*Mz’ì‘¼5y$\Å3¥,å¹„\'©¼L\rLÝ›:žšv m2=:½1ƒ’‘qBª!M“¶g\êg\æfvË¬e…²þ\Ån‹·/•\Ék³¬Y-\n¶B¦\èTZ(\×*²geWf¿Í‰\Ê9–«ž+\Í\íÌ³\ÊÛ7œ\ïŸÿ\í\Âá’¶¥†KW-Xæ½¬j9²<qy\Û\n\ã+†V¬<¸Š¶*m\ÕO«\íW—®~½&zMk^ÁÊ‚Áµk\ëU\n\å…}\ë\Ü\×\í]OX/Yßµaú†>‰Š®\Û—\Ø(\Üx\å‡oÊ¿™Ü”´©«Ä¹d\Ïf\Òf\é\æ\Þ-ž[–ª—\æ—n\r\ÙÚ´\r\ßV´\íõöE\Û/—\Í(Û»ƒ¶C¹£¿<¸¼e§\É\Î\Í;?T¤TôTúT6\î\ÒÝµa\×øn\Ñ\î{¼ö4\ì\Õ\Û[¼÷ý>É¾\ÛUUM\Õf\ÕeûIû³÷?®‰ª\éø–ûm]­Nmq\í\Ç\Òý#¶×¹\Ô\Õ\Ò=TR\Ö+\ëG\Ç¾þ\ïw-\r6\rUœ\Æ\â#pDy\ä\é÷	\ß÷\r:\ÚvŒ{¬\á\Óvg/jBšòšF›Sšû[b[ºO\Ì>\Ñ\Ö\ê\ÞzüG\Ûœ4<YyJóT\Éi\Ú\é‚Ó“gòÏŒ•}~.ù\Ü`Û¢¶{\çc\Î\ßjo\ïºt\á\ÒEÿ‹\ç;¼;\Î\\ò¸tò²\Û\åW¸Wš¯:_m\êt\ê<þ“\ÓOÇ»œ»š®¹\\k¹\îz½µ{f÷\éž7\Î\Ýô½yñÿ\ÖÕž9=Ý½ózo÷\Å÷õ\ß\Ý~r\'ý\ÎË»\Ùw\'î­¼O¼_ô@\íA\ÙCÝ‡\Õ?[þ\Ü\Ø\ï\ÜjÀw ó\Ñ\ÜG÷…ƒ\Ïþ‘õC™Ë†\r†\ëž8>99\â?rý\éü§C\Ïd\Ï&žþ¢þË®/~ø\Õ\ë\×\ÎÑ˜Ñ¡—ò—“¿m|¥ý\êÀ\ë¯\Û\Æ\Â\Æ¾\Éx31^ôVû\íÁw\Üw\ï£\ßO\ä| (ÿhù±õSÐ§û“““ÿ˜óüc3-\Û\0\0\0 cHRM\0\0z%\0\0€ƒ\0\0ùÿ\0\0€\é\0\0u0\0\0\ê`\0\0:˜\0\0o’_\ÅF\0\0ÿIDATx\Ú\ìÚ½J\ÃP\Æñ\ç$i¡\ÑÒY­[\ïAp\è\î^\È\\\ì\ÔÁµ\à\Ü]Å¡\à=\Ô\Éj\ç~M!Í‡C\ZÁ&m\Îy\Ó{¦BÃo’?„\Ã|\ßGV–þ\è\\]\×%vÚ—&„F³!›¢×»}\0P0øšŒa4Çµ£\Ù0†\Ñl-AP¡µ\Ã\ì0”¯\æ¿V¾vºò{\Ø\ÇÁ\Éù\ÊkF]´\ï^W^\Ó9;\ÜMf­Éˆ^Œû9E~c@1§ ¬krcŠy•‚†Š\Ìe	)r¨\ê\ZJº*\'&€¨(4T÷4”\n*T…É‡	!=¸µJº\n•1nûGb\ìa?r“\Ñc7ò\ZIŒ¡\âý\Ó\0Àõ}\Ì,\ËÁ\Är`\Ú.<?zŸ\Ô;ó{¹ž\Ù\Ü\Åø\ÃÁtþ\rIý™Y7ˆ®Ldl9˜\Î0m/6D(f“ N—·\Õd\îÀ´=¬ûyB\Ù ˆc\ËÁ\Ôr`.Ö‡Á$	\â\Ärð¾!„;&iM\ÛÛŽhŠ\"e“\î‰¡bœ@oM4“Q\è3CDa˜4‚(“V¹c\Ò\"WL\ÚA\ä†Ù† rÁP1N …w†W\ã|\n&‰¦\è ’M†\"ˆdŠ ’a(‚H†¡\"†\"ˆ±zˆ-.“\á\Ä8Ÿ‚ÿ\Ídv˜­Ç„\çO2óx¾´dü\Ïe\á¼\Ë\ÒIÀ\Ï\0MydŒv÷\0\0\0\0IEND®B`‚',0,'admin','2014-12-14 03:46:00','admin','2015-05-06 15:32:11','','Liferay ','http://www.Liferay.com',0,NULL,NULL,NULL,'0',NULL,9,0,0,0,'',NULL,NULL),('c8038bd4-12a4-4b45-9d43-61b3ecdc2eb4','æœ‰é“äº‘ç¬”è®°','http://note.youdao.com/signIn/','SAAS','995c91d60c0c29f7015a1bd0538010c159ec2b5e4130f848a2b9ae2bb2de98ec','Form_Based',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0|\0\0\0\0\0\0-Š¦\0\0\0	pHYs\0\0\Ä\0\0\Ä•+\0\0\0tIME\ß“´\Íö\0\0\0tEXtAuthor\0©®\ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬\Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0À\æ‡\0\0\0tEXtSource\0õÿƒ\ë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨\î\Ò\'\0\0IDATxœ\íù“\åy\Ç?o÷Ì®´ºV\ÒJHBBZÐ1º–C’\'•³ˆ`d\Ì\á8•¤\\I\å?IR®ò/q€b§b\n‚m(‚m!#K$F`!KBB·´\Ç\Ì\ì\Õý\æ‡wz¶g¦û\í»{$ô”Vµ\Û\ß÷}Ÿwú\Û\Ïñ>ývRJ®\ËFŒ¢\'p]ò•\ë„Á\ä:\á_0)=€	\ÎT\àóølŽ_Cp¾\n—Gad,w¦\á—uè²‘”1\Ô/®Î¸ós\ïwÕ¯…>i\Ã\é8tŸ‡\ãƒpr®Œj:\å|’´‡Ó¾¸²¸x<°\Ü	¿Tƒ\ß^„\×NÀÁsp±£“\ík’‚OR\Ó\áN²\êXn„Ÿ«À›§\àW\Ç\áð…:Éš‰5¤NR\ÇwLr |pöœ€Ÿ…#—`\Ü\n1±,0®é§­R\ä\ìa´X\È>™>:	û?‡ŸQ®»At„É…:žÑ˜V}•b™þ\Ù<÷1ü\ß1•eÇ™XQX\"¢ux‡Œ™*\á\ã¼}\Zžz_¹o\ÛQš·‹\Öay–`ž©~yžÿž;\â²\êŒ\Ô\Ýw\'‘©Ã“Ì³.©~jþ\ã \ì>‘0)\Ë\Ñ\Â¯§ýð¿x.¥ZSÿ\à]x\çLtå¡Žg€]KË¬¨:~\èü\Û~¯¯†d¦¢;iLbnKµ\äúþ8z%¦ò«i™Sg\Ñ\î\ÛKbþ\ÞYø÷p\ìrLÅä¾¯Ï”h\Ì,2\á]„\ï\ï×÷•\ÛID\ÇÕ™c²\Z‰ð\ß_Qd¹Qù}™•sœN%†_…‚w\ÏFP#Ñ‰—Y\×Eø˜?þP\Ý	=øõ8ß˜ô.¥\"ú\ÅOÔ¦…¨\n²\Ä\nYfuRì¡/ðcƒð\Ìa¸2OA £ob÷\í‡wZ\ì\Û7n¯NÀ>€\ß]Ž7x ~-¸ï«„hG´„¿y\nö© \í\Ì;‰¾˜:ñðvQ1_\ÂO\ÃsÁ\ÈDŠŠux\'\Å\éN\Z3‰NÌ“ðI[m2<|.¥Ié°¬¬:&&¥Â…hžƒù\ÏE‹\ÇÓ“ðsø\é\'!÷‚a~x‡\r m\è2ay/\Üv\Ì\è‚Óƒ’\Ãg\áb…©« ‡¹d5n\áR\Â+ŸªmJ™M,\È\Â\â\èŠ;—:&™Ý°óvø\Úm0¯`IÁ‡g%Oü\æ¤D\"ñFfˆ„\r<?=¯Ÿ\ËöjžÍ¤/³º=)•5?¼v®…i®³b–\æ÷À¿¾nó¦“\ÄzY{\Üy\á)ž—&\Âm	ûNÁ§9\ÝKL´@ö\Ìnxt\0¾q;tû¤±\Ëz\ß\Ûf ¥\Å[\'H£™ô\Îö\ÝM›>\Þð¼uªeKqRå”}\Ëö?{º\à‘p¿†lGV\Ìü\ã6Û¶xû¤À@9þó\Ôa)¯fMOž‚ƒ­7Gdxaú5qÜ¨ó”fv)\Ënu\ã:Y1Oðøv“»—J•\åù%IÎ›®O\Èó\"[±º4·%\ì;\r\Ã\ã))÷;‡Ì ùDœ§D‘ý\Ø\0<°6Ø²[¥ž\àŸw˜l^\æCzœó–\Òyij\êÑ¯Aø¸Õ²1®…ùYu\Ü¤7\Î<¥„ž’J\Ðþ*„÷“ó\ß\Ûnr§\Û\ÒsðL~}ƒˆv¤Aø‰A85S¹Î«ee\Ñqò	=eø\ÖFØ¹.¼÷\Ç\Ò7Ý¤q\ï1<a¨~^\î;\è¼\à\"ü\ÈE\Zóiñ\ê$šX\Ê\'IJ˜^‚‡6À)\íHÿ|\Ó\ïZ*‘nÒ“z4?i^¶\×õ:\áRª\íKGxƒ”\ëNl\åiŸˆ€“\ä¸ñ‡6Àƒë••§)+\æ	þi‡Ë½Ûš““’û5¦_‡3#!•_%\î\ÛÁ\Z1{\0vm€\é)“\íHÿ|Á\ã;Lnl±ôóóù\Ã\Æi>\Ô~µU\× QÝ·FN¹n\ÜØ‰œw\Üø7\ën<+²YÙ§,}\ã°‘¶L\Åkµ5‹{ñP\'|h.×¢\r\ÐDtšn8-·\'\ë\åÒŒ-»UV.üË½&;ú%19\Ùüg¨~m½\Å\0µ³¥2Þ‚„‰\ÓY¸o]ŸcJÁyY¶[&l\è›)øöf“·\0­¤Gø‘\ÜwH.J ’µ\êDK#¿ù]EYA_Ã¯W	Zžd[PT7ž\Ì<º\É@`ó\ÚQ\Û6!o¸HŸ\ãAý\Â`%P&lÿF…cL)\Õrk\×ÉƒDþdOL\í!°%,tH6»?±‘7\\\"\Åi\Åp]vj=7†\ÇÓ§¯”0Í„¿^\'ÙµA0£K\Ó?e™°\ë–\Ý2/[B\ß,Á\Ãwl¿6Ò½Ng\ê\×P\î;.­.Ý¯o¢\ä)³¯\ã\Æ\\/ù\æÆœÉ¶ 2\ÑN¶{nŽ¥Kiñú\Ñ)K\ÏÃ¢[±¶w­JBZuT,Ì•#£—ºMx`dW\Þd\Ûz²±%,œ-xd“\É\æ\é¶tHeU\ây\Ü+¹\Û$VµOB}Rª¤\ìu’o\r`\Ùn\ÜO¤„\Ås³\ÕD‹7ŽEK\äB\ãý¦\\z.%Ã Q	Úƒ\ë°\ì\07\î\'vôoo1X¼þ©M\Û\Î\ÈÆ°\êüú\ì$.%JŸ }®~\r7^\Ù3óv\ã,»Ul	‹\æ\Ûb²¥_¶\'r1\ÂZŽü	\ÏJy\\}®_»Ku7^„elð!Ž{ÿ\ÎV“{n–º.\êJ§ð¼—Y	”\r¸­Š\Ù3»5:S–°	ZXq,ý\Ñ-&›VH\Ú\î§\ë\Ù\Ô\Õ0\r\æq\Üq\ã;\×J½C0+g²«)’\íˆc\éo6Y¹ÀÆ¶];g<;,\×\Â!<aL¬<\â•+¥z\"d\çZ\É#‘=™Ðû‰-¡¿O°}¥ÀÀRwØ¼$%\Z?†§ <L‡\ìûo—<|‡`ö4\î”%k²‘V-Ì›n·\ßKOÙƒ¶WÚ²XJ\ép]®\â¸ñuŠ\ìkÉ²[eZYR2,l\ÛÀ0\ìw§•ð¸6FÝ²\ë	Z\ÞdO\æL¶!\àø‹\Ë\Ã‚’:7^|\Ç5*·.m\ã,²XÃ¯•<zgþ–]É‘l!`dTòÎ±1*c¶ÿs,q\ÃhK_ÿ½›y[µ3ü\éškß`Ò’<¿¿Ê¯Œc\Z]ÑªnAxa1<&%,\ëU´\Þ\éš~)K\Þn\\˜°$\Ï\ï«ò\ßoW™´K%³ùyµ\Îwqµt_\\2p#,š\Ð7E™\ÌÙƒ\"û\'oWøÑ›Uj“&F©F	!ê„§O\Õ%x;~J…“ðcJ–Î’¥¦$y“-„*Í¾x Êdw!Œ2\Â(¡}\Z5.\â\Åp?<x#\ëo_È\ï\Ü\ÉF\Å\ìÿ=På™½UªMd—•;O\ëµ\">Ç½	OÛª#\\eS2³;û\ïÀ-Â[’\Ù#\ãF3\Ù\"\âmRžxsð¨cJ)™\Ù\r\ÓK~\Ñt¤7>iI^|\ÇE¶©!;«œ	‡ð$e\Ô1\ç\á\é\åk‡l˜Zzý\×UF\Æ\ê–m–1Œ2D!;£\Ëw€;óN\Ëh{qQ	\ÚO\ßi\'[´’“aE‹\á)\Ú0©^°“öÓP\\‚ö\Âþ*\Ï\ê,;\çºGòZzå­¸D¹ô´-¼\È\íYw‚\æ¶l\ÝZ²á‚ \ÂKND7I™z/Ä[’—Þ«y’\Ý(¬øI\ÆF—~\ÛWª\Ó0³\ÊfÀø!¥Ë¶\àg\ï\Ôxzo…a/7\îGva1<o¢LBÉ€\Ù\ÓÒ±\î¢´—Vyzo…ÁQ—e‹2\è\Þ\é–\ã\n)\\-=#\å\Íñ[\ÒeÂœn˜‘ Y–\äg\ï\Öxúõ\nWF\rLÇ²…\Ç\ÒË‘Œ‹X^’¬–„Gü@eC2»;™…A¶-\á¥÷Ùƒ\ÙFÝ²£V\Ð\Ò\ÆB\×\ÒsPÞŠ•\r˜“`\ÏZ·8\í©_»\Üxœ\nZ\ÚVy-=!&Q\ÉZ\\—\î=‘g‚f\Ópãƒµ)²#U\Ðrô F-=ò&L*\Â\ã$mV\Îd;nü•ƒ\Õf²\Íú-\Î0´œ‰v$¸–ž…r¬\Ë$ò6\ä¼-[½4_ò\ÊÁ\ZO\î©p¥\æJÐ²® \éð\Ä\ëðƒ¤…\Í\è‚\îE—¼-”e¿z¨\ÆS{*\\®N‘ª‚V@¨l•Ž‰\áBJ\æNS´(\Ë~õP\'^«p±…lm-o‹\Ö\à\ácxÊ›0½=jv‘ ©˜­,\ÛM¶¶‚Vpm\ÃK’mbL\é9¿öN\Þ\ÚTT‚ö\êûŠ\ìKn\Ë%|+hà¾½$ÿ\î\Â\Ý\Í0¯Gh	·¤*ª\äºôª\'hm–vQE‡\'4,7ý\æIJlùC \é.|c¸%\Õ\Ãøy’-ñ°l¿\nZ\ÞË¬ú¼š„¿y’Ò•\ÛJ´#˜\Û\ã\Ý=o²7þ«j<¹§:e\ÙyU\ÐR´\èV	Ž\á)\Çi¿%C2gZûn\Õ\",Û–ð\Ëj<±»Â…a\Ñ »­‚Vd¡\ÊjR•\Â\ã5#¹\ï¶\æR2£[\Ð\Õ\âoŠ²\ì\×>TdŸw\Èn­ ]MDK0]vTõvw	jS’(÷l\Z0f\Éh^’Eö/\×xòµ\n\ç\\dI·%\é°<¨g\×Á®2\áeµq°\Öú\n\í\ÊÛš…\è#€\áQ\ÉÐ¨Djd;žW\Ôÿk|A°)Á¶TfdTòÊ¡\Z/\ì¯qqDxW\Ð>Gž˜ö:i{]¹Q	Ô¶¢\Ù\ÝõoÐªÜ…‡&\Ú¯\Ã/>²Xµ\Ð`R†#[ˆ)2\r¦¼­m\×\ß=!›\Ô\Æ%\ÕqÉ¤¥~jã’¡š\Í\Å›³ƒ¿?oqq\Ø\âüd\Ü21J\ål+h	¬:Î—ñ.t=˜Yu\Ãbn»bQ­ºUl	/ž\ä–>ØºªŒa4·ww³mõôem\ÜfxT2Xµ¹0ls¹b3iI&,\É\è¸d¸fs¹\"¹0lqvÈ¦6\æ¸¡^–$\Õnm)@†‰Y6¡þ`Ÿö«&;Üª\ÝÇ—ôNýYU\á\ê›RqžÄª§D „Á•šÁvñ\îñq¶¬,3kš 6nsi\Ä\æÜ\Åg—,.Wll©6Ž×‰­Œ)—<2&‘r\Ê\ìª#„P¯\Òõu¾¨%t½MIPO\È„0#—ûÂŽ©K\ÈÂŒ¹¼o\ê÷¨\Ô.™]qi\Ç\é\Ã\äB\Õ\â\ç\ïOð\Ë\ÇH¤-±¤rÑ“¶²n‡(õO½ VYv\n7S„ªõ\ß\Ö\ê5.’ºk\É\áÁ¾¤\Ùw˜\ã=]°lþ\Ôß…Pÿ|•¸µ}÷‰\Ç IÝ·&“2ƒQk¤\Ýh&˜%\é\í\"©a¹m¤R\Ç\Ü\äµZ­ø:¼`¢Y2Í™ú»Aø\êÊµW4_6›¶U7I=\ã¢¦0L@\Ö_<\ë&—õ‰¶ÿiûÿ\ãi†\ÆL\ê¾[eù|\ÂÍ‚\åó\à\Ô`û m\ãe¶r\Ój)¤\ÎÀMš*­x„\éð„úÒ²jGLk\Ã,\×>ÁF\r¦l\Â]\Ë@H¼É–®?\åq±Vq\â/F½n\íüˆ¦„k*G\Üóó\Â\ãŽY\Ç}›$\Ñ\'\Õú{Ý²f¸A¸0°\æ\Ïð3\è\ê\Ì\ã\"‹gDJªý\ê˜\ï{t\ã|}ý`õ\â\æcMw+Ï†\rK=¬:Ê¤|”\'\îw>Y\ê‹\ÙO÷\Â\ä´ô¶\ß\Úþ$n\á3ºa[ýù\ì;I©\'PYy&\Î@¢SôLK\æ\Â\Ý7·G»¶û‘KU\ÆUA\'°c\Üw˜8³gÚ±÷¶Cm„÷Í„¯®V{\Ä+\ï$¢	epñDŽ\ÓhŽ‡œÏ¢^\Øñ%uS¬U<ßu\ï*X³0ò¤\'>ª¾¤ýâŽ©Áb\Çé„ž\É°}\r|ùF\ïfž„\Ï\íûnW_\íIy\\\Ë,Cƒ%Ô—jœ&\æ\Âõ\Â}Pò\áÎ“pCÀ=·ÀÖ›#(Ï‚\è }\æ¾ýšd\á™<ú–LøúpóB\ï\æ ù\nŒ\Þ\épÿ¸Á¹—šg‚W_\Üù$œg\ì8óÀ–\Ã¯k\Þ\Ò\Ô*\Úw\\®[__‹º}\è§\ÜOR¼rC÷\É\"Nkð\\\ãt\0\Ö7vm……s|\Ú\ÔEK¸iÀ}\ë”{Oeb„ÀüÈŽ\Ú\'¨o\nViÜ¤\çEƒ•Mø\Æ&¸\Ó+·H\à[l\çõÀ#›`e™¸!mß¢\â´/b™4Ï¯|	¾v‡¢\æ–P¯-^³þn›r¾ó“$6n¿¸cj°¢–YA\Ç7®€\ï\Ü½3<[·I(Â…€\Íýð\Ø\æ–Wrd™”t¦%º@\ÏÔ¿\0þþ`…_e\ÔCB¿˜¼dÂŸºf\èj\í\Å\Ô\Èc†\Ð\çÛ¤÷\Ý\"\Ë\æ\Ãwÿ\ÖÝ¤\Ñ\ç!Á¯\írIwv¨GvŸù\rŒŒyL\ÎOò\Ät}BŒ\Ùu\'Át¸\Ïñ¥ó\àñ?m«ƒ¯n•H„ƒzBe\ç€*\Î<»kþ\r–”,0\í”\Ò\ÎbŽ¹¢OYv²„”\Ú}ª¾2aÁ\ËÀ¾g†|\Zu\ÈI\n3f¬„,`\Ì\ÈXÀ˜\ëo‚øCX¿<Ù€pP®ý­cðÃ½ð\Û3. Sˆ¡³#ˆÀJ\ì¸þö«Ð¯)›†‘D„;r\ì<ñ&\ìù\Æ&=\Z|\Ñ\Ýw‚}nÜ¿	þò.˜\ç·,Ž ©0<\n/†\çÞ…\îG–:%‘s\á©ZuFž\É*ßµ\r¶¬\nWT	#©\êù°\ÎÀÿ€½¿S§\ä§e@“NYAÔ±\æÀ}wÀŸm€\Ås5\ícHª„;R‡ý\Ç\á…÷\à\Ðg-Ë·k}™•\0[8¶®†¿¸V-JÏªÝ’	\áŽ\Õ\à\íc°û\ì;\æ±nw\äZ Z‡kú	\à†\ÙpÏ­ð•\ÛT&^Ž¼X/™\î\ÈP\rŽžWIÝ¾cpn\ØE~§¸ïœ—Ys{\à¦>µžÞ²ZUÎ¼ö ¥-¹\îˆeÃ¥Šró‡OÁ\Ñsð\Ù%u4\Þ/“wB–‚\Î0ÇºKjû\Ñò>XµX%d·Ý¨¶†\çñ=«Ž\äJ¸[¤TDŸ„\ÓW\àøE8y>„K#04\n£\îºÁ:g™e\0=\Ý0{:,˜­Æ¿©O\Ý\à¸aŽ\Ú:ö\ÎVR\á×¥\Éþk|¯KG\Éÿ`\Â\Ö .)\0\0\0\0IEND®B`‚',1,'admin','2015-01-16 15:33:02','admin','2015-05-06 15:33:35','','ç½‘æ˜“','http://note.youdao.com/',3,'','','username','1','[{\"attr\":\"tt\",\"value\":\"tt\"},{\"attr\":\"jj\",\"value\":\"dd\"}]',5,0,1,1,'org.maxkey.authz.formbased.endpoint.adapter.FormBasedNeteaseNoteYoudaoAdapter',NULL,NULL),('f1e33b71-f553-42ab-ae91-2fd913854cda','Token_Based_Simple','http://tokenbased.demo.connsec.com:8080/sampletoken.jsp','E-COMMERCE','1729a1ee16e532d61e097c01054dcfe7','Token_Based',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0D\0\0\0B\0\0\0\îJp¯\0\0\0	pHYs\0\0\Ä\0\0\Ä•+\0\0\0tIME\Þ,%6¤œ\ã\0\0\0tEXtAuthor\0©®\ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬\Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0À\æ‡\0\0\0tEXtSource\0õÿƒ\ë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨\î\Ò\'\0\0kIDATxœÕœy°%W}\ß?\çœ\î¾\ë»óöE³i´\Ìh—X……\0£If©@ $(Š\Ä	IX‚¸¨r¥‚+N\ÊØ‚\ÄE06c,,‰M\Â9‡hŒ4\Ìh4o™÷\Þ\Üwß»·owŸsò\Ç9Ý·\ï›E#UþIWõ\Üûúör\Î÷ü\Ö\ï\ï\×#tf¬µ %d\Ú†Šv»M»\ÝQµ\Â\è\èÕ¨þ\Ü2m‰\ã˜õõuúIJ%dtt”0‘B¡µF)€p€X¤kY^^¡×‹™¢R©\')O\íŠ\'Ÿ|‚Ng)%\ÖZ\0,€pŸ\"ÿ\ÛoÖ‚H…ƒcFk@ ¥;\ßZ\ë`pc!ü=EqÌºS‹k”R!þ!ùw!XK­Reff–\Ù\ÙY¦g&	¤`eu…\å\ÕeFšM&\Æ&QJaŒq÷2\ÚX\0m‡/P­D\Ì\ÌNr\è\àa¾ø¥?å¯¿qóGŽ°\Þ\íb´AŠ\rqr‰±\Ö\"…@*‰~¢T ~²ù$Ÿ‚(\nBúIAH\éž#¥$\ÃbòR\Ê!@„T*¤”þP©\Ôh4jŒŒŒ°m\ÛV^öò—ðšW¿š X\\˜§Q©2:6€1\ÑOR+€g\Ï#¥`çŽ­|\é\Ï\î\äSŸú}<D5Ù‚5!Æ˜Á¬\Å@¢ôƒ\Å‰ŽNòIY\ì\Ð\ß\ä«\ï¿X›nn\0\Þ\à’a€¥t¿K©ˆ¢\nR\n\æfgi5©×«lÛ¶•½7\ßÌ…{Î¡½\Ö&K3Z­A ­\íÑ£+\0\ÌMOò;Ÿø$Ÿý\ì\çh6š(bŒ\ÄµG^ ¥\Äƒô\"«Ki¼œ\n²\é·\\ÝŠS7K™µ\Ä\â®\ZQÀvüõ›7k\Ý\ÕR¬…TgXkQRF»wŸ\Ëö\í[imiq\ËÞ·ñŠ—½˜P¯×™˜˜@¬v\Ö\ì±v›3·n\ãsòEþ\Í>\È\Ô\Ô4X‰1„\Z\è§_]·r 7Í¥˜t‚üÍ°@z\Ë°€\äù]m€Œ1£\éõº\Ì\ÎMs\å•W2º¥Å¿ú\ïezj’……fff‡—l£\Ñ`a~‰×¿þF:R„*t7€dH¬`H+\Å\äýš\Úa‰0b“ôœh\Ûl‘K2\"­$„†\Ü} †µ\ÒswÈŸ\ï€\ÑT+½.­‘®¹úµ¼ø\â\Ý\Üþ\îw³ººŠR\n™¦	[j\r¾ð…/°´´D%ªz«k	\Ã\nR9\Ýt»-S¬½5`\r\Ö\ï`\Â\"¬Á\n{Š\ÝýN~OQ\Ú+L	\Z÷l‹q{>\É2¢\äa¬ð#¡B!…D¢\ÈRM%¬°¸¸\Ä/ñKž9t˜\'žx‚\Ñ\ÑQº\Ý.²\ÖX^ms\×\×\ï¦^m`ŒEH‰Pm5\ÊJ«H¤‘(\Â\n”‘H\Ö\ZŒ5XcJúmK\"a7}ž@@\ì@P|e<Xi\ÑRc°hi\Ð\Ò`0þ˜\ÙtW¯bb\0¢°a\r‹´N\â”U4+\r\ß÷$+«žxòWA@T©#\ÇFG\Ù÷\èc,,,R­V.Rx£h…saV ðŸ\Ö\Û+°Ö_Þ±\Þ\é`ý¢®q\ß\Ýò\ÂC\çKŽ»\ÏÀ\ïHœ’\nRBQ’[\á,ˆpc\Ö\rFXŠ¹aA©~?aiq‰……%º½„ 	\Â@qð™g0Æ$	B„`¬uA¹—ô\Ö\Ï\ÂI\Äñ^\Ã\Ù\Z‹± …\ÄZ	Â€)»K‰AX‹¢d!„÷6‰\Ä\Z\0\ã>‹“¼ý*\ÇÀs‹jXc@\ÊBŠ…Ed:??\Ï\ê\ê*\Ë\ËG%°\Ö\ÒY[\'Œ1(Ü¦»¹_µÒ±ü¸›Y\é˜H‹\0„w\ÏBH\0‚,MI²”4\Í0&\Ã\Ú\Äùƒ2 ¤tq„ \"\"\Â((‚0pd\á\'¿ya\n\Ù\Ê\ï]Š£„€(\é¬w0Æ°¶¶\Æøø8‚v{µ]¥”npöTZ¿Y\"<D>FI’„ Š0\\g}Ž.­R©D4\ZM¶ž1\Ç\Ô\ÔA ¨WR\r$+\Ë4\Æh²L“¦«\í\ÇV±´´D·\×C`Ñ™¦92\â¢Rz7?Oµ)¥‹&Y–¡µ¦\Û\í!¥$€A°c½ž\å9„…†üJŽ\î`Êº\îü½”)%\ë\ë\ëh\ÝgzzŠ\ë¯W¼ò\n\Î9û\Æ\'&˜žž¢V«R\r\\®#%9£!\ËR6\â>\íµ‹,¯¬ððÃ²ÿW¿\äñÇŸ\à\é§\íCtIYg\nu±¶§-I‘ñª3˜÷\0 `x‚\Þ \n°^M¬\0\é\Ôÿ¤±„1\îfZ\ëb¥———yÑ‹.\ão3W\\ñLLN>¶Ábt\ÌúZ—XY\ä*yŽ„Š\ÑJƒñ\Ñ&»v\ÌpýuWe{o~þ\àiµ\Æü³\Ë5oþž\ï·\î~wR²\ÊVXg™ó›{€\ÄIWÀ_K‘	¯­µy×»\Þ\É\í·¿›\ÑÑˆ^¼Á\ÂÂ³T¢\n•j€TŠ@)WY¬Rš”T\ã2aQJ\"¥©NQJQªDUHuY[6K\ÈÉ¶\\Š¼\ÊZ(¤\ÈCF‰ñ\"]|œh\ÔDª!,&KÉ²k2þ\í~›·¾\åMt:Žt5Q†!ZC\ÜK\n\ÂÐlõ ku\é·ÀSü-…ô.Z ¤ ‘\Ïø\à\Ô/«F™&(£\âl§*\â\Å5Ÿªõñ…¶0°þŸˆ_­\ã=‚õ{\ßú®½\æ*\æ\ç`Œ¡R«’¦ÂŸ«	…4\n\Æ\ZK ¥\çFü3¥ôÙª{F–e\ÞX›8·®.\×Ÿ –%¤H±œ¥«¬>\âO”Rxt“µˆBe†®E‰Å&)µZ…õõ_r·\ßñNöÿj?õz©\Ú\Z\Â0$MS‚  C”2di†RŠZE%}V-$\Ö¬\î\Ó{;k,\Z–Ž\ÌRJ¢3[\ä)/d\ËÁ\0Z\Õü \Ï[Š„\"iÛŒpNøDQ\Ú\íU\îx\Ïm\ìØ¹G~˜¨¢PHD&°VcmT\\kŒ¥\ÙlE™\Öô66\n\Z/FGa\Òh4Ã(R(9<\åôIc2®¤Ä‘P…\rñAPq\Æ\é!l­E\Û¡Û·o\ãò\Ë_\Æøø(»v\í\à\éûiµZ˜B|zc©V\êüŸŸ>Ê·¿óö?µŸn¯K§\Ó!MS\Â\ÐQ©DAH5Šh6l¥\Ùh061Á\Ü\Ü»\Î\ÜÁò\ê±\"\Þ90\'ahü€5Æ§,Nð1€\ÇùóS@H¬5A@w\Ùu\ÞfggÁ\Âo\\ñ\n:\ëmŽ\Ð\Ú2VdlF\Ã\Ôd“û\îûŸù\ÌgI\Ó\Ôu””\ÄqJ\Ò\×@\\rÏºˆòôBz#X¯Õ¨\Õ\ê£O\èbO\n\Ä&Nx`C\È%D\Ü\Öi‰‰;G)‰T\Ð\ï\Ç\Ì\Í\Í\ÐjTHÒŒ(\n¹þú\×ó\àƒ\ß\ç\É_þŠfsk Riðëƒ‡¸ë®»i\Ô[\Ôu\â$v¼˜\ÑD\ÕZ1‘œ&%JIz½^\É` ž®\ÛõF¢ô½\0rb÷¹\È\ãx\Î{8w¨‰\Õ¥W_}-3³süøB¯\Ûg|,dqa‰¸\×GIw£‡ŒD!y\Â\åT›\"Zk´\Ö\Å9RJ´Ö„a\ÆO¼4\äÓ•’ó°\ÒEZ…Q¢{šöC\0Æ™w¬÷\ã\Æhÿ4\n–\\\Ë%_\Ê\Ö3\Îà¡‡~\Ìü‘£(ô²>µZƒ^\ÖC.r†Z•M:Fg,JJÏ“Z0%Vk„ðõ”\çaû†À\ä6ƒ\Ì\Ú<ƒ¤\Óqa…˜\"+\Ð:Z)]Xœ¦=&&&ù\ç7\ÞÀ\ÕW¿–\í;¶177\Ç\ê\Ê\n\Æf>\nu j9š@x\"Îš\Â\ë•óž\",©‡xa®×’_\ï\î/ó	l\Ö\×S\ßÄ»O¯\ÇA°¸¸D§›l¢\0J…$Icg½“›nº\Ï}þx\ã›\Þ@÷X>ºˆ\Ñ)\Öh¢0pô5H¡˜9+ygKùˆ}>ôñ“±9‰\ä\é§\ád\ç4ƒœ¼•¦	Qñ\ì³GxöÈ³ù\Ø\Ý&ri	@J´vœ\ë\î=gñ\ç_ù\Üu÷¼\å\Ío$\é÷h·—\éõ\ÖAh‚PFn,–€‚a3ðÁ\Ø4w@ª\Õ\êp\êT7a ªJ)\Èaú\ÑC\äC’¤ù”P*(&§”\ç<´\æò\Ë_\Âÿ\ç\çù‡‡þŽ\ßý\ÄÇ¸\è‚óX9º\È\Ê\ÑE\Â\0\ÂÀ]›oùø^hdz:[H\î\æN÷aù¹rQ†\Üù•;\É2R>\ç(‘\ÎB*-J9#¦µ¡Ÿô9s\çv>ø\ï>À=ó\×\Üù\å?\ãõ\×]\Ëz§Í³\Ï\æ\Ø\êja“\Ìf\";÷œ\Ä\å¹öá­°I¥8¤\0$ÿ|>6\ÄZKÜÉ²Œf³\Éÿúñù‹¯} H\Ó\ÄÓ‡ù\ÃˆœwŸRZ¢0 Mû\ÄqLµ\ZqÃÿŒ¯\Üù%\î¿ÿ\Û|\ìw>\ÂÎ;Y\\\\\"ŽûDQ4”w\ä*\é\î+Ù©ö\ãuó\Ì\ä\æŒ\ïtxÃ²\ÊDQ\ä3TE³9\Âø\ÝO²\ïgOP«\ÕIS=Äš\Û|!O°ja¨HÓ„4M\É2Í…\Ï\Ç?ñ\î¿ÿ^>õ©\ßct\ËKKXkPRcp\ã\Èolü~ú2¼{£ªTP :\îK\äÙ¯§„ujµÒ¤\Ó\és\Ë\Û\ïà±Ÿ\í§Z­¢µ¦\'E\è=X©A¬†\n¥(A ‘\Ò`LJšÆŒŽVø×¿ý~øƒoó±~LJ\Ü\Ý@W»ƒµºF¾\ç\ÇN¾¹R§@k‹\ÑÎ£I…\Î[k}\Õ\ë\Ô[‡£iš\Ñh49tðoy\Ë-ü\å_\ÝCTk:3%“2\Ës\îÅ¥y•Px\î#@g)i\Úgrr‚}\ì\Ã|õ+_fjjŠvûR	0ƒ\nŸõ;{œ=\ÉC\ë\Í+È©O\Ç\ë¤ÁIHÁFŸ†\Î806©\ZŽùÊ²Œ±±Q:5\Þóž÷s\Ûm\ï\ã\ç?’0\n×˜¢\Æ°\Öœ¦ŠbBø\ïÎ‹EA€\Ö½nŸ+®|s\Ï_q\ÑE\Ðn¯„¡S~ŸRv=°\'có\âYš¦¤i\n¸jdA×. 9(\'\ÚUh¨\×\ëŒn\Ù\Â]w}“nxý\è\'yô±ŸœG\îeœ]‘\0¹\Ä\äßñd•…0\n‰{	;vœÁŸù‹l\Ý:G÷Ü¹\'u\Ã\ã;\É`…“¬<W\Ê\r=Y–}a\ß0\Å8Ä…`jf–$\Ó|ú3ÿ×¼ö:nyû»¹\ï\Û\ß#É¬£½TeiZ$u\ÖZ²,¤“\"§B®\Ç#ŽSv\ìœ\ã?}\ê÷\èv»X \ßï»‘!ýsŒ\Ø?+\Ë2\â8öQ ­`Àjsb’å¹¶rž‘ÿ›$)\ÕZƒ;\Îdltœ{\ïû.\ï¸õnº\é\Í|þOþ”ù…EÏˆE¾H\å«q·@\Çe®– pÁ\ßu\×]Ã«_ý*\Ú\í6­Vë”“\ÏÁ*{¦|\Üý~¿ˆq¤\ËVƒ¢”ùB¢ÀrhOf`\Ò$Z[zqBœ¤LNM36>ÁOþ\é§|\èCÿžW]ùj>ü\áð\è£û\Ã|nù\ç\à9Ö“\ác2„€w¾ódiZT\àJgv\Ôa9„\åj	\Ð\ëu\Ñ:\ÃZ\ãlˆGYŠžß–/^9cv­xw \"À\èõú4›#LMM\Ç	ÿýó_\à\r7½‰\Ûn{/?üÁEx›fÊ«*üÀ­ó0À¥—]\Ê\ì\Üý~¿¦7¦EP\èž\\—‚k÷{&s\Ë\ì\Øp…xiô0@…\"”\ç\ï\Ý\Ãtª]Ü¢I_#ƒ*“3[1¢\Â=÷~Ÿ\ßð6n»ý_ò\Ô\Ó1€TŽ\êõú¾LªP*D\Él\Û>Ë®]\ÛY__G\Ê\0%#¿úœ0\È\ÜJ–eôz=\â8&#k…\"?Óštn\ÉsŸ_Îƒno£@û<\Ã\Ôd™\ëhmE\Ë×¾ö— \r7\ßüV\Î\Ùu&\çœ}–÷^¹‘u\\‰µP\rc\Îöxž\×-H\Îþ•Uº\\\ÓHH¿{F¤1‚<MwºRPNŽŽ?^Î\ÔÂ‰\nG9pZk’~Ÿ-[¶ð³}ûX\\X\äû\à›ß¼‡••c>40¥I»\ë\êõf\Ñ\Ëb}qÚµkrI‹qw»½\Âõz£\êlˆC\ì¹Á\È]–«±P/ƒ”‡\êeI*7lñ \å\î?\Ó\Z©“““,//s÷\ÝwóðÃEñ2¨Y–zû!pé€¥¼H\06“¹—\É\r²t\â”\×VK\ìû)¶ò9Žôq.2I’\â¸1¹$+s>!g¯\\WO^\ÍY8!0y\×r½^G)Å~ô#|ð‡dYF¬Y!X__\ÇX;XTN.e@ò~\ÛN§Sô´\È|Ï‹Bô¹Œ1†J\ÅU\ä’$q“”\îAQ™py5rý5F»\Ì\Õw¹>RK «««\ìÞ½›¹¹3R•¼f³É¾}ûø\Þ÷ð5_HÒŒc\Ç\Ú?X‚\\}¬—˜SIH\\”Á\Ë|Qn¨²Lû	\Z´\Ö,..ú* ðÞ«\â=ƒþ‚ \ïMÏ‹Ü€$ý”$Ù \ß\ïs\é¥sóÞ½¤IBµR!\Í\ÒbÀ­V‹\Çœ\Ù\ÙY.»\ì\ì\ßÏ¡C=\ãg\Í}Vú\ÎQ\Ð($ \×sF\ÕW\îòš\Ì\éIHy3Fý~\Ì^À{î¸ƒÃ‡3??\Ï\Ú\Ú\Z\íc6º]\Ö\××‰{1kkk¤iJ’&X«\É\Ò„bfvŠ[·òÒ—¿œ×½\î\Z¬\È\ènt\Ã\nÖ€ôa«\Õ\â‘G\á\ÒK/\á±}?\ç\Ù#Ï²un½8A*WK&—\n\ë\Û9†ò›Aµ@Aß“\\\ÆØÛ­\Õj¸\Æ\ÛÓ\\W±±$¨H\Ò[]gnf‚·\ßò†\ã\Î\×\ÚU÷’$ac£K–¥$I‚ÖšÔ‡\ëaR­Ö±V³¶¶Fk\ê\Õ\Z½^B\n!Iše¤YŸckm\îü\êWJ\Ò\Ëz¡±\"u¶	\ë:Ÿ£Š¾\Å\\\\“q¯\×\Å	† È­oY×Ÿk¬ñ®•üUA–¥h‘¦`ÀZ´\ÄJA«U\Ç‹\ÖnE\ÒÌ©Z’$$I~¿îµ,\ËB‰Àb¬‹\\£z½€;¶ñ\à\ßþ=ûý‡˜žÜ†\Î2 (z_‡y(\çŠË‘«”\Îð\çI¡³c\Öu!\'P§©\æ%\ÄRÓ¬p%‡0”IµO\éM\áuŒ÷,¹!\Í]o^¢9‰±±!B\â\Ø\Ð\Z§·‘ñ_ÿ\à‰\Â:&X£À‚®`/ŒðRaÈ›\\\Ê3\ËI©<Û®TB”ò2\0#?ñÔ›«\É\à\'\ç\æl^\Þ=[ô€(\nK\0\ä\Ùl\Î,z(ž\ÉcŒ<¿R¾iFi\ç\"\'Æ§X˜?\Ê\ïÿ\ç?à©§ž¦\Ñh $ˆœñB\Ù\n\×m”÷\Ä\çÒ’\ÛcI\n\ÖvŸ»›n7&0†buA\Ôs\É\Æ\0m\í¹HDIw4\É\\¿¼ðL®WM\n·j\Æ\Z¨0(\å\\dDN\\¤Y\ê\Å;`t´…Ö‚Ÿ\í{‚Oÿ\ág8ü\Ì<###¬v–\èõºTü5®\ï\Þ¯²c‡4(=\\2gHÒ˜\Û\ß}—\\z	‹\Î\Ë\äMqÅ ¤%\ïaÏ¥\'wUC’‚%BŒv-Vi–!¥¤Z«l>°h=œXc|R:P\Ù 1FEÕª3ôYfx\ä‘Gù\îw\ï\ç\Û\ßú.XI*Ž./rþe\ç26>F¨\ÕJÍ¿\Ò\æž)„[1B¸vsX«9÷\Üó¹\ãŽ;\ÐÚµjJ	ª\Õ\Z•J¥H¹s3\à6Srm!\Ñ²L\Ól6ù\éO\æ­o½•ó\Ï\ÛÍ¥—^Â¶m[™œœ \Ñh\Òjµ¨\Õ*TkÁq÷É´ñ|†¡ß\é÷æ™Ÿ_\ä±G\çþû\à\á‡ÁK£QC\É\è\èŸÿŸ\áª\×ý¦\ëQ\Ã)l\Î\ëŸLñ7ÿžYXko$	QT!pe€\\B”\×e6!72\Èdƒ $Ib¤Tdi\Æ<È·\îûJ¹\Ç\Ç\'hŽ4¥\ÑhP­V©V«4\Z\r*\Õ*Q%ð‰UF–e,--\Ñn¯±°0\ÏÑ£\Ët\Ö¬Œ¡”$\Ë\çùôþn¸þ*{¶O7\écl\Ì$\î…\á\Þ\ÂR¶BU­Q\r\Ø\r¬\ÈÀ¸f>£0\Ö2\Þ<\é©cAk¶li\Òjmadd„ù#‹\Ôk®!¶\ß\ï£TXL~\à¬÷dk4\Ê#S\Ã\Øä´·«c,ë½”öú\n-z½õ\ß]õ\ï¥\\¿›£kŒŽ\0–4\Ë\ÐŽµW¹\ì%/\æªk¯¦$Ü»\ï(_h… ubd=AZM%ˆt†š\Ìx\í÷qfý/°\"\"Ö‘IŸ¤?NÁY\æ#¼\æ¥oD›˜Tg\ÎË´Z£4\Zufff8ø\ëgü›\nf\È•cÿœv#ä’”¥º°9\Â\È5\éú\Ô \âDt@™.p\î8\ïÊ«q®öóþ÷¿Ÿ0I\Òž[\çž_la=l\ÒÀD½ˆ«÷\ëülùZ¦§¢fc\Ì9T[h&+\ë\ã(:»\"¥#½\ã8fd¤\É\ì\ì»v\í¤\Û]\'ŠB‚@Q©DC’1ø\îWxH­­M\Ö:‘\Öb)ò9µ\È÷\Í\Çu\æ\Âo\ï\"`\îe‚‹/¾˜k¯½†$Ó$¼h²\Â\î]šX´©k\Ëø:4bÖ€\ÑT„\àÀ\Æ6÷_ŽÁs˜,A%\Ì\á³y\É\ÅW`m†0®R »½\r‚0`||Œ;w\Òh\Ö\Ù\èvPJù„\ÇPÜ†xÜ¢”~/z\Çs‰8\Å~²\ß\ÏrQ.\Þ\Ã%I\Ê-·\ìejb„8Ž‘T˜u^~\æ:=ó4„\Íö*õx$\ÐDl0)Ž ³˜ù#/Á\Æ¡\Â.:ˆ\é­U8{üjš•Q°®Kºh˜‘Rp\Æ\Ö9¦¦&¸úš«ˆ\ãÚ¤¤Y\âuZŒ\Ö@mþ\ßlyÚ°9u\ÈBkƒ‚~¿Ï®]»ø­\ßz#\íµ¾«„\n¡c^³mŠ]­qq„_ð^sù2†5F\ä^¹\ç1\ÎoFt\\Fû\ÈmŒ\Ê\Ù(ýþV.8÷U*\Äd†,M\Æ\"\ÃP\Ñ\ëm077\Çô\ÌW¾ò\n.¼ðŽ[)J\Îó˜¡\çÀ¼½\ê€M\ÜoÐ¸\ãŠYZk²,e\ïÞ½l\ß6‰±`´¡w\èš[Ãˆ\ß<sñ¢dl[\Æ\ì¶o°­zˆF\Öb\Ï6\ÉE3ÿÀ\ÆJF/|%•z„^©T/frz†¸»WW\×Ö¨\Õj\Èf³Á\Ú\Ú\ZZ§\ì\Ùs\Üz\ë\Û9\çœsX]Yvz\å_ÿposË¢52‹\àÄ»|\Î\ÝÆ¹dS99«\íe—½ˆ›o\ÞKg=õÄ’$Š*h%\å\Ús\ë\ì¬\ÍòwO\Ö\é¬=\Í\åS‡i³<üx—\ÙñŸP™Y\ç{‡°\Ô=„Ym0w\Æ+°\Ò 0$ýc,­‘:A¥R!Š\"\Ú\í5¦§§\Ùs\Þn„|\èC\ä‡?ü{\îÿÞƒ,//±H\êCü²ÈŸH\r6ú;\éK\Æ:W\Ë\Ím]££[X\ï¬qû\ïbûöiVVº„a^\ÞTh±–¤\ìÙ¢x\é\Î\r\î\Ý\Ð\Ý\Í\Ù2g\íHXlj½”g‡<õd\Ì\êÁ—2ÙŸå‚³_Š\É2*aÀü\â\"­‘&Q\"Rc¬Ö†…\ÅejÕˆF³\É\Â\Â\"û÷\ïgc£\ËZ{~\Í3\Ï<\ã\Þ{\ëvû2¡\ìø\Î\0ù\n—ý\\\ró\ã\å\Ïüš\\Š’$\á\Ö[o\å\ãÿ\Î\à\Ø7k,‹–\Ì\n”tú†»ï»Ÿlc7\ßt#&\âŸ~ú¿¹x\Ïvf¶\ï\æ÷>À\ê\á\'¹þšW±gÏ…\è~Ÿ¤\ç\æ311\î\èLm\ÜY`´e~aLNN\Ò\ívYX\\deµC·\Û+€p­R~bÖ½2°3Ž_H’„8Ž}‹\ã[»Ý®\ç<’¡¿1\Äq<ô»{)°K³\Ù\ä¶\Û\Þ\Åû\Þ÷^\â8-x!=X¼ÆŽ%‹\ÕV(ªµ:\r2@kH\â.tüK£Q#\í÷iw\ÚT¤\àŒ3f‹„öÿ°>4Ï®ºL\0\0\0\0IEND®B`‚',1,'admin','2014-12-14 16:14:35','admin','2015-05-06 15:31:11','','For Test','For Test',0,NULL,NULL,NULL,'0',NULL,2,0,1,1,'org.maxkey.authz.token.endpoint.adapter.TokenBasedSimpleAdapter',NULL,NULL),('fe86db85-5475-4494-b5aa-dbd3b886ff64','è…¾è®¯ä¼ä¸šé‚®ç®±','https://exmail.qq.com/cgi-bin/login','OA','815fe27ae9ab72a746ddc1b2b33298241a5a4e5fafa030e2336ec5109626b452','Extend_API',_binary '‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0C\0\0\07\0\0\0Wy÷¤\0\0\0	pHYs\0\0\Ä\0\0\Ä•+\0\0\0tIME\ß+2x\\ö\0\0\0tEXtAuthor\0©®\ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬\Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0À\æ‡\0\0\0tEXtSource\0õÿƒ\ë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨\î\Ò\'\0\0\0IDATh\í›kŒ%\ÇU\ÇU\Ý}û>ffgv\×û°½ñjmÅ²Á\Ø$V\Ç6\ÈQ@‚`aH „ø\Ä7„„ø\àQ¤ R„	‘€ >ldL?\Øu\Ök¯÷\åñ¾w=;³;\Ç}twU>T÷}Mß™õ¬³kE>R\ï\Ü{»ºªÎ¿\ÎùŸS§k•sNøX\0Ð·{%	G\Ýœ@¿\Ù(¥P[H\0\è-vp³ö+\"ˆ€R~J­Ÿˆ*s\'‚.iüaˆ>@\×ˆ¶”\é¸\Î2$o´\Ø\Êxñ\Ì§Zt\Ë\î\Ûø\ä\ÞI\Æ\âˆVjF¢[&\Ö4\Û>Q‡vVqù\nmö´¿Š\ÆÁô²eÁ„Î±‘¡ý\Z\ëˆ\ãç„™\Ùe\æ®,ð‰\íUžú‰\Ýl«…ˆÈ€¥n²\Ô\Îø\æ\áþ\æ‡W9·\"TÙ¿]ñ\ÄOMñÐ½\ÐC2‹7;¼b¥“\ß&\Ò0×¬1½x\ÉZ\È\ÞF˜+;\ÚD\ÛÇ—ÿ·¨qS^UqA)\Û)üx\Åóa .]R>²\Â\é\éYöŒ9–;–/}z/\ÕAõK\Ý\ä‡¯ð\ç/^`¦\é¨`@kda™F#\æ3\à³÷\ßEjœuMˆ@a\Ñl\'\ì\n~÷q&*zSwL­ðú\\\ÊÁEÈ‚*.K6¼eƒ·Z­\ç\Î]\ã\Ð§¹6wúx\Ä\ì›ø£/\ì\ç7>sWþL\Î%e¾zq™S%\Âbœ`Œ\Ãlgy¥\ÉÁWó\Â\Ûi&QŠŽR\ëÈ¬Œ¼+´“”°Ráª«òÏ§\Û,´\rZ)\Ì\ÐZ˜œ\è\0¾{¾\Ã\Ës–¶ª$	\écd\Ö\Ï#µ‚q`Q;u…—_>\Ê\ÌÅ«Äµ:Ö¶W\áû§\Ö\é]\n\Æb\ÛBT\'T N\ÂG·M°œ\ZŽ¿þ/~…vF\\\Ñ$™!sŽ\Ì9ŒH÷²\à/2\'dÆ Âˆ³\í€\ï]J™m\ZB­°¹ò‰B¥H­\ã[\'W9|\Ýa*u\Ò,\Ã8‹\í\ë·#B\æIfA+¬‚·Žž\çõ\ï¿\Åüµ\ëÄF7ŒJPQ\ÅfÖ³&0‚3TnfôsµH\Â\ä6šó‹œ8t\nI2ý\Ô}\Ü1^£\Ó\É@+Tnr]û\Ëû1\í:8¶¦Ð³Ÿß«\ØS°q ˜YIø\ÏómŽ¯ijuH¬s€\êö+\Îõ\ÉÇ°\Ö\×b:©\á\Ô\Ñs9tŠµ\Õc“Û¨TcLf<i!Rªg‘*·‰\ró\Ïº7c \"»v\â®\Ìr\ìSdiÆ£\Þ\Ï\Ôxqg]È¾Þº€`\Ê:‚0\â\È2ˆd|qŸf2V\\^Iù)o6kLV!\í$¸. ‚Ó›à£‹@…¬5\Þ=vŽ·^?A§\Õfb\ç¢j\r“¥\è>=œµ]Wô\Ý\nU†ø\ÑW¡X\ë­H÷\î¡:3\Ëô¡“´Z)?þc1JÀ9Wv@|\æ`lJX‰9´˜ t\Ê\Óû*üý\Ñ\ë\\¬\îbG5¡•˜\Þ\ÊuŸ[¿`~±„´qüð4GžÀ:\Çø\ÎQ„\ÉRÿ|Ÿâ‚”ö7\Ò2FJA½\n:»w\Í\Í3óö{¼\ÔJx\ìó°}¢3\Þw‡Qf-( K”R¼:—ñƒ\Ë-2»«&_9•+\ë&ß\Z\Îa3Ë‘×Ž1}d\Z¥\ÉItz\×,\ÜVúg1ð\Ã\0£\ë¨2øY*1\æŽˆ\æ§/ðZ–ðð\Ïü4{÷l\ÇuDû{ ô’¯¤OTtX\ê8:k“™G/_PJ\rpDq„•ˆ\æj‡£¯\åÜ‰sˆÀøöI\Â\Ø[¨ˆC\å\Ä\'¹ò\Þý‘õ!ý\Æ-C©. *ÿ.Î¡\ë5Â»vQ¹2\Çò\ÙN)…|\î!ö\îÛ\Ê2Œu©L¿b…\é+¥PiJ`2¬ln	ª\ÏMû\ÝD+E Q¥\Æò\Â2\ï¾q’K\'\Ï*Ec\Çvt%\î*\îÁú\ÖQ!(\Öyñ£\é³+\n;6EzwÚµ.™§\åN\Ñ|¬\Â\î½;µÁei¾ÉõJ:¿zbsw(¸¥´\â3 Z£u•¹\ÙUN>\Ãùc—ˆ«\ã4¶M C…X2E\áû\ÍÁ\í\á\ãÝ´,eþ\àœÑ·sPÁ\Ò\Ð	ºÁö˜\íA\ìý‹\\y­\Ã\ØÐ˜\ZG´\Æ:A+oº…’Œ ²~®ñ 9´P\Ö`V\ç¹rø8K\'Î³w[¨V!ª*:Ö’Z†\\qHr\ã¦\ÜdH­0•1ž¾7\æ¹/\ìg\×X\ìu‚µŽ@+²0\ä\ÛRþw6Á…0)YA|Ý¹•€10w!ÀAP!3OEüö\Ã\Û\Ùó³O’¤EZ)\æ\×¾òwyþ\Ô\Z:kb7\Úó¸µ%0|]BAó\à\rî¿£1²\í³û5õ\0¾;“ÑŠj„i‹\ÔySõ46ýw\×õ\é!‰ªH–ñ\ä®\n¿¹?\æÀD\äo6*\Ý\ç&>¹gœ\ÞKQ¦\Õ\ÕxXo¡\àÿ#´\äd\Zj°NH¬#\ÌM_¡\Ò\ì¬jž\ÞW%\Äñ_W\r\×*c\ÐY\ËS|\Î1Àfùw%>u\î\Ä\r\"›ñÔžg\î©q`<@\ë<¤q¤‰•N\íÊ¶€*bJ­ß‰l\ÙM\n@œx—‰\Ñ%»Wã„UÍ¯hP	Zü\Û\å6W\Ã²åŒ¯`\rY\â\Ó\ï,Œ\Çò\Ô.Å³\êÜ•§\íJ„j\ØS&PB 6›Î¹Ò•÷S–\îõ¡‘Ï¼7Èˆ&\ÅF¬(ž\Ù\ß`,jñõ÷Z,\ê\n\Øl0-\ÎC­(P: bR¾xgÌ³\ZL\Å\Z+B \Ãq±(\\®\äe‹1FqK\n\ÂE™/\Òðów\×ø\ã\ÇØ¥Sl\Ôð™h¾7p‚b0p_•ßº·\ÎT¬ó~6©\Ýd}ð\æ,c)\Å»«h„¿;pBM´—±ÎƒÕ©N0\åZü\ácü\â¾\ZcQ¾Á’u\Ñ\'>wW¹\Ë\Ä\ß÷m†¿õ`¸¼ ü\äž\Z\Z\Å?œK9”U‘´CVipO”ò;ûªü\ê=u¢@õ\ê¥®zQ\ë\Ëª\îU\Þv8ÿ(¸´Œ^ü ·\Ò6–f×‹\Îk$\Î	\ï©Òˆ_=±\Æ[\Ç^\Õ\æ÷\ï©ñ¥ý>Tw9bS)r\í\ã…à¤¯&\â< \å`UÞ–¤{\ïƒ;j‘§8Gv\Äü\ÅÃš¯\\\å\é»kü\Ü]µnmôÆ€\è\éwƒ\Ñn\"\â\é\Ñe1\Ì-w“þ	ª\Þ;ñ\åOO‘ó\ä\ìMw`2cI3K%\nº\éû-w“u\Ý\äœPU·Û›Â‰CoZ\Ã95²[ko?\àIµØº‡[xÿX¬lQ\Z•G\Ðw\Ö\Ïü¶pÆºn¸9\×ð;\áÀ—n€3ºW\Î\Ý>\Î(²\Í\Õ\Är|n\rë„Ÿ\Ü=Vú\Úo3)ªÛ¢ðf¶EûÚ† jÈªµ¾}Ghg–|ó\n¿ð­“ü\Ò?Mó\Í7¯\ÐJ\í–\Ï9\é•87»†ÀRJ\Ý02\ëC\ç·ß¹Æ—_™e\Ù,gŠ¯¼>Ç¿¾s\r­™½qHŠ¤)3q\äŽþ\Ëo\âüF.³½\\#¸Õ–QXo(^<½\ÄWp‘÷\ÛPµ-ª¶\ÅLþò•K<?½\è³\Ï<”¡ð¯\'\ÚI†r†¸c€Gò{8Y\çI7Fÿ\Öûšœ{n±\Ísÿsž£‹–\Ø4Iò÷±q¶\Æ;K†\ç^ºÀôµZ«\î³‰RŠNji\'\×\ÝÂ—]¾¿ò·FQ!\ï½\ØXŠU^j¥ü\Ùg9øþ*8Œ\ë\í$Œ(gy\ãýUþôù3,¶|q\×m†°¸Ú¡\Ù18gG\Ñ_\ëpCÇ’Dd\ë`ô£\ë\ÄqF]‰q `¹cùúÁ¾sj!JÌ€¢N%–”€N/ñW¯]f¡™\ÐI\rÆºÒ«“\Z\ÎÎ­pu¹…rf\Ý\\oT¶\\E)p†¥¶?\Å\Ó_yZ7Hn\î\ÏO\Ïóµƒ³4­FcüjÁ€uù2´¬\æoß˜cÿd\Ì\ï=z\'AeôT\Ã@³¸’°´š ÖŽ,ùsW\â]¢\ßÞ”\Zñ®u3|\ÆH\Ò\ä¿O\'ü\É÷\Î0VÑŒf¡9^:{¹D¡ÅXò÷]\ÝIi‚F\ÐJ˜O\à¯\Îpúò2\Û]‚\ÓšõÄ—f–·\Ï-0³\ØÌ£Iq™{\å,\îDò·û\ÅÏ²u0D€¬Ã‰¥*\'\Þj‚\n6y\ÈA’šV líŠ©ûº¨#tmŽ\\99“1±²J”¦^G¥Qƒ˜1\ÆG“Þ†‡\ä.\Ü/[wü¤´M	\Ú\×o€ò}\á\Û\ÈP%}®cQ\è,Á†šf\ì˜l® õÇªd\Ðø#\Åñ(\ZÝ¨¢A-\ÈM§\ã\"þTÝJ\Ú\ÏTbdb‚‰¥%tfpZû\Ý\é@k5ð§Tò\ÔóÕ‡`\Ã\åÃ›\ícø÷)Þµ*E»V\'LSê««(\ã®“{(Ç¤H¾<\ÃÝ€e”uxóõ—\á\Ó}wª5kc\ã\Æ7›ù\êö¶\ïIo\ß\çO¹!\ÎMP9\ê·„\á9øù[ÿ\Ü:\\\Ñc‰;œö¡r³yö\È\Ù\ÝðÜ†\ÑDJ,ã¶€\Ów.¤‹i½Ž²–\Ð´5(œ*^-?\Þ;:E÷_\" Z	Ú•fJH£Lgcd\Ä9’Fƒµ\Ém@ÿ•D\îÉ‹H ”¦Z	˜‹ó[¾U©e<rgƒ÷2Mª¡t+Ù·]\n³ €‰q¬8\ê\Ë\×}ù_‡¥\Öy\Â\ì„N&Tu‹OÝ·o]©ô ý/?°“3óm¾ñö<\íh\n¢˜Á\àv‹\ÌJ§±›¬º@}um¢cD‘w‚Ø”X·øõ\Çöñ\Ì\çt+j\Ý\Z\êð\Ùñ¢Á…¥ÿrtŽ£³MZ¶`¨\ÝJU^\â\Ï\ê+«Y†\ÓC`H/’T+!~b’_ù\ì\î¾c|]y±ô }?®R¦c)þ¸\å:~ü\Ôú\äc0ú\äÿY\ÈÈ¦óK:ý\0\0\0\0IEND®B`‚',1,'admin','2015-01-20 15:46:06','admin','2016-11-06 15:10:36','','è…¾è®¯','http://exmail.qq.com/',3,'','','username','0',NULL,11,0,1,1,'org.maxkey.authz.exapi.endpoint.adapter.ExtendApiQQExmailDefaultAdapter','connsec','2df203cdfa2bd2dfd29f48739f431869');
/*!40000 ALTER TABLE `apps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apps_cas_details`
--

DROP TABLE IF EXISTS `apps_cas_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apps_cas_details` (
  `ID` varchar(45) NOT NULL,
  `SERVICE` varchar(400) NOT NULL,
  `VALIDATION` varchar(400) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apps_cas_details`
--

LOCK TABLES `apps_cas_details` WRITE;
/*!40000 ALTER TABLE `apps_cas_details` DISABLE KEYS */;
INSERT INTO `apps_cas_details` VALUES ('12e99fd2-20b9-43ce-84c2-f16ef0a675a0','saf','saf'),('3f83593e-3826-4319-8467-651f45d3c977','HTTTPS','HTTTPS'),('41065fe3-ae67-4172-a460-fd0079e88294','http://cas.demo.maxkey.org:8080/demo-cas/','http://login.connsec.com:8080/casproxy'),('489807c2-3311-4289-ad56-30b9047515d9','asfd','asf'),('b1c9f5f0-6850-4845-a4f0-cd48ece0f364','HTTTPS','HTTTPS');
/*!40000 ALTER TABLE `apps_cas_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apps_desktop_details`
--

DROP TABLE IF EXISTS `apps_desktop_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apps_desktop_details` (
  `ID` varchar(45) NOT NULL,
  `PROGRAMPATH` varchar(400) NOT NULL,
  `PARAMETER` varchar(400) DEFAULT NULL,
  `USERNAMETYPE` varchar(45) NOT NULL,
  `PREUSERNAME` varchar(45) DEFAULT NULL,
  `USERNAMEPARAMETER` varchar(45) DEFAULT NULL,
  `PASSWORDTYPE` varchar(45) NOT NULL,
  `PREPASSWORD` varchar(45) DEFAULT NULL,
  `PASSWORDPARAMETER` varchar(45) DEFAULT NULL,
  `SUBMITTYPE` varchar(45) NOT NULL,
  `SUBMITKEY` varchar(45) DEFAULT NULL,
  `PRESUBMIT` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apps_desktop_details`
--

LOCK TABLES `apps_desktop_details` WRITE;
/*!40000 ALTER TABLE `apps_desktop_details` DISABLE KEYS */;
INSERT INTO `apps_desktop_details` VALUES ('c1cabfaeb9a448028ffab2148da9f65c','C:\\Program Files (x86)\\Tencent\\QQ\\Bin\\QQ.exe','','SIMULATION','','username','SIMULATION','','password','Enter','d','');
/*!40000 ALTER TABLE `apps_desktop_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apps_form_based_details`
--

DROP TABLE IF EXISTS `apps_form_based_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apps_form_based_details` (
  `ID` varchar(45) NOT NULL COMMENT 'Ã¤Â¸Â»Ã©Â”Â®',
  `USERNAMEMAPPING` varchar(45) DEFAULT NULL COMMENT 'Ã§Â”Â¨Ã¦ÂˆÂ·Ã¥ÂÂÃ¦Â˜Â Ã¥Â°Â„',
  `PASSWORDMAPPING` varchar(45) DEFAULT NULL COMMENT 'Ã¥Â¯Â†Ã§Â ÂÃ¦Â˜Â Ã¥Â°Â„',
  `REDIRECTURI` varchar(400) DEFAULT NULL COMMENT 'Ã¦ÂÂÃ¤ÂºÂ¤Ã¥ÂœÂ°Ã¥ÂÂ€',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='FormBaseÃ©Â…ÂÃ§Â½Â®Ã¨Â¡Â¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apps_form_based_details`
--

LOCK TABLES `apps_form_based_details` WRITE;
/*!40000 ALTER TABLE `apps_form_based_details` DISABLE KEYS */;
INSERT INTO `apps_form_based_details` VALUES ('850379a1-7923-4f6b-90be-d363b2dfd2ca','userNameIpt','password','https://ssl.mail.163.com/entry/coremail/fcg/ntesdoor2'),('c8038bd4-12a4-4b45-9d43-61b3ecdc2eb4','username','password','http://note.youdao.com/signIn/');
/*!40000 ALTER TABLE `apps_form_based_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apps_oauth_client_details`
--

DROP TABLE IF EXISTS `apps_oauth_client_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apps_oauth_client_details` (
  `CLIENT_ID` varchar(45) NOT NULL COMMENT 'appkey',
  `RESOURCE_IDS` varchar(256) DEFAULT NULL COMMENT 'Ã¥ÂºÂ”Ã§Â”Â¨id',
  `CLIENT_SECRET` varchar(256) DEFAULT NULL COMMENT 'appsecret',
  `SCOPE` varchar(256) DEFAULT NULL COMMENT 'Ã¨ÂŒÂƒÃ¥Â›Â´',
  `AUTHORIZED_GRANT_TYPES` varchar(256) DEFAULT NULL COMMENT 'Ã¨Â®Â¤Ã¨Â¯ÂÃ¦Â–Â¹Ã¥Â¼Â',
  `WEB_SERVER_REDIRECT_URI` varchar(256) DEFAULT NULL COMMENT 'Ã¥Â›ÂžÃ¨Â°ÂƒÃ¥ÂœÂ°Ã¥ÂÂ€',
  `AUTHORITIES` varchar(256) DEFAULT NULL COMMENT 'Ã¦ÂÂƒÃ©Â™ÂÃ¤Â¿Â¡Ã¦ÂÂ¯',
  `ACCESS_TOKEN_VALIDITY` int(11) unsigned DEFAULT NULL COMMENT 'accesstokenÃ¦ÂœÂ‰Ã¦Â•ÂˆÃ¦Â—Â¶Ã©Â—Â´',
  `REFRESH_TOKEN_VALIDITY` int(11) unsigned DEFAULT NULL,
  `ADDITIONAL_INFORMATION` varchar(4096) DEFAULT NULL,
  `APPROVALPROMPT` varchar(45) DEFAULT 'force',
  `AUTOAPPROVE` varchar(45) DEFAULT NULL,
  `IDTOKENENCRYPTEDALGORITHM` varchar(45) DEFAULT NULL,
  `IDTOKENSIGNINGALGORITHM` varchar(45) DEFAULT NULL,
  `IDTOKENENCRYPTIONMETHOD` varchar(45) DEFAULT NULL,
  `USERINFOSIGNINGALGORITHM` varchar(45) DEFAULT NULL,
  `USERINFOCRYPTEDALGORITHM` varchar(45) DEFAULT NULL,
  `USERINFOENCRYPTIONMETHOD` varchar(45) DEFAULT NULL,
  `JWKSURI` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`CLIENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='oauthÃ¥ÂºÂ”Ã§Â”Â¨Ã¤Â¿Â¡Ã¦ÂÂ¯';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apps_oauth_client_details`
--

LOCK TABLES `apps_oauth_client_details` WRITE;
/*!40000 ALTER TABLE `apps_oauth_client_details` DISABLE KEYS */;
INSERT INTO `apps_oauth_client_details` VALUES ('33e28d3d-5c74-4da4-b708-598291f43a2a','33e28d3d-5c74-4da4-b708-598291f43a2a','df9b97b854c5c0079c845b6bf8e4e82ac9bd3faa43f719b4beded2c7e509f159bdbd9571be32a6a15b016e9d764ee4a4fc907307f5c48bbf32a6197b56f97dde1e698f87c64032dc548d6ec7dc3c4863','read','authorization_code',NULL,'ROLE_CLIENT,ROLE_TRUSTED_CLIENT',180,180,'{}','force','read','none','none','none','none','none','none',''),('42853d95-71b1-4641-9e40-c5398e2ee92f','42853d95-71b1-4641-9e40-c5398e2ee92f','db0ec65fb9bdbcbf09a90429b55166f818cd97eed1a25764e172c77ecdff6d573c5631c3fa79e5818d5f7d2b12bb6aef9964797c213709ab1f66e3f540210dd51e698f87c64032dc548d6ec7dc3c4863','read','authorization_code','åŒ—äº¬','ROLE_CLIENT,ROLE_TRUSTED_CLIENT',180,180,'{}','force','read','none','none','none','none','none','none',''),('71a1a118-5f41-40e6-914e-a82a81905b47','71a1a118-5f41-40e6-914e-a82a81905b47','a025cc2b9984d3d9fbcc1e52c7b0ee2c5f3c86ab15435fc782b8e7d21fe9fed348f858de7faeb943855a42dc585654e5f4def8c169305e6370cc8884d3daec4c1e698f87c64032dc548d6ec7dc3c4863','read','authorization_code','SADF','ROLE_CLIENT,ROLE_TRUSTED_CLIENT',180,180,'{}','force','read','none','none','none','none','none','none','DF'),('79b4d1d7-2046-47e4-8fd2-65c3337eee54','79b4d1d7-2046-47e4-8fd2-65c3337eee54','1b3b94f449aa3cda6b00c7730a3d1fac4e1904b86bf2650db7b0d963cff1e1c3440df18d26f10ddf74585e75aacbc279e3a690b3cdadc41d56fe73ed4cb164601e698f87c64032dc548d6ec7dc3c4863','read','authorization_code','FFFF','ROLE_CLIENT,ROLE_TRUSTED_CLIENT',180,180,'{}','force','read','none','none','none','none','none','none',''),('ae20330a-ef0b-4dad-9f10-d5e3485ca2ad','ae20330a-ef0b-4dad-9f10-d5e3485ca2ad','ade8aeb8b9513880baa804887ff89571e7fbe584acdbeff154519a5a39f6a567','openid,profile,read','authorization_code','http://oauth.demo.maxkey.org:8080/demo-oauth/oidc10callback.jsp','ROLE_CLIENT,ROLE_TRUSTED_CLIENT',180,180,'{}','force','read,openid,profile','none','RS256','none','none','none','none',''),('b32834accb544ea7a9a09dcae4a36403','b32834accb544ea7a9a09dcae4a36403','4e1d7eb7b14ad658e8d9066c95902c852ff6494512a742a8392d1d16adc5af551e698f87c64032dc548d6ec7dc3c4863','read','authorization_code','http://oauth.demo.maxkey.org:8080/demo-oauth/oauth20callback.jsp','ROLE_CLIENT,ROLE_TRUSTED_CLIENT',180,180,'{}','force','read','none','RS256','none','none','none','none','http://'),('b490248d-5f68-4996-93bc-60bea2879e93','b490248d-5f68-4996-93bc-60bea2879e93','2974f63bb37aaa5f3482b65a986835cfa41473aed3d0b939345e32eca65cdebbd99cc81926bb24d7bde66aa51d7e26e7ce216d1b5bc30e57f82c90f100165a921e698f87c64032dc548d6ec7dc3c4863','read','authorization_code','sadf','ROLE_CLIENT,ROLE_TRUSTED_CLIENT',180,180,'{}','force','read','none','none','none','none','none','none',''),('c3d44bb1-e2c4-45dd-91ce-43e821f1321c','c3d44bb1-e2c4-45dd-91ce-43e821f1321c','d9457a9a9017d2f92ce3d0b58e4328ea637dcc5a434d3bc900bd5f07cd1eda86','all,read,trust,write','authorization_code','http://liferay.demo.connsec.com:8080/','ROLE_CLIENT,ROLE_TRUSTED_CLIENT',180,180,'{}','force','trust,write,read,all','none','none','none','none','none','none',''),('my-client-with-registered-redirect',NULL,NULL,'trust','authorization_code,client_credentials','http://anywhere','ROLE_CLIENT',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('my-less-trusted-client',NULL,NULL,NULL,'authorization_code,implicit','http://','ROLE_CLIENT',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('my-trusted-client',NULL,NULL,'read,write,trust','password,authorization_code,refresh_token,implicit','http://','ROLE_CLIENT, ROLE_TRUSTED_CLIENT',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('my-trusted-client-with-secret',NULL,'somesecret','read,write,trust','password,authorization_code,refresh_token,implicit','http://','ROLE_CLIENT, ROLE_TRUSTED_CLIENT',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('my-untrusted-client-with-registered-redirect',NULL,NULL,'read','authorization_code','http://anywhere','ROLE_CLIENT',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `apps_oauth_client_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apps_saml_v20_details`
--

DROP TABLE IF EXISTS `apps_saml_v20_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apps_saml_v20_details` (
  `ID` varchar(45) NOT NULL,
  `CERTISSUER` varchar(200) DEFAULT NULL,
  `CERTSUBJECT` varchar(200) DEFAULT NULL,
  `CERTEXPIRATION` varchar(100) DEFAULT NULL,
  `KEYSTORE` blob,
  `SPACSURL` varchar(200) NOT NULL,
  `ISSUER` varchar(300) DEFAULT NULL,
  `ENTITYID` varchar(300) DEFAULT NULL,
  `VALIDITYINTERVAL` int(10) unsigned DEFAULT NULL,
  `NAMEIDFORMAT` varchar(45) DEFAULT NULL,
  `AUDIENCE` varchar(300) DEFAULT NULL,
  `ENCRYPTED` tinyint(3) unsigned DEFAULT NULL,
  `BINDING` varchar(45) DEFAULT NULL,
  `NAMEIDCONVERT` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apps_saml_v20_details`
--

LOCK TABLES `apps_saml_v20_details` WRITE;
/*!40000 ALTER TABLE `apps_saml_v20_details` DISABLE KEYS */;
INSERT INTO `apps_saml_v20_details` VALUES ('525d261fa3b04d19af0debabbd5a1e2d','VeriSign Class 3 International Server CA - G3','CN=proxy.salesforce.com, OU=Applications, O=\"Salesforce.com, Inc.\", L=San Francisco, ST=California, C=US','17 Oct 2017 23:59:59 GMT',_binary 'þ\íþ\í\0\0\0\0\0\0\0\0\0\0 https://sso.connsec.com/sec/saml\0\0CDF­\Ð\0X.509\0\0d0‚`0‚H C‚±7±7hÖºpBn g0\r	*†H†÷\r\00¼10	UUS10U\nVeriSign, Inc.10UVeriSign Trust Network1;09U2Terms of use at https://www.verisign.com/rpa (c)101604U-VeriSign Class 3 International Server CA - G30\r131018000000Z\r171017235959Z010	UUS10U\nCalifornia10U\rSan Francisco10U\nSalesforce.com, Inc.10UApplications10Uproxy.salesforce.com0‚\"0\r	*†H†÷\r\0‚\00‚\n‚\0²mKÿ-&c\Ùü\"Ž\Ï÷I\ì\Å\ëƒ==±`­Ó…u\ìjô\Øù\âDG¾\Ï\ë)\ßY°$·\Z[U\Ø`E\æ1_;©µ\nþe@\Âñ\\1ûVC]\Ã\ç™\æ¡BUpgT\×€‰@:<\í\Æx\0…b¹%dQô°^1ò“2x2\Ð\"\É\Ò\nu8Ù‘©¢\Z­dß¯l4H\ÑM>)ö´#zý\èz!¾Ý²Dù\0Á¸˜Rh\ÄB¬ˆ”!Ÿ)ªz…`,Ä›\ÝD\ÒðŸQ\Õ‹\é\"°-ý4\ÕÁ\Ì\Õ~»\í\ÇP·AZ¡¥g\Þ\ï\ë¤\Ð	œpih‘5\rö—\ß\Ú\éð5iJO^\ÊsF§RHÿ©ˆ¿q\0£‚‡0‚ƒ0U0‚proxy.salesforce.com0	U0\00Uÿ 0(U%!0++	`†H†øB0CU <0:08\n`†H†øE60*0(+https://www.verisign.com/cps0U#0€×›|\Ø\" ÷Ý­_\Î)›XÃ¼F\0µ0AU:0806 4 2†0http://SVRIntl-G3-crl.verisign.com/SVRIntlG3.crl0r+f0d0$+0†http://ocsp.verisign.com0<+0†0http://SVRIntl-G3-aia.verisign.com/SVRIntlG3.cer0\r	*†H†÷\r\0‚\02\Âøy¹…·D~¦^\é\ÑZ8£±\Êp­W†c\ÎD…\ÂôQ÷\îsŠ6*–¸¢\íR/…€X(\Ä\é+=/T½6›t!Â¦!g‘²2J+9\Åk\Ë1¾m\Î\×V½¹W\à¾iðŸED¦\ä\æ\Zõ\ï!D€gš´	P\àÍ±¬®5§eye€î¤¯\èœH:1\ÑZ¬;l	\n²\Ù\Ðz–yEei\í~ô{’:ô¶µü@q=\×\Å\ã\n\ïA	ªK»Á;\Å:ö;LƒÿiY3H¥ø.9º]\Éø\Ô\Ð\Þ\Ø £Jf®ar…þÀ©qŒ6=g•b`­^Ë”\åo\Ø)\Â\ëS\'Rÿ\Ú@w§\0\0\0\0connsec.com\0\0>‰\Ô\r”\0\0\00‚ü0\n+*\0‚\è\Ë¯aM\éº¡-ÿ5³·ž¿Hfk<ëž“­nH	„\Í%ÐŠ{„4º#’bº¨ùzõ\Ä;“W¿\Â\rËŠ£2­?0š\ìQC#6¢ˆwP@\\ª09CJ.õ*†™pA\Êa§û\á\ÄH/\íùCdB\\úO @‚E¹z\î=ó\0h%\ìb:}\È\Å\Ã\ËN©\à\í«¼R\'˜C‡Á+Æš*ks«\éw¿x\ë .\îG\Õ	±k[¡w—³n_+…\Ù\0•s—†\Â=©`wd¶@\ÑñÚ‡Hœüte·``ý`\â±‡(5\'1Z½\Ù4LRhy_ú23y°Ñ¿™!¬\ËM\ä,\é&ýC\Úz\Òb†_£P¡û±B5Ï´\Í•%¤ö ý¹\ÐP}”³Å•cœš\×qŸ‹Hb~\Ä¿7M÷\êy¥œ’Ð‘`žO•Ž=œY»`/«r\ï\Ñ\ë¼:›Pƒ~ƒËž4Voü£·€´´Aˆ7^»ú/\ÏKþlbûŽ‹rÀaS‚)™$¯T‹C?\'¤V#ù¯i\ÑÄ¤Ág”w\Í<e–\nlT\Ú®ñºM¯¥›{\0\n¡cl–-‹§ƒ”\É\0^:VÂ¦ø#\Z<_\ì¾Ó‚Ü•¹O+l^}.¶¼k1S«øO\'=F6}Õ¾³e2¼C©\ârƒ\Ê\Ì\Øtó²i´÷\nd\Ø	\è GJOö~ZH®Qe¿¦\Ï4{\É\í¥túmÀu·Ó«`¥F\ÂY°q­¶”¡t§|k(ø\é}ò’>‰J¶R\ï\í‹\Åj‚·4æ–ª\n\\7oÏ·€¬=Rù]\Ì\ëlzSÐ¨u¤\è~\ÚÅ¿€>¥Eó.O\0˜\í\Øá¸EHù‡×ªqZlNt*x\Ò\íút’\Å6:?”\å\âLUº“8ü\åŒ#g\ìÑª†;\Ö.\Ý-R°§>“„2R\ãj\Ë\"¬ÿ«ßˆÀ–P`\×\ÇQüzV±71U×»$‰ðRtD‹¬_4k¡ñ\â\Îö,\Ñ\ÝoôUüðs·™h&p8k—1Ü¢˜4¹ª—\ÑZ8‰5Y‹õ\Ô1Þ—¦ð\È\á!°E\ßUðOó4/ 0ýÜ¡6$‹ùZ‰hN: \È_y­\r\ç]Mg…ûU\Ã`–Cs\r\Ö\Û\ë\Òù8C—œ\ÕlºWð\Ç ø“8¾X?uhu=R$ˆN\ÓA—z–‰Ø¯e¬›{˜\Î\Õ\Z12\ãi\ï\â:;`\ÊZ-\0²)z\'\Ô\ì6]¦`\Ë	¾µƒaA\àó,(Lº\ë\Æqbp\Ãø°\Ú\"‰÷\ÔEÿj%YÈ¸\Ã8É­DZ\åÝš©´O6D|ú<MWô€¬Tþ\Z]5¹ú\Øz>x°þÄ¿\Ó\ìˆf\Ði\í\Òv\ÖN\Ã\Åm\Ò¸\âK8W\îqI\Ö$\Î	\ÉF\\ŸðL\ìý2,j²\Ü\Ð\"\Ð{Að–0ˆy…öPa=kV\ß~9\ÅW>´o\Òâµ‹”®\ÄU^üœke‹ET3|Ÿ·n`\ç\å\ï8!Q™jªKO´s{\r=l¢ø§K/\ÍJ\Â.—\å\ä,\ìÇ\Ï\Ü ?Q\ã\"Z÷h¶9}B,€\Ø\Õ \Ú\Ü:\Ù\Èsu‰Œu˜©»ýs	)$†f\ï\ï…?¤Ü¨¥-¼gløk¥\Í^ŸÑ¼	Oå±°›ÔŽ¹lñ\ÕI\Ð$\ïl°¼Kcø¾\ï0›±\Ô\Ö?\"¬,Ï†8›²\è.\ÎÕŽIuxtš©?nm]®œ8™J\ìº2$\Ã\0\0\0\0X.509\0\00‚\Z0‚ Q‹¸û0\r	*†H†÷\r\00O10	UCN10	USH10	USH10U\nconnsec10Uconnsec.com0\r130509145555Z\r400924145555Z0O10	UCN10	USH10	USH10U\nconnsec10Uconnsec.com0‚\"0\r	*†H†÷\r\0‚\00‚\n‚\0›s~\ìÿ\æg9h\Ç}ò@³‡öç«¦¦^Š\Û\Ú\Øk²¯\ä:\êi\í&^\éWüö+br‡Õš‘ÿ<KP\ßu®\Úx¨7›\å\Ïi\Ïû¢\È\×-.ˆB\È1¢‹l´^D\Í~.\×ûø„j¬ôó¨\æ\ÂóFðQ)÷#Y#\Z «\â›X0`ŸØ·\Úý¯©‚\ZC\ê÷óŽ~À<\ÆÉ”#–PktJ²Žš$uÍ¯E¸\çŠ›f x‚\Ýöä„¿±üº\ã:ùö\Ä\0\Þt6L©Ú³/8\Æ\Ôˆ\ÃFQM\á¼z|,‰ó\Ü\ïJ9uuX?ê Ž¢\ËD™q–\'ûŒn\ãµ\Ð\Äk\Âa&\í$sq\Å\00\r	*†H†÷\r\0‚\0š¤\rÅŽ²š8÷xU°\Ç\Ð\èÀ\"½ü\çN‡KT \ÙFžØ€\Ôw^Uó_:¶Œ!C|µŸEh\ë.þRt\êu«\ìdþ\ÄñI¬wczÌŠ.\è=\îe\ËZ\0‘\ã\Å\n×Ÿ\é½\ÆOG-š²#qlB\çU“}EB\nj^Ÿ›j\Ñ7\â\Ñþú°x[3\Ã%\Ïsº2H‰…ÙˆE¯°ý¥Ú‘p´zŠ”o\êZª\â:\æ\î8pS¹–Sr€R«)‘øûô\è+h^–ª«Y§RÁ^\ØWD˜rO£u ö±s¨YZo“þ>¾#ú\î¢\áNûzxœô\é°)Yƒ|º¼wŒ-}\ãn\ï—\ÔmR}(\áG%	\ëœU+V\×H’›ð\âÁ	\Ø\ÖDÀ','https://shikey-dev-ed.my.salesforce.com?so=00D90000000r4kw','https://sso.connsec.com/sec/saml','https://sso.connsec.com/sec/saml',15,'persistent','https://sso.connsec.com/sec/saml',0,'Redirect-Post',0);
/*!40000 ALTER TABLE `apps_saml_v20_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apps_token_based_details`
--

DROP TABLE IF EXISTS `apps_token_based_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apps_token_based_details` (
  `ID` varchar(45) NOT NULL COMMENT 'Ã¥ÂºÂ”Ã§Â”Â¨id',
  `ALGORITHMKEY` varchar(400) NOT NULL COMMENT 'Ã¥ÂŠÂ Ã¥Â¯Â†Ã¥Â¯Â†Ã©Â’Â¥',
  `ALGORITHM` varchar(45) NOT NULL COMMENT 'Ã¥ÂŠÂ Ã¥Â¯Â†Ã§Â®Â—Ã¦Â³Â• /DES,DESede,Blowfish and AES',
  `UID` tinyint(4) DEFAULT '0' COMMENT 'Ã§Â”Â¨Ã¦ÂˆÂ·id',
  `USERNAME` tinyint(4) DEFAULT '0' COMMENT 'Ã§Â”Â¨Ã¦ÂˆÂ·Ã¥ÂÂ',
  `EMAIL` tinyint(4) DEFAULT '0' COMMENT 'Ã§Â”ÂµÃ¥Â­ÂÃ©Â‚Â®Ã¤Â»Â¶',
  `EXPIRES` int(10) unsigned DEFAULT '0' COMMENT 'Ã¨Â¿Â‡Ã¦ÂœÂŸÃ¦Â—Â¶Ã©Â—Â´',
  `REDIRECTURI` varchar(400) NOT NULL COMMENT 'Ã¥Â›ÂžÃ¨Â°ÂƒÃ¥ÂœÂ°Ã¥ÂÂ€',
  `WINDOWSACCOUNT` tinyint(4) DEFAULT '0',
  `EMPLOYEENUMBER` tinyint(4) DEFAULT '0',
  `DEPARTMENTID` tinyint(4) DEFAULT '0',
  `DEPARTMENT` tinyint(4) DEFAULT '0',
  `COOKIENAME` varchar(45) DEFAULT NULL,
  `tokenType` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apps_token_based_details`
--

LOCK TABLES `apps_token_based_details` WRITE;
/*!40000 ALTER TABLE `apps_token_based_details` DISABLE KEYS */;
INSERT INTO `apps_token_based_details` VALUES ('1327c121-cfad-49ba-bf61-afd3a1e09d5c','d6227a3d7756c255874ec7029678b8d1','DES',1,1,1,1,'http://tokenbased.demo.maxkey.org:8080/demo-ltpa/ltpa.jsp',0,1,0,0,'ltpa','LTPA'),('38c8a544eaa04aaeaa49d9c77ace40cd','c1f6adfcadd8ba23f73395f16a45dbe7','DES',1,1,1,1,'http://tokenbased.demo.maxkey.org:8080/demo-tokenbase/jsontoken.jsp',0,0,0,1,NULL,'POST'),('78917a82-1c86-4020-b86a-3b1b350357e3','985e805bd49770e7e797209db3cc2767','DES',0,1,1,1,'http://tokenbased.demo.maxkey.org:8080/demo-jwt/jwtcallback.jsp',0,0,0,0,'ttt','POST'),('f1e33b71-f553-42ab-ae91-2fd913854cda','1729a1ee16e532d61e097c01054dcfe7','DES',0,1,0,1,'http://tokenbased.demo.maxkey.org:8080/demo-tokenbase/sampletoken.jsp',0,0,0,0,NULL,'POST');
/*!40000 ALTER TABLE `apps_token_based_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forgot_password`
--

DROP TABLE IF EXISTS `forgot_password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forgot_password` (
  `ID` varchar(45) NOT NULL COMMENT 'Ã¤Â¸Â»Ã©Â”Â®',
  `UID` varchar(45) DEFAULT NULL COMMENT 'Ã§Â”Â¨Ã¦ÂˆÂ·ID',
  `STATUS` tinyint(3) unsigned DEFAULT NULL COMMENT 'Ã§ÂŠÂ¶Ã¦Â€Â',
  `CREATEDDATE` datetime DEFAULT NULL COMMENT 'Ã¥ÂˆÂ›Ã¥Â»ÂºÃ¦Â—Â¶Ã©Â—Â´',
  `EMAIL` varchar(45) NOT NULL COMMENT 'Ã©Â‚Â®Ã§Â®Â±',
  `USERNAME` varchar(45) NOT NULL COMMENT 'Ã§Â”Â¨Ã¦ÂˆÂ·Ã¥ÂÂ',
  PRIMARY KEY (`ID`),
  KEY `Index_forgot_password_id` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Ã¦Â‰Â¾Ã¥Â›ÂžÃ¥Â¯Â†Ã§Â Â';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forgot_password`
--

LOCK TABLES `forgot_password` WRITE;
/*!40000 ALTER TABLE `forgot_password` DISABLE KEYS */;
INSERT INTO `forgot_password` VALUES ('184d656b-01e8-472f-9661-6e5115aba41d','6ac07a3d-b935-43f2-a693-9ce49b6695b7',1,'2013-05-03 10:29:45','shimingxy@qq.com','sadf'),('44e7dfb1-49d7-4f3f-88f8-9542d383c3bb','6ac07a3d-b935-43f2-a693-9ce49b6695b7',1,'2013-05-03 10:32:03','shimingxy@qq.com','sadf'),('ebd73563-ee1f-493e-8f71-48b2c762cf05','6ac07a3d-b935-43f2-a693-9ce49b6695b7',1,'2013-05-03 10:30:52','shimingxy@qq.com','sadf');
/*!40000 ALTER TABLE `forgot_password` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_member`
--

DROP TABLE IF EXISTS `group_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_member` (
  `ID` varchar(45) NOT NULL DEFAULT '' COMMENT 'Ã¤Â¸Â»Ã©Â”Â®',
  `GROUPID` varchar(45) NOT NULL COMMENT 'Ã¥ÂºÂ”Ã§Â”Â¨ID',
  `MEMBERID` varchar(45) NOT NULL COMMENT 'Ã§Â”Â¨Ã¦ÂˆÂ·ID',
  `TYPE` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_APPROLEU_REFERENCE_APPROLES` (`GROUPID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Ã¥ÂºÂ”Ã§Â”Â¨Ã¨Â§Â’Ã¨Â‰Â²Ã§Â”Â¨Ã¦ÂˆÂ·Ã¨Â¡Â¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_member`
--

LOCK TABLES `group_member` WRITE;
/*!40000 ALTER TABLE `group_member` DISABLE KEYS */;
INSERT INTO `group_member` VALUES ('0313f3d7-166f-4436-b667-ba940d58f507','0E1A4E0F39484456BB2BDE902B4BC275','7BF5315CA1004CDB8E614B0361C4D46B','USER'),('04ded9a1-4120-4de6-8856-2d85691b21f3','702D5E61C13047FEB64CF8DF476672D0','58bf53ad-46e9-4ebe-a910-2f325d459088','USER'),('151c97f3-4b73-4c98-a508-dba342e14aaa','3C36EE4661684DF8B4CBC977B9C8AE35','f3993b8f-e78f-4d79-b629-d6179c1ec2aa','USER'),('1a2d6d05-d7e9-480f-a854-f5b14de0ff80','7BA5E4713E994E3BBC11BA642DF988BF','7BF5315CA1004CDB8E614B0361C4D46B','USER'),('1fef08e5-a8da-409e-b2b7-9ba59e066396','35E6CE83F2C242838BF00563E2B1A3AE','58bf53ad-46e9-4ebe-a910-2f325d459088','USER'),('210d87eb-3a86-452f-92f6-7ca3107b6f93','10064180F8AC4492B30CBC9F8972C3D7','58bf53ad-46e9-4ebe-a910-2f325d459088','USER'),('21ef51cc-702f-432c-ba64-6b5006c4b39c','016e3a86-3181-4126-8e00-acb2e234bb58','97b8fc47-fb31-4c7a-8322-7d16266b08af','USER'),('2496b63c-b883-418d-8f44-3b3219886a04','2D7A79F2889347B781AF3F162B6CAF8B','3557da42-7f8d-4a55-ae19-b4abb1be926d','USER'),('24aff38d-45e3-47c7-8641-a0f06fb43ec8','702D5E61C13047FEB64CF8DF476672D0','7BF5315CA1004CDB8E614B0361C4D46B','USER'),('24edbd93-2586-4882-8e75-8cf261144065','10064180F8AC4492B30CBC9F8972C3D7','5a405e33-4ec1-4a59-b611-1abf42453611','USER'),('297c2209-c5bc-4eaf-89d1-b67e028b5bfa','35E6CE83F2C242838BF00563E2B1A3AE','44D64694BADD4423A336C05D49469B60','USER'),('2b1078ab-2152-4c35-9766-ebd8429ac571','2D7A79F2889347B781AF3F162B6CAF8B','44D64694BADD4423A336C05D49469B60','USER'),('2ca811a2-b705-42fc-8bea-73c97f35fe7d','2D7A79F2889347B781AF3F162B6CAF8B','a64d6778-cea0-4790-9a9f-155de1984501','USER'),('2d8813f8-8dce-4285-a9b2-5c51ab910e80','2D7A79F2889347B781AF3F162B6CAF8B','97b8fc47-fb31-4c7a-8322-7d16266b08af','USER'),('2f4ff1e8-a763-4f97-a382-3c6704f6c7e3','702D5E61C13047FEB64CF8DF476672D0','5a405e33-4ec1-4a59-b611-1abf42453611','USER'),('31d439f0-3295-4cb8-b4e8-fc00be0ca6db','1DF53A025E944C4C95DB5D330083DDB9','44D64694BADD4423A336C05D49469B60','USER'),('353da12a-c21b-46f4-8cd9-36759b2f0846','016e3a86-3181-4126-8e00-acb2e234bb58','6a52a7af-6855-4e15-8616-31ff0c5bcebc','USER'),('3717900f-e470-4b9b-9362-12ed87a719aa','016e3a86-3181-4126-8e00-acb2e234bb58','7b7af24a-1d8c-430a-ac2a-6d869bce96f6','USER'),('4a1547d0-34f4-46fb-914c-d4c4f48746fa','FC43153502BB4A489F71C390A09B55C1','1ff88ce8-4aea-4a43-9cbb-144487e71d6e','USER'),('4c048c310b24b6c0000801402b3b0d94','0E1A4E0F39484456BB2BDE902B4BC275','568d2022-6a45-496c-8df4-9886a27ff117','USER'),('4e9952f91590b6c0000801402b4323f4','0E1A4E0F39484456BB2BDE902B4BC275','53b78e43-1216-492d-bd2f-a2cc9318cb71','USER'),('5618b2f2-c69e-458e-b77c-84de51fee62b','3C36EE4661684DF8B4CBC977B9C8AE35','1b82c5a7-8822-4e0a-9fbb-3e5b54a1d9e2','USER'),('56d93dfa-3035-487f-bec4-fc952a12084c','702D5E61C13047FEB64CF8DF476672D0','97b8fc47-fb31-4c7a-8322-7d16266b08af','USER'),('577be9c2-990f-47c8-b295-3ce529289e2e','2D7A79F2889347B781AF3F162B6CAF8B','1b82c5a7-8822-4e0a-9fbb-3e5b54a1d9e2','USER'),('588a5565-0bca-4ff9-9331-39caf8b3ec8f','702D5E61C13047FEB64CF8DF476672D0','44D64694BADD4423A336C05D49469B60','USER'),('5b896a4a-0278-4f39-b69d-22f96c4b19ca','3C36EE4661684DF8B4CBC977B9C8AE35','97b8fc47-fb31-4c7a-8322-7d16266b08af','USER'),('5d6fde3c-444c-472f-b277-b5b8e3040cd0','2D7A79F2889347B781AF3F162B6CAF8B','f3993b8f-e78f-4d79-b629-d6179c1ec2aa','USER'),('6c797ac8-26e9-42d0-a064-73a6ce1d498f','2D7A79F2889347B781AF3F162B6CAF8B','58bf53ad-46e9-4ebe-a910-2f325d459088','USER'),('6f8f47a01590b6c0000801402b461e77','0E1A4E0F39484456BB2BDE902B4BC275','B79A140814E3446EA24E673DE436ABCB','USER'),('7482bb0a-6cb2-4d79-b56a-3155701c81c9','35E6CE83F2C242838BF00563E2B1A3AE','a64d6778-cea0-4790-9a9f-155de1984501','USER'),('7962a0d5-ea1d-49aa-9e5a-29f2b4dfb1c0','3C36EE4661684DF8B4CBC977B9C8AE35','6a52a7af-6855-4e15-8616-31ff0c5bcebc','USER'),('7b8a3284-7231-4592-b771-5dbac6fbefa6','FC43153502BB4A489F71C390A09B55C1','1b82c5a7-8822-4e0a-9fbb-3e5b54a1d9e2','USER'),('7d2c6ffe1590b6c0000801402b461e19','0E1A4E0F39484456BB2BDE902B4BC275','6ac07a3d-b935-43f2-a693-9ce49b6695b7','USER'),('7fc8e1a3-39c6-48c6-8162-e3a908e26cc6','ALL_USER_GROUP','97b8fc47-fb31-4c7a-8322-7d16266b08af','USER'),('8bc887fc1590b6c0000801402b461dbb','0E1A4E0F39484456BB2BDE902B4BC275','0e675911-09f9-4380-8280-33ec6a088187','USER'),('903e0493-7e38-468b-b597-bc89920b6ed9','1DF53A025E944C4C95DB5D330083DDB9','58bf53ad-46e9-4ebe-a910-2f325d459088','USER'),('951ce5c5-3b29-40d7-bf60-5b998e1098cc','1DF53A025E944C4C95DB5D330083DDB9','3557da42-7f8d-4a55-ae19-b4abb1be926d','USER'),('a7adea67-0857-4af6-8e0e-2eb95861b070','3C36EE4661684DF8B4CBC977B9C8AE35','58bf53ad-46e9-4ebe-a910-2f325d459088','USER'),('bad2a79b-0e0d-4350-8672-f1f1c87fff25','35E6CE83F2C242838BF00563E2B1A3AE','1b82c5a7-8822-4e0a-9fbb-3e5b54a1d9e2','USER'),('bb4afa27-dd51-44c9-a2ba-74f70798842c','1DF53A025E944C4C95DB5D330083DDB9','5a405e33-4ec1-4a59-b611-1abf42453611','USER'),('c182a6fb-4c50-4a10-be85-538d671c7c6a','3C36EE4661684DF8B4CBC977B9C8AE35','5a405e33-4ec1-4a59-b611-1abf42453611','USER'),('c5f8d381-79c5-40a3-8e46-45d26e4c9250','702D5E61C13047FEB64CF8DF476672D0','6a52a7af-6855-4e15-8616-31ff0c5bcebc','USER'),('c8f7bcff1590b6c0000801402b461e38','0E1A4E0F39484456BB2BDE902B4BC275','7AF5AD2269784EFB92DB2F621ED1E340','USER'),('cab09b86-ee4b-4991-9913-23eee5f2e792','016e3a86-3181-4126-8e00-acb2e234bb58','58bf53ad-46e9-4ebe-a910-2f325d459088','USER'),('cca1a319-9d03-4502-8d4d-76f26b521b8b','3C36EE4661684DF8B4CBC977B9C8AE35','3557da42-7f8d-4a55-ae19-b4abb1be926d','USER'),('ccbeaa39-156a-4b59-85c8-c678c5a6d160','016e3a86-3181-4126-8e00-acb2e234bb58','7BF5315CA1004CDB8E614B0361C4D46B','USER'),('cd3aca37-3a9b-4f23-a32e-814d07862dfb','702D5E61C13047FEB64CF8DF476672D0','f3993b8f-e78f-4d79-b629-d6179c1ec2aa','USER'),('d3551fa7-f84b-488b-862a-5aa7e570b0c3','2D7A79F2889347B781AF3F162B6CAF8B','6a52a7af-6855-4e15-8616-31ff0c5bcebc','USER'),('d694d4fc1590b6c0000801402b461dea','0E1A4E0F39484456BB2BDE902B4BC275','2C3FA5A17E51403CA49E4BEFF41B7375','USER'),('dbb8a8d5-ac77-4f82-8087-32899c3c438a','2D7A79F2889347B781AF3F162B6CAF8B','5a405e33-4ec1-4a59-b611-1abf42453611','USER'),('e3a346eb-a8c4-43fd-adf7-b561117ee732','10064180F8AC4492B30CBC9F8972C3D7','6a52a7af-6855-4e15-8616-31ff0c5bcebc','USER');
/*!40000 ALTER TABLE `group_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_privileges`
--

DROP TABLE IF EXISTS `group_privileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_privileges` (
  `ID` varchar(45) NOT NULL COMMENT 'Ã¤Â¸Â»Ã©Â”Â®',
  `GROUPID` varchar(45) NOT NULL COMMENT 'Ã¥ÂºÂ”Ã§Â”Â¨Ã¨Â§Â’Ã¨Â‰Â²ID',
  `APPID` varchar(45) NOT NULL COMMENT 'Ã¥ÂºÂ”Ã§Â”Â¨ID',
  PRIMARY KEY (`ID`),
  KEY `FK_APPROLEA_REFERENCE_APPLICAT` (`APPID`),
  KEY `FK_APPROLEA_REFERENCE_APPROLES` (`GROUPID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Ã¥ÂºÂ”Ã§Â”Â¨Ã¨Â§Â’Ã¨Â‰Â²Ã¥ÂºÂ”Ã§Â”Â¨Ã¥Â…Â³Ã¨ÂÂ”Ã¨Â¡Â¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_privileges`
--

LOCK TABLES `group_privileges` WRITE;
/*!40000 ALTER TABLE `group_privileges` DISABLE KEYS */;
INSERT INTO `group_privileges` VALUES ('01068c63-4f71-4d10-a863-75cc3ad76657','null','cas'),('0b9fd7f4-dce8-4e04-b85c-85bf1f0c1319','2D7A79F2889347B781AF3F162B6CAF8B','69DB747E5A464ECDA5E27C5C56C4006C'),('0bcdeae4-dc1a-455d-bf8d-04cd1758226d','016e3a86-3181-4126-8e00-acb2e234bb58','41065fe3-ae67-4172-a460-fd0079e88294'),('0efef5a2-d04f-436d-b9d6-83890aa78150','ALL_USER_GROUP','69DB747E5A464ECDA5E27C5C56C4006C'),('1650a3cb-183a-42e2-90da-95b0eabccc2d','0E415B0F253D45B0B752631A0D41D94F','7610cb37255842f892a2bf8bc822cb49'),('1a4311130cc4b6c0000801402b1878eb','0E1A4E0F39484456BB2BDE902B4BC275','0c5cca99774d41b797646e4bc666f295'),('1acce469-c8c5-4fb9-812c-72d214a4fe18','ALL_USER_GROUP','363b7606ff644c04a3c7a848d2d32247'),('1b0c8bd6-33e9-48a5-a0b7-f746272a2161','null','cas'),('204f1e1b-520a-4c64-8947-d94e70dfd4dc','0E415B0F253D45B0B752631A0D41D94F','33d21613-29a4-bf56-f8b9-0147168b7d20'),('213f81bf-a3c2-48db-b413-b0175550607f','0E415B0F253D45B0B752631A0D41D94F','525d261fa3b04d19af0debabbd5a1e2d'),('2949f0a8-02f5-40b8-be4c-8f9bb532ca4d','0E415B0F253D45B0B752631A0D41D94F','363b7606ff644c04a3c7a848d2d32247'),('37003683-350a-4c78-9d59-f635d2c74103','0E1A4E0F39484456BB2BDE902B4BC275','e6bfadbfc1d64d0e9140a716548c35db'),('3c20d159-f3fd-4c1d-9267-ce2cb349820f','016e3a86-3181-4126-8e00-acb2e234bb58','71b7b9b1-7d12-4f24-a956-24d21fd66052'),('3e6764730b24b6c0000801402b3cdad2','10064180F8AC4492B30CBC9F8972C3D7','0c5cca99774d41b797646e4bc666f295'),('4103ca41-7560-44e1-be0c-810d28a2ea86','0E415B0F253D45B0B752631A0D41D94F','3f57d0b2-99ab-4e66-a938-718befb55369'),('442ca6d1-a17b-4a3c-9712-c3b2c1473008','ALL_USER_GROUP','b32834accb544ea7a9a09dcae4a36403'),('44dc1c90-0216-4dc0-a7b5-127b5d24a832','ALL_USER_GROUP','525d261fa3b04d19af0debabbd5a1e2d'),('484a3f43-bf5a-436e-a5c0-f1d4ac28def0','ALL_USER_GROUP','33d21613-29a4-bf56-f8b9-0147168b7d20'),('4f5b4738-5b3b-4161-950e-051e6a5d158f','2D7A79F2889347B781AF3F162B6CAF8B','cas'),('50093f71-d4d8-42d6-9a3b-e7c1264adca6','ALL_USER_GROUP','1327c121-cfad-49ba-bf61-afd3a1e09d5c'),('547cdf12-3e03-4324-9fef-702f03500cd9','ALL_USER_GROUP','38c8a544eaa04aaeaa49d9c77ace40cd'),('54caa2f7-b393-4e4a-a5af-5df9d6dc33c4','016e3a86-3181-4126-8e00-acb2e234bb58','38c8a544eaa04aaeaa49d9c77ace40cd'),('568f3d7a-ce67-4d03-9d45-89fb6be215e3','0E415B0F253D45B0B752631A0D41D94F','a40388d23cea4c5ba93bed865b81d255'),('577246060cc4b6c0000801402b187968','0E1A4E0F39484456BB2BDE902B4BC275','3ada377b22d24254964b9c5707f792f1'),('6197521b-7b29-417e-883a-0ab6f12e884a','ALL_USER_GROUP','f1e33b71-f553-42ab-ae91-2fd913854cda'),('63e5d968-3df3-46ff-856e-b4e65d05b22f','2D7A79F2889347B781AF3F162B6CAF8B','3f57d0b2-99ab-4e66-a938-718befb55369'),('6d1d9701-93f7-4d9c-a3b0-e94b93f82acc','2D7A79F2889347B781AF3F162B6CAF8B','525d261fa3b04d19af0debabbd5a1e2d'),('6e153505-eeaf-4df8-a652-f2616bb1e5ca','0E1A4E0F39484456BB2BDE902B4BC275','7132bffd-1e94-bf56-f8b9-01474a2fe832'),('6e2f1b9d-1910-422f-a8d5-d1e09898c25c','0E1A4E0F39484456BB2BDE902B4BC275','213ffd94-1254-bf56-f8b9-014749d3a76e'),('70a24f4c-af66-45ed-90fa-b7e6682688dd','0E415B0F253D45B0B752631A0D41D94F','38c8a544eaa04aaeaa49d9c77ace40cd'),('73bb76910cc4b6c0000801402b18789d','0E1A4E0F39484456BB2BDE902B4BC275','007a423d09dd4d3891f881affc2168c9'),('752ad92f-a724-4d24-965d-7c299888eb92','0E1A4E0F39484456BB2BDE902B4BC275','363b7606ff644c04a3c7a848d2d322sd'),('7780cce2-efc4-46ab-98b7-e632f180b7f3','016e3a86-3181-4126-8e00-acb2e234bb58','7610cb37255842f892a2bf8bc822cb49'),('7b40056d-b4b0-4f14-b4f7-63fbf8a51113','0E1A4E0F39484456BB2BDE902B4BC275','69DB747E5A464ECDA5E27C5C56C4006C'),('7d07eb43-8b71-4bb5-9815-1d5d077ba2b7','ALL_USER_GROUP','3f57d0b2-99ab-4e66-a938-718befb55369'),('7e0ccd50-9772-4b84-9964-d69395586bda','ALL_USER_GROUP','52f0002d-4ef7-4b27-8c5b-41b9ee80835d'),('7e6b0f83-0834-47b0-bad8-425491dec6b1','2D7A79F2889347B781AF3F162B6CAF8B','38c8a544eaa04aaeaa49d9c77ace40cd'),('84dacff3-2c0a-49a7-bc9a-3fa14289d367','1DF53A025E944C4C95DB5D330083DDB9','363b7606ff644c04a3c7a848d2d32247'),('860553fb0cc4b6c0000801402b187a33','0E1A4E0F39484456BB2BDE902B4BC275','b20574e77d684bc2a7b622b0a7b11c68'),('88e218c2-c88e-4239-9b8f-48d94b63c795','0E1A4E0F39484456BB2BDE902B4BC275','b32834accb544ea7a9a09dcae4a36403'),('95b1eaf61058b6c000080140a69ee2ff','0E1A4E0F39484456BB2BDE902B4BC275','0b6765163d3d4600a35057dc5e64d906'),('9952b873-13e9-4646-8bb7-f3839f6055f4','DE1700C5A51F472D8EC31A36BB0FFF06','95ab7aec-782d-480f-8b86-74a851586b3d'),('99c80daf-7838-4c7c-bf04-5ed5f14d63a6','ALL_USER_GROUP','c1cabfaeb9a448028ffab2148da9f65c'),('9b70267c-8e15-4f5f-ab05-208b9814cebb','0E1A4E0F39484456BB2BDE902B4BC275','5a1e0d97-07b6-4b3a-8e51-80893dbad277'),('9c745a5c-8913-4fdf-bc6c-322b2265c259','35E6CE83F2C242838BF00563E2B1A3AE','cas'),('9dae04c0-f653-4d6b-87a4-1a0d76873d1c','1DF53A025E944C4C95DB5D330083DDB9','33d21613-29a4-bf56-f8b9-0147168b7d20'),('a24e83c60cc4b6c0000801402b187987','0E1A4E0F39484456BB2BDE902B4BC275','483679ad60624d48899e73faa27762f8'),('a27d8717-4c14-4076-bd19-660177e2a98a','10064180F8AC4492B30CBC9F8972C3D7','33d21613-29a4-bf56-f8b9-0147168b7d20'),('a5e575b5-53d5-4cb4-a36e-591f4aa87c7e','0E415B0F253D45B0B752631A0D41D94F','b32834accb544ea7a9a09dcae4a36403'),('a9006883-c143-403c-bc6b-97758c6f9ae3','2D7A79F2889347B781AF3F162B6CAF8B','a40388d23cea4c5ba93bed865b81d255'),('adef03e1-bd05-4c64-a867-13a3610222cc','0E1A4E0F39484456BB2BDE902B4BC275','b8860cc4-b381-48a8-9459-0918c3ab2485'),('afa043d9-bc5c-4d9f-af23-c2e7e878d710','0E415B0F253D45B0B752631A0D41D94F','c1cabfaeb9a448028ffab2148da9f65c'),('b17c6960-bc51-4246-b100-734a750990a3','2D7A79F2889347B781AF3F162B6CAF8B','41065fe3-ae67-4172-a460-fd0079e88294'),('b2c16f6f-68a8-4374-bde9-03cf30a16374','ALL_USER_GROUP','850379a1-7923-4f6b-90be-d363b2dfd2ca'),('b5fa0a8e-393f-451b-a4b2-f5732c7a4729','DE1700C5A51F472D8EC31A36BB0FFF06','5a1e0d97-07b6-4b3a-8e51-80893dbad277'),('b704282f-feb6-46d1-b981-23ff5c6d16bf','016e3a86-3181-4126-8e00-acb2e234bb58','525d261fa3b04d19af0debabbd5a1e2d'),('b79a6f1b-3f3f-45b0-b9d2-65917a6196e6','0E415B0F253D45B0B752631A0D41D94F','69DB747E5A464ECDA5E27C5C56C4006C'),('bb52639a-c574-411c-bd77-d9a85732ccf5','0E1A4E0F39484456BB2BDE902B4BC275','b8860cc4-b381-48a8-9459-0918c3ab2485'),('bccdda70-2849-4fb7-a537-b00112ad9442','0E1A4E0F39484456BB2BDE902B4BC275','e707c852-29a4-bf56-f8b9-014716850d89'),('be0f52d6-979b-4682-9379-06c1998a85e6','0E1A4E0F39484456BB2BDE902B4BC275','5a1e0d97-07b6-4b3a-8e51-80893dbad277'),('c3f19655-d42d-49bf-a28c-b5fd8909dec2','null','cas'),('c8c56e54-8649-49c7-975f-fd365b2415c6','0E1A4E0F39484456BB2BDE902B4BC275','abed57cada2a4b358762f914f0df71e9'),('ce77c3520cc4b6c0000801402b1878cb','0E1A4E0F39484456BB2BDE902B4BC275','0b6765163d3d4600a35057dc5e64d906'),('d376c691-44bf-4c9f-b5bd-f5d56a5834e6','016e3a86-3181-4126-8e00-acb2e234bb58','3f57d0b2-99ab-4e66-a938-718befb55369'),('e03e9984-6584-485f-8602-8222680d9b30','ALL_USER_GROUP','c3d44bb1-e2c4-45dd-91ce-43e821f1321c'),('e22641d1-02a7-4b37-8752-d117a98ffd4a','1DF53A025E944C4C95DB5D330083DDB9','38c8a544eaa04aaeaa49d9c77ace40cd'),('e42f333f-639a-498e-a6e0-f552897704df','2D7A79F2889347B781AF3F162B6CAF8B','7610cb37255842f892a2bf8bc822cb49'),('e518fa71-a07f-4947-be87-419ba500cd68','2D7A79F2889347B781AF3F162B6CAF8B','d623598669d348bca999ab94c73eead6'),('e63184b1-5126-4016-8a10-68b22c389e63','10064180F8AC4492B30CBC9F8972C3D7','363b7606ff644c04a3c7a848d2d32247'),('eb217090-eb60-45bc-a85b-de68a90a7929','ALL_USER_GROUP','fe86db85-5475-4494-b5aa-dbd3b886ff64'),('ec745945-a10f-4a87-af35-16adfb85b9d5','ALL_USER_GROUP','a08d486a-2007-4436-aeda-4310e9443ec7'),('ed8c1917-31e1-444d-bbb8-02697df96dea','6b612583-a9c7-45af-9d81-72acd3417052','3f57d0b2-99ab-4e66-a938-718befb55369'),('f32865e2-9269-4f79-a309-2e6342910116','ALL_USER_GROUP','c8038bd4-12a4-4b45-9d43-61b3ecdc2eb4'),('f427e1a8-7761-480a-a1c4-5cf124bb1be4','ALL_USER_GROUP','e6c21b8c-87f9-461e-918e-5af6cbc57694'),('f43e4dd0-4b9d-4482-b592-4fcb00a9c3c2','2D7A79F2889347B781AF3F162B6CAF8B','33d21613-29a4-bf56-f8b9-0147168b7d20'),('f4866d00-2012-4313-9906-ef94454fd910','ALL_USER_GROUP','78917a82-1c86-4020-b86a-3b1b350357e3'),('f5efde85-a6ed-429c-9d47-c011d65571fb','0E1A4E0F39484456BB2BDE902B4BC275','95ab7aec-782d-480f-8b86-74a851586b3d'),('f675b13f-748b-4ad4-b2d6-1539b5cd0752','0E1A4E0F39484456BB2BDE902B4BC275','d623598669d348bca999ab94c73eead6'),('f94ee98a-8285-49a9-a249-14af5772e231','0E1A4E0F39484456BB2BDE902B4BC275','95ab7aec-782d-480f-8b86-74a851586b3d'),('f9978fd3-2154-4839-96b8-a1fd8b909d3c','2D7A79F2889347B781AF3F162B6CAF8B','b32834accb544ea7a9a09dcae4a36403'),('fb9256eb-b5cf-4bf1-b6a3-d9be7b9d5f91','0E1A4E0F39484456BB2BDE902B4BC275','c11a96e39c744583a10293b44c415f55'),('fe2ae10e-a69f-44a9-af9e-0ee6ca547713','ALL_USER_GROUP','ae20330a-ef0b-4dad-9f10-d5e3485ca2ad'),('fe2ae10e-a69f-44a9-af9e-0ee6ca5477a4','ALL_USER_GROUP','41065fe3-ae67-4172-a460-fd0079e88294'),('fe2ae10e-a69f-44a9-af9e-0ee6ca5477ab','2D7A79F2889347B781AF3F162B6CAF8B','363b7606ff644c04a3c7a848d2d32247');
/*!40000 ALTER TABLE `group_privileges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `groups` (
  `ID` varchar(45) NOT NULL COMMENT 'Ã¤Â¸Â»Ã©Â”Â®',
  `NAME` varchar(100) DEFAULT NULL COMMENT 'Ã¥ÂºÂ”Ã§Â”Â¨Ã¨Â§Â’Ã¨Â‰Â²Ã¥ÂÂ',
  `STATUS` tinyint(3) unsigned DEFAULT NULL COMMENT 'Ã§ÂŠÂ¶Ã¦Â€Â',
  `CREATEDBY` varchar(45) DEFAULT NULL COMMENT 'Ã¥ÂˆÂ›Ã¥Â»ÂºÃ¤ÂºÂº',
  `ISDEFAULT` tinyint(3) unsigned DEFAULT NULL COMMENT 'Ã©Â»Â˜Ã¨Â®Â¤Ã¨Â§Â’Ã¨Â‰Â²',
  `CREATEDDATE` date DEFAULT NULL COMMENT 'Ã¥ÂˆÂ›Ã¥Â»ÂºÃ¦Â—Â¶Ã©Â—Â´',
  `MODIFIEDBY` varchar(45) DEFAULT NULL COMMENT 'Ã¤Â¿Â®Ã¦Â”Â¹Ã¤ÂºÂº',
  `MODIFIEDDATE` date DEFAULT NULL COMMENT 'Ã¤Â¿Â®Ã¦Â”Â¹Ã¦Â—Â¶Ã©Â—Â´',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Ã¥ÂºÂ”Ã§Â”Â¨Ã¨Â§Â’Ã¨Â‰Â²Ã¨Â¡Â¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` VALUES ('35E6CE83F2C242838BF00563E2B1A3AE','role1',1,'admin',0,'2012-07-11',NULL,NULL),('7BA5E4713E994E3BBC11BA642DF988BF','CRMè§’è‰²',1,'admin',0,'2012-06-21','7BF5315CA1004CDB8E614B0361C4D46A','2012-06-26'),('87B69209FEEE4BDE9E7377AAB28B5B76','role2',1,'admin',0,'2012-07-11',NULL,NULL),('AB3400CE12D640618DAEAEBEBA52B8BD','HRç³»ç»Ÿè§’è‰²',1,'admin',0,'2012-06-21',NULL,NULL),('ALL_USER_GROUP','æ‰€æœ‰è®¤è¯ç”¨æˆ·ç»„',1,NULL,1,NULL,NULL,NULL),('DE1700C5A51F472D8EC31A36BB0FFF06','OAè§’è‰²',1,'admin',1,'2012-06-21',NULL,NULL);
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_login`
--

DROP TABLE IF EXISTS `history_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `history_login` (
  `ID` varchar(45) NOT NULL COMMENT 'Ã¤Â¸Â»Ã©Â”Â®',
  `USERNAME` varchar(200) NOT NULL COMMENT 'Ã§Â™Â»Ã¥Â½Â•Ã¥ÂÂ',
  `DISPLAYNAME` varchar(45) DEFAULT NULL COMMENT 'Ã¥Â§Â“Ã¥ÂÂ',
  `MESSAGE` varchar(200) DEFAULT NULL COMMENT 'Ã¦Â¶ÂˆÃ¦ÂÂ¯',
  `SOURCEIP` varchar(45) DEFAULT NULL COMMENT 'Ã¦Â“ÂÃ¤Â½ÂœIP',
  `LOGINTIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Ã¨Â®Â¤Ã¨Â¯ÂÃ¦Â—Â¶Ã©Â—Â´',
  `LOGINTYPE` varchar(45) DEFAULT NULL COMMENT 'Ã§Â±Â»Ã¥ÂžÂ‹',
  `UID` varchar(45) NOT NULL COMMENT 'Ã§Â”Â¨Ã¦ÂˆÂ·ID',
  `CODE` varchar(45) DEFAULT NULL COMMENT 'Ã§Â¼Â–Ã§Â Â',
  `PROVIDER` varchar(45) DEFAULT NULL COMMENT 'Ã§Â¬Â¬Ã¤Â¸Â‰Ã¦Â–Â¹',
  `SESSIONID` varchar(45) DEFAULT NULL COMMENT 'Ã¤Â¼ÂšÃ¨Â¯Â',
  `BROWSER` varchar(45) DEFAULT NULL COMMENT 'Ã¦ÂµÂÃ¨Â§ÂˆÃ¥Â™Â¨Ã§Â‰ÂˆÃ¦ÂœÂ¬',
  `PLATFORM` varchar(45) DEFAULT NULL COMMENT 'Ã¥Â¹Â³Ã¥ÂÂ°',
  `APPLICATION` varchar(45) DEFAULT NULL COMMENT 'Ã¥ÂºÂ”Ã§Â”Â¨Ã§Â¨Â‹Ã¥ÂºÂ',
  `LOGINURL` varchar(450) DEFAULT NULL COMMENT 'Ã§Â™Â»Ã¥Â½Â•URL',
  `LOGOUTTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Ã§Â™Â»Ã¥Â‡ÂºÃ¦Â—Â¶Ã©Â—Â´',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Ã§Â™Â»Ã¥Â½Â•Ã¦Â—Â¥Ã¥Â¿Â—Ã¨Â¡Â¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_login`
--

LOCK TABLES `history_login` WRITE;
/*!40000 ALTER TABLE `history_login` DISABLE KEYS */;
INSERT INTO `history_login` VALUES ('11a6dfc7-5d36-43fe-bf6c-9dccf6f1dd12','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 14:05:08','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','03e25597-33e1-43d1-80b7-37be15bdecf5','Chrome/78','Windows NT 10.0','Browser','2019-11-09 22:05:08','2019-11-09 14:23:38'),('12e7120a-c7e1-45fe-8d74-d12e95d5880d','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-04 14:45:03','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','f3691728-4580-4a4c-b204-4c96102a404e','Chrome/77','Windows NT 10.0','Browser','2019-11-04 22:45:03','0000-00-00 00:00:00'),('14db82b0-d2b2-4bf3-b4db-b62093f89d23','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-09 01:02:47','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','3fc6ee3b-5910-4e87-b504-5b5621fa48d4','Chrome/78','Windows NT 10.0','Browser','2019-11-09 09:02:47','0000-00-00 00:00:00'),('1937c1bd-ff1a-46e4-8307-7bcac1022139','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-09 10:16:48','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','c78f3ac8-2eae-4733-b7a8-01d74908e021','Chrome/78','Windows NT 10.0','Browser','2019-11-09 18:16:48','2019-11-09 10:28:45'),('1b43ea5f-7d68-4111-831f-f2b969ae25eb','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-06 14:58:26','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','98ef9cb6-ba1b-41ff-bf40-a2cb2d63b0c0','Chrome/77','Windows NT 10.0','Browser','2019-11-06 22:58:26','0000-00-00 00:00:00'),('2186d5f9-e0d1-4e75-845d-85e738aced91','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 14:48:25','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','d68ae4c5-b749-4ccd-bb18-e362c9db329a','Chrome/78','Windows NT 10.0','Browser','2019-11-09 22:48:25','0000-00-00 00:00:00'),('2402a0d9-11ae-4fe8-81e0-bd58e331bb9f','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-30 15:00:28','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','06e55836-4fd5-4f66-b4c5-653b4f793119','Chrome/77','Windows NT 10.0','Browser','2019-10-30 23:00:28','0000-00-00 00:00:00'),('24ede113-b483-44a0-8a55-9b51ab364051','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-07 14:33:02','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','b668efa3-8423-4ed6-95d4-c38414a50491','Chrome/77','Windows NT 10.0','Browser','2019-11-07 22:33:02','0000-00-00 00:00:00'),('2b110937-a553-4edf-8f40-325b7bed1d7f','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-06 14:53:01','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','ca5d7ad6-1a9c-426a-bb85-9394a1940a0f','Chrome/77','Windows NT 10.0','Browser','2019-11-06 22:53:01','0000-00-00 00:00:00'),('3077aae7-6298-4234-99ec-0f8390d06ebc','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-08 14:46:30','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','8854f632-873d-4108-935e-2006cc23f7f0','Chrome/78','Windows NT 10.0','Browser','2019-11-08 22:46:30','0000-00-00 00:00:00'),('4086d31a-94a0-4c28-9dbe-ee345f730562','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-06 15:28:25','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','2e0c997f-6ce2-409b-b8a7-8d99fcd1ce66','Chrome/77','Windows NT 10.0','Browser','2019-11-06 23:28:25','0000-00-00 00:00:00'),('41a7c0a7-1203-4fca-91c7-ee434f31b12a','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-09 10:04:38','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','37cce053-b022-4331-b110-b808472f4ca3','Chrome/78','Windows NT 10.0','Browser','2019-11-09 18:04:38','0000-00-00 00:00:00'),('43a61c35-3e1d-48da-934a-a5f344ad58be','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-20 14:47:29','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','881e00e9-0047-4661-b7b2-f76dbd3a46c9','Chrome/77','Windows NT 10.0','Browser','2019-10-20 22:47:29','0000-00-00 00:00:00'),('4bbfcdce-efd2-4770-a501-3c7b87bbc518','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-21 15:46:44','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','19b2a9e4-94fc-4c8a-826b-ac20bab14e2d','Chrome/77','Windows NT 10.0','Browser','2019-10-21 23:46:44','0000-00-00 00:00:00'),('5179d964-fa8b-4a28-b286-8e6816771848','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-04 15:46:04','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','a285178a-2672-45a5-841c-bc20040ca16c','Chrome/77','Windows NT 10.0','Browser','2019-11-04 23:46:04','0000-00-00 00:00:00'),('54a63b5b-0000-4699-8cd5-30aed4d8fba5','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-07 13:30:38','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','afe35752-19ed-4140-9042-16123678d594','Chrome/77','Windows NT 10.0','Browser','2019-11-07 21:30:38','2019-11-07 14:15:55'),('5d126fbb-20ba-4035-9306-83ff24f711ab','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-30 14:54:46','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','3b4de92b-948a-4b42-9d5d-2803216c62dd','Chrome/77','Windows NT 10.0','Browser','2019-10-30 22:54:46','0000-00-00 00:00:00'),('64900db2-f2d9-49a7-9845-852ac8708c28','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-09 01:06:05','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','0fc5e1e5-4bf3-4a4e-9d52-77e2a84bfb71','Chrome/78','Windows NT 10.0','Browser','2019-11-09 09:06:05','0000-00-00 00:00:00'),('6bc27d76-afff-4721-98b5-cc86589b257a','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 10:58:59','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','b4b9d103-a850-499c-9ec5-7e4d5e1b677e','Chrome/78','Windows NT 10.0','Browser','2019-11-09 18:58:59','0000-00-00 00:00:00'),('6c4b7032-327a-4114-af59-903342143026','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-29 15:00:11','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','262ee673-8ac3-4e7e-8344-d216ff5994ab','Chrome/77','Windows NT 10.0','Browser','2019-10-29 23:00:11','0000-00-00 00:00:00'),('77397d99-865b-4ee9-974e-013a0fc2a7dc','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 10:45:00','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','dfc2eb06-bbbc-4959-a8b1-aedb35686fdc','MSIE/ Touch','Windows NT 10.0','Browser','2019-11-09 18:45:00','0000-00-00 00:00:00'),('7e3d48cc-602c-464a-bd85-cf86d61a57ea','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-20 14:44:23','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','7db4e942-dfc4-4539-bf0b-317aea98222c','Chrome/77','Windows NT 10.0','Browser','2019-10-20 22:44:23','0000-00-00 00:00:00'),('7f438485-83b9-4174-802b-1bb33fcf44ae','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-09 02:28:45','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','1df9c283-01de-45a3-82a1-4dc1b4e7686d','Chrome/78','Windows NT 10.0','Browser','2019-11-09 10:28:45','2019-11-09 02:29:35'),('7f954cde-ce70-414b-8a5d-7c7404bfe791','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-09 03:28:22','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','d550de99-7ed4-4b39-b61f-186abe4ca271','Chrome/78','Windows NT 10.0','Browser','2019-11-09 11:28:22','0000-00-00 00:00:00'),('86304e15-610f-438a-8e36-0d934f7a6757','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-02 07:46:03','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','122e7048-6333-4b96-ac46-c989d85dfdc5','Chrome/77','Windows NT 10.0','Browser','2019-11-02 15:46:03','0000-00-00 00:00:00'),('90348d1e-1a60-4cf1-9dc2-d4bf35ad2edc','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 01:49:18','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','ba1efb04-f5ac-4e69-831b-d40952c134ac','Chrome/78','Windows NT 10.0','Browser','2019-11-09 09:49:18','0000-00-00 00:00:00'),('93c7501c-afcf-45be-94b4-caaeaf9ec74b','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 01:45:35','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','a24d4f30-5508-43d7-9e29-5dfd7b57320e','Chrome/78','Windows NT 10.0','Browser','2019-11-09 09:45:35','2019-11-09 01:49:00'),('93c7eafc-0a3c-4122-a04b-e59ee2afc861','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-04 14:02:09','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','b6dc6e15-bb91-4a36-899a-fab7e1b89709','Chrome/77','Windows NT 10.0','Browser','2019-11-04 22:02:09','0000-00-00 00:00:00'),('9672f460-5861-474d-b669-cfb7921d13eb','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-22 14:41:13','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','cbcd9c77-79e3-4b5d-a3ac-0a5dfcd0c854','Chrome/77','Windows NT 10.0','Browser','2019-10-22 22:41:13','0000-00-00 00:00:00'),('9f974c2f-f419-4f3e-ad48-52663b486061','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-20 15:19:59','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','9c277049-e9e4-4d0b-8b20-a8703458899c','Chrome/77','Windows NT 10.0','Browser','2019-10-20 23:19:59','0000-00-00 00:00:00'),('a0483e46-efc9-4660-a9e7-398df8a297f1','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-29 15:50:18','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','e837fd57-2efe-4627-bb85-fb3c087a9074','Chrome/77','Windows NT 10.0','Browser','2019-10-29 23:50:18','0000-00-00 00:00:00'),('a597d4a0-be6f-417f-bfad-1c781136e17e','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-04 15:52:32','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','bcdaf679-1e06-43da-b5c9-a30dd616c4e5','Chrome/77','Windows NT 10.0','Browser','2019-11-04 23:52:32','0000-00-00 00:00:00'),('a6b9651d-b0e5-4aed-82c7-416b8b5f92b1','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 10:43:25','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','fcedbcc7-3426-400e-bcf0-def47a14833d','Chrome/78','Windows NT 10.0','Browser','2019-11-09 18:43:25','0000-00-00 00:00:00'),('a7da5d68-1595-4525-8916-477a0cc63edf','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-02 07:28:11','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','f171642d-3a22-43d9-a378-71260a18e8e0','Chrome/77','Windows NT 10.0','Browser','2019-11-02 15:28:11','0000-00-00 00:00:00'),('a8d854f1-7dcb-4b0b-9d37-10f53f7e7637','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 03:18:13','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','c3d47b1c-f961-45b2-a909-c2a63ebfa28a','Chrome/78','Windows NT 10.0','Browser','2019-11-09 11:18:13','0000-00-00 00:00:00'),('aaf31480-ead5-40d6-9523-dbea71d50f7f','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-07 15:58:30','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','408da3a5-7544-4fb7-b970-f4a99868f047','Chrome/77','Windows NT 10.0','Browser','2019-11-07 23:58:30','0000-00-00 00:00:00'),('aec58000-a4ed-4546-b7a5-0959ac755834','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-02 11:04:05','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','c39868dc-7c0f-41e7-ac2c-b3ea93fc67c1','Chrome/77','Windows NT 10.0','Browser','2019-11-02 19:04:05','0000-00-00 00:00:00'),('b3b0fd79-de1f-48b2-a5e7-7f8f02b6f92e','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-02 08:40:09','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','227fa42e-74e6-4647-9ac7-1852008325f1','Chrome/77','Windows NT 10.0','Browser','2019-11-02 16:40:09','0000-00-00 00:00:00'),('b52a2c85-6241-4893-99a6-6a2a2f6e193d','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-05 15:13:02','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','8a9ae8de-16c1-46a3-9433-4693a4688651','Chrome/77','Windows NT 10.0','Browser','2019-11-05 23:13:02','0000-00-00 00:00:00'),('ba9ff748-03cf-4ba2-b6e8-aa62e43339d1','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-07 14:16:15','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','5dd5579a-7e72-4e4a-a525-cd18a99c77b9','Chrome/77','Windows NT 10.0','Browser','2019-11-07 22:16:15','2019-11-07 14:32:49'),('bb954985-9e87-4787-92f0-c239139a00d3','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-04 15:50:02','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','3a1e3bf5-2db8-40f6-9ad0-861c16790fc5','Chrome/77','Windows NT 10.0','Browser','2019-11-04 23:50:02','0000-00-00 00:00:00'),('bda7bd29-d60e-46a9-af25-2259767d7b52','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-06 14:26:42','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','93bac58b-091b-42f2-9747-daac6b33aaff','Chrome/77','Windows NT 10.0','Browser','2019-11-06 22:26:42','0000-00-00 00:00:00'),('c4f7de49-f335-4c85-9d98-9a7be8d32532','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-20 13:01:29','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','7db51f3a-108c-4cf4-b15b-97c32c63ab56','Chrome/77','Windows NT 10.0','Browser','2019-10-20 21:01:29','0000-00-00 00:00:00'),('c54a4b2a-b0a9-4da4-abf4-0e62596cbac2','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-23 15:27:19','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','e64124dc-54fd-4104-9f5c-fd03710bd3f5','Chrome/77','Windows NT 10.0','Browser','2019-10-23 23:27:19','0000-00-00 00:00:00'),('c6a08bdf-33df-4f66-bd17-98c9ddb63f98','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-30 15:14:03','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','2934dcbc-1501-4ff0-8fd9-327c954bc7df','Chrome/77','Windows NT 10.0','Browser','2019-10-30 23:14:03','0000-00-00 00:00:00'),('ccd0028a-815d-4479-b21e-ca80fe91b518','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-20 12:41:03','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','9c5da3b6-97e4-4239-b5f8-8990a9e3b9e4','Chrome/77','Windows NT 10.0','Browser','2019-10-20 20:41:03','0000-00-00 00:00:00'),('d17ee2ef-6756-4134-a2ca-cd28ddb815e6','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-07 16:00:20','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','89c1b5eb-9810-457e-b693-0ec7cf0a599b','Chrome/77','Windows NT 10.0','Browser','2019-11-08 00:00:20','0000-00-00 00:00:00'),('d4d748a0-dcac-49a6-938b-f6ef945180a3','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 02:29:48','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','c3a9b83c-bc9a-4f08-8580-0ed476042bfb','Chrome/78','Windows NT 10.0','Browser','2019-11-09 10:29:48','0000-00-00 00:00:00'),('dd022de9-042e-487c-b250-c784c9fff4b0','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-08 15:10:33','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','b64311b4-c64a-4a74-88af-890e87af4a41','Chrome/78','Windows NT 10.0','Browser','2019-11-08 23:10:33','0000-00-00 00:00:00'),('e1c4cc13-3ebe-41ea-b651-cd971a2ed3bd','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 10:29:06','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','ad186813-1c49-491a-b632-5576e91955b1','Chrome/78','Windows NT 10.0','Browser','2019-11-09 18:29:06','0000-00-00 00:00:00'),('e1ee0549-5adc-4a27-b4e4-f7ddb1007251','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-09 01:40:59','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','4e7e9212-75ba-4655-9da5-5737b65cfae6','Chrome/78','Windows NT 10.0','Browser','2019-11-09 09:40:59','0000-00-00 00:00:00'),('e2e8c05a-a649-40a8-8a8a-4fe6a1d6efc8','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-09 03:33:23','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','6e797edf-faef-4f29-988b-c99d628195a6','Chrome/78','Windows NT 10.0','Browser','2019-11-09 11:33:23','0000-00-00 00:00:00'),('f03e8e5b-5613-4e77-b69d-04c3df77e773','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-07 15:54:57','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','71b6b7f9-2b7f-4a2d-81e3-c40e244cb38d','Chrome/77','Windows NT 10.0','Browser','2019-11-07 23:54:57','0000-00-00 00:00:00'),('f3d3e385-aac0-4f3a-a122-4c0c5e17691a','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-02 08:28:25','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','26d1afd0-1741-470a-a558-be7532bebe3c','Chrome/77','Windows NT 10.0','Browser','2019-11-02 16:28:25','0000-00-00 00:00:00'),('fb55c52a-c315-4fb3-a878-1652f67a8373','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-08 14:29:24','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','eda90658-e76d-4ba7-b2b7-28729f9b1c6d','Chrome/78','Windows NT 10.0','Browser','2019-11-08 22:29:24','0000-00-00 00:00:00'),('fc6f5e6f-3536-4eb9-9929-737233875f9d','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-31 15:01:26','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','dc680e9d-83d8-4a48-b92b-73bdc8671fc1','Chrome/77','Windows NT 10.0','Browser','2019-10-31 23:01:26','0000-00-00 00:00:00');
/*!40000 ALTER TABLE `history_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_login_apps`
--

DROP TABLE IF EXISTS `history_login_apps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `history_login_apps` (
  `ID` varchar(45) NOT NULL COMMENT 'Ã¤Â¸Â»Ã©Â”Â®',
  `SESSIONID` varchar(45) DEFAULT NULL COMMENT 'Ã¤Â¼ÂšÃ¨Â¯Â',
  `LOGINTIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Ã¥ÂÂ•Ã§Â‚Â¹Ã§Â™Â»Ã¥Â½Â•Ã¦Â—Â¶Ã©Â—Â´',
  `APPID` varchar(45) NOT NULL COMMENT 'Ã¥ÂºÂ”Ã§Â”Â¨ID',
  `APPNAME` varchar(45) DEFAULT NULL,
  `UID` varchar(45) DEFAULT NULL,
  `USERNAME` varchar(45) DEFAULT NULL,
  `DISPLAYNAME` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Ã¥ÂÂ•Ã§Â‚Â¹Ã§Â™Â»Ã¥Â½Â•Ã¦Â—Â¥Ã¥Â¿Â—Ã¨Â¡Â¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_login_apps`
--

LOCK TABLES `history_login_apps` WRITE;
/*!40000 ALTER TABLE `history_login_apps` DISABLE KEYS */;
INSERT INTO `history_login_apps` VALUES ('097f9281-0511-4cf4-b484-0ca38db03088','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:13:07','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('1','1','2019-10-30 15:04:37','1','1','1','1','1'),('1d397fc8-23dc-4c86-9458-00b0165e46ae','eda90658-e76d-4ba7-b2b7-28729f9b1c6d','2019-11-08 14:34:08','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('1da9367a-2dd9-4b84-bb05-5c7b382bb2a5','0fc5e1e5-4bf3-4a4e-9d52-77e2a84bfb71','2019-11-09 01:21:08','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('27900d59-9b97-4022-8ab1-e7a8842264a5','dfc2eb06-bbbc-4959-a8b1-aedb35686fdc','2019-11-09 10:45:07','c1cabfaeb9a448028ffab2148da9f65c','QQ Login','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('2e498dde-f525-4123-aee0-bf7df0e3e8c9','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:17:47','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('430ba289-01c0-4a75-b0ea-7ec49c507ac9','fcedbcc7-3426-400e-bcf0-def47a14833d','2019-11-09 10:43:28','525d261fa3b04d19af0debabbd5a1e2d','SalesForce     ','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('438fcf0c-cc6f-4349-be81-13e3ca211664','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:10:40','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('647e277d-433f-463c-a0a6-98930d0becd1','eda90658-e76d-4ba7-b2b7-28729f9b1c6d','2019-11-08 14:29:37','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('6d697814-f33d-4e52-819b-df230e9e795b','8854f632-873d-4108-935e-2006cc23f7f0','2019-11-08 14:50:12','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('75230872-b6e3-4334-9998-8efdf845d219','0fc5e1e5-4bf3-4a4e-9d52-77e2a84bfb71','2019-11-09 01:06:09','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('76028fc0-65db-4f14-83b7-e13497b8d48d','0fc5e1e5-4bf3-4a4e-9d52-77e2a84bfb71','2019-11-09 01:25:20','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('797b9202-1ddc-44eb-b84b-4df41ef98f63','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:48:31','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('7a329e91-bf7c-47b4-ac3a-ad428f37a4bd','3fc6ee3b-5910-4e87-b504-5b5621fa48d4','2019-11-09 01:02:52','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('7b2356f4-197c-4afc-a6f2-9f02a1082817','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:18:19','c8038bd4-12a4-4b45-9d43-61b3ecdc2eb4','æœ‰é“äº‘ç¬”è®°','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('8754b8d4-33c4-418b-9440-ec2a68a1487b','fcedbcc7-3426-400e-bcf0-def47a14833d','2019-11-09 10:44:01','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('8b340426-2cb6-4219-a530-70181777ed30','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:44:57','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('93e012fa-0c33-4b00-ac85-7cf0ef2d1468','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:46:16','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('94aeb5db-ccad-42e2-a51c-b916b59b02ec','dfc2eb06-bbbc-4959-a8b1-aedb35686fdc','2019-11-09 10:45:11','c1cabfaeb9a448028ffab2148da9f65c','QQ Login','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('aab7c33d-3ea9-4398-97ee-7e0b63e01739','8854f632-873d-4108-935e-2006cc23f7f0','2019-11-08 14:46:37','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('b4312fc6-811d-41d5-ae3a-268e3a4c9829','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:18:10','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('b6f70f47-528d-4a73-bbb5-de0b76db4b65','0fc5e1e5-4bf3-4a4e-9d52-77e2a84bfb71','2019-11-09 01:26:26','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('c64f34ba-7d6e-45e2-b946-369ec361b0bc','c3d47b1c-f961-45b2-a909-c2a63ebfa28a','2019-11-09 03:25:58','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('dfc32176-616c-481a-98bf-1651a9286ec2','0fc5e1e5-4bf3-4a4e-9d52-77e2a84bfb71','2019-11-09 01:23:55','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('eaa39588-78a8-477a-a2ae-bcbd40152624','0fc5e1e5-4bf3-4a4e-9d52-77e2a84bfb71','2019-11-09 01:08:27','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('f332a0fd-bc87-4639-ae1d-f4894cd95c8c','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:45:17','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('f336631d-2d4b-4d1c-8708-6bfc5de5a439','8854f632-873d-4108-935e-2006cc23f7f0','2019-11-08 14:49:53','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('f3d9ba18-370e-48e5-9723-092cbf613413','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:43:41','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('fee87158-83e9-4e7a-bcba-476d4f32b7fb','0fc5e1e5-4bf3-4a4e-9d52-77e2a84bfb71','2019-11-09 01:28:48','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜');
/*!40000 ALTER TABLE `history_login_apps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_logs`
--

DROP TABLE IF EXISTS `history_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `history_logs` (
  `ID` varchar(45) NOT NULL COMMENT 'Ã¤Â¸Â»Ã©Â”Â®',
  `SERVICENAME` varchar(100) DEFAULT NULL COMMENT 'Ã¤Â¸ÂšÃ¥ÂŠÂ¡Ã¥ÂÂÃ§Â§Â°',
  `MESSAGE` varchar(200) DEFAULT NULL COMMENT 'Ã¦Â¶ÂˆÃ¦ÂÂ¯',
  `OPERATETYPE` varchar(45) DEFAULT NULL COMMENT 'Ã¦Â“ÂÃ¤Â½ÂœÃ§Â±Â»Ã¥ÂžÂ‹',
  `CONTENT` text COMMENT 'Ã¦Â“ÂÃ¤Â½ÂœÃ¥Â†Â…Ã¥Â®Â¹Ã¯Â¼ÂˆJSONÃ¯Â¼Â‰',
  `CREATEDDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Ã¦Â“ÂÃ¤Â½ÂœÃ¦Â—Â¶Ã©Â—Â´',
  `CREATEDBY` varchar(45) DEFAULT NULL COMMENT 'Ã¦Â“ÂÃ¤Â½ÂœÃ¤ÂºÂº',
  `MESSAGETYPE` varchar(45) DEFAULT NULL COMMENT 'Ã¦Â¶ÂˆÃ¦ÂÂ¯Ã§Â±Â»Ã¥ÂžÂ‹',
  `TNAME` varchar(45) DEFAULT NULL COMMENT 'Ã¤Â¼ÂÃ¤Â¸ÂšÃ§Â®Â€Ã§Â§Â°',
  `USERNAME` varchar(45) DEFAULT NULL COMMENT 'Ã§Â™Â»Ã¥Â½Â•Ã¥ÂÂ',
  `CODE` varchar(45) DEFAULT NULL COMMENT 'Ã¦Â—Â¥Ã¥Â¿Â—Ã§Â¼Â–Ã§Â Â',
  `TID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Ã¦Â“ÂÃ¤Â½ÂœÃ¦Â—Â¥Ã¥Â¿Â—Ã¨Â¡Â¨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_logs`
--

LOCK TABLES `history_logs` WRITE;
/*!40000 ALTER TABLE `history_logs` DISABLE KEYS */;
INSERT INTO `history_logs` VALUES ('1','1',NULL,NULL,NULL,'2019-10-30 15:04:49',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `history_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ipaddrfilter`
--

DROP TABLE IF EXISTS `ipaddrfilter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ipaddrfilter` (
  `ID` varchar(45) NOT NULL,
  `IPADDR` varchar(45) DEFAULT NULL,
  `FILTER` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(45) DEFAULT NULL,
  `STATUS` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ipaddrfilter`
--

LOCK TABLES `ipaddrfilter` WRITE;
/*!40000 ALTER TABLE `ipaddrfilter` DISABLE KEYS */;
/*!40000 ALTER TABLE `ipaddrfilter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `one_time_password`
--

DROP TABLE IF EXISTS `one_time_password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `one_time_password` (
  `ID` varchar(45) NOT NULL,
  `OPTTYPE` tinyint(3) unsigned DEFAULT '1',
  `USERNAME` varchar(45) NOT NULL,
  `TOKEN` varchar(45) NOT NULL,
  `RECEIVER` varchar(45) DEFAULT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `STATUS` tinyint(3) unsigned DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `one_time_password`
--

LOCK TABLES `one_time_password` WRITE;
/*!40000 ALTER TABLE `one_time_password` DISABLE KEYS */;
/*!40000 ALTER TABLE `one_time_password` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizations`
--

DROP TABLE IF EXISTS `organizations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organizations` (
  `ID` varchar(45) NOT NULL,
  `NAME` varchar(200) NOT NULL,
  `PID` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `STATUS` tinyint(3) unsigned DEFAULT NULL,
  `CREATEDBY` varchar(45) DEFAULT NULL,
  `CREATEDDATE` datetime DEFAULT NULL,
  `MODIFIEDBY` varchar(45) DEFAULT NULL,
  `MODIFIEDDATE` datetime DEFAULT NULL,
  `ADDRESS` varchar(200) DEFAULT NULL,
  `POSTALCODE` varchar(45) DEFAULT NULL,
  `PHONE` varchar(200) DEFAULT NULL,
  `FAX` varchar(200) DEFAULT NULL,
  `SORTORDER` int(10) unsigned DEFAULT '0',
  `FULLNAME` varchar(100) DEFAULT NULL,
  `TYPE` varchar(45) DEFAULT NULL,
  `XPATH` varchar(500) DEFAULT NULL,
  `LEVEL` int(10) unsigned DEFAULT NULL,
  `PNAME` varchar(45) DEFAULT NULL,
  `DIVISION` varchar(45) DEFAULT NULL,
  `COUNTRY` varchar(45) DEFAULT NULL,
  `REGION` varchar(45) DEFAULT NULL,
  `LOCALITY` varchar(45) DEFAULT NULL,
  `STREET` varchar(45) DEFAULT NULL,
  `HASCHILD` varchar(45) DEFAULT NULL,
  `CONTACT` varchar(45) DEFAULT NULL,
  `CODE` varchar(45) DEFAULT NULL,
  `EMAIL` varchar(45) DEFAULT NULL,
  `XNAMEPATH` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizations`
--

LOCK TABLES `organizations` WRITE;
/*!40000 ALTER TABLE `organizations` DISABLE KEYS */;
INSERT INTO `organizations` VALUES ('101','äº§å“éƒ¨','1','',1,'admin','2014-12-28 16:41:30',NULL,NULL,'','','','',1,'','','/101',NULL,'æµ·è¿žè½¯ä»¶','','','','','',NULL,'',NULL,'',NULL),('10101','äº‘èº«ä»½å®‰å…¨éƒ¨','101','',1,'admin','2014-12-28 16:50:10',NULL,NULL,'','','','',1,'','','/101/10101',NULL,'äº§å“éƒ¨','','','','','',NULL,'',NULL,'',NULL),('10102','IAMäº§å“éƒ¨','101','',1,'admin','2014-12-28 16:50:30',NULL,NULL,'','','','',1,'','','/101/10102',NULL,'äº§å“éƒ¨','','','','','',NULL,'',NULL,'',NULL),('10103','åŠ¨æ€ä»¤ç‰Œäº§å“éƒ¨','101','',1,'admin','2014-12-28 16:51:02',NULL,NULL,'','','','',1,'','','/101/10103',NULL,'äº§å“éƒ¨','','','','','',NULL,'',NULL,'',NULL),('102','é¡¹ç›®éƒ¨','1','',1,'admin','2014-12-28 16:41:46',NULL,NULL,'','','','',1,'','','/102',NULL,'æµ·è¿žè½¯ä»¶','','','','','',NULL,'',NULL,'',NULL),('10201','IAMé¡¹ç›®éƒ¨','102','',1,'admin','2014-12-28 16:49:18',NULL,NULL,'','','','',1,'','','/102/10201',NULL,'é¡¹ç›®éƒ¨','','','','','',NULL,'',NULL,'',NULL),('10202','Portalé¡¹ç›®éƒ¨','102','',1,'admin','2014-12-28 16:49:35',NULL,NULL,'','','','',1,'','','/102/10202',NULL,'é¡¹ç›®éƒ¨','','','','','',NULL,'',NULL,'',NULL),('10203','æŠ€æœ¯æ”¯æŒéƒ¨','102','',1,'admin','2014-12-28 16:52:45',NULL,NULL,'','','','',1,'','','/102/10203',NULL,'é¡¹ç›®éƒ¨','','','','','',NULL,'',NULL,'',NULL),('103','é”€å”®éƒ¨','1','',1,'admin','2014-12-28 16:42:41','admin','2015-06-11 14:07:10','','','','',1,'é”€å”®éƒ¨','','/103',NULL,'æµ·è¿žè½¯ä»¶','','','','','',NULL,'',NULL,'',''),('104','è´¢åŠ¡éƒ¨','1','',1,'admin','2014-12-28 16:42:56',NULL,NULL,'','','','',1,'','','/104',NULL,'æµ·è¿žè½¯ä»¶','','','','','',NULL,'',NULL,'',NULL),('105','è¡Œæ”¿éƒ¨','1','',1,'admin','2014-12-28 16:43:08','admin','2015-05-06 12:46:54','','','','',1,'','','/1/105',NULL,'æµ·è¿žè½¯ä»¶','','','','','',NULL,'',NULL,'',NULL),('10601','åŒ—äº¬åˆ†å…¬å¸','1','',1,'admin','2014-12-28 16:53:30',NULL,NULL,'','','','',1,'','','/10601',NULL,'æµ·è¿žè½¯ä»¶','','','','','',NULL,'',NULL,'',NULL),('1060101','äº§å“æ”¯æŒéƒ¨','10601','',1,'admin','2014-12-28 16:53:53',NULL,NULL,'','','','',1,'','','/10601/1060101',NULL,'åŒ—äº¬åˆ†å…¬å¸','','','','','',NULL,'',NULL,'',NULL),('1060102','é¡¹ç›®æœåŠ¡éƒ¨','10601','',1,'admin','2014-12-28 16:54:10',NULL,NULL,'','','','',1,'','','/10601/1060102',NULL,'åŒ—äº¬åˆ†å…¬å¸','','','','','',NULL,'',NULL,'',NULL),('107','å•†åŠ¡éƒ¨','1','',1,'admin','2015-06-11 14:08:04',NULL,NULL,'','','','',1,'å•†åŠ¡éƒ¨','','/1/107',NULL,'æŠ€å®‰ä¿¡æ¯','','','','','',NULL,'',NULL,'','/æŠ€å®‰ä¿¡æ¯/å•†åŠ¡éƒ¨');
/*!40000 ALTER TABLE `organizations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password_policy`
--

DROP TABLE IF EXISTS `password_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `password_policy` (
  `ID` varchar(45) NOT NULL,
  `MINLENGTH` tinyint(3) unsigned DEFAULT '0' COMMENT 'Ã¦ÂœÂ€Ã¥Â°ÂÃ©Â•Â¿Ã¥ÂºÂ¦',
  `MAXLENGTH` tinyint(3) unsigned DEFAULT '0' COMMENT 'Ã¦ÂœÂ€Ã¥Â¤Â§Ã©Â•Â¿Ã¥ÂºÂ¦',
  `LOWERCASE` tinyint(3) unsigned DEFAULT '0' COMMENT 'Ã¦ÂœÂ€Ã¥Â¤Â§Ã¦ÂœÂ‰Ã¦Â•ÂˆÃ¦ÂœÂŸ',
  `UPPERCASE` tinyint(3) unsigned DEFAULT '0' COMMENT 'Ã¦ÂœÂ€Ã¥Â°ÂÃ¦ÂœÂ‰Ã¦Â•ÂˆÃ¦ÂœÂŸ',
  `DIGITS` tinyint(3) unsigned DEFAULT '0' COMMENT 'Ã¥ÂŒÂ…Ã¥ÂÂ«Ã¦Â•Â°Ã¥Â­Â—Ã¤Â¸ÂªÃ¦Â•Â°',
  `SPECIALCHAR` tinyint(3) unsigned DEFAULT '0' COMMENT 'Ã¥ÂŒÂ…Ã¥ÂÂ«Ã¥Â°ÂÃ¥Â†Â™Ã¥Â­Â—Ã¦Â¯ÂÃ¤Â¸ÂªÃ¦Â•Â°',
  `ATTEMPTS` tinyint(3) unsigned DEFAULT '0' COMMENT 'Ã¥ÂŒÂ…Ã¥ÂÂ«Ã¥Â¤Â§Ã¥Â†Â™Ã¥Â­Â—Ã¦Â¯ÂÃ¤Â¸ÂªÃ¦Â•Â°',
  `DURATION` tinyint(3) unsigned DEFAULT '0' COMMENT 'Ã¨Â´Â¦Ã¥ÂÂ·Ã©Â”ÂÃ¥Â®ÂšÃ©Â˜Â€Ã¥Â€Â¼',
  `EXPIRATION` tinyint(3) unsigned DEFAULT '0' COMMENT 'Ã¨Â§Â£Ã©Â”ÂÃ¦Â—Â¶Ã©Â—Â´',
  `USERNAME` tinyint(3) unsigned DEFAULT '0' COMMENT 'Ã§Â‰Â¹Ã¦Â®ÂŠÃ¥Â­Â—Ã§Â¬Â¦Ã¤Â¸ÂªÃ¦Â•Â°',
  `SIMPLEPASSWORDS` varchar(600) DEFAULT '0' COMMENT 'Ã§ÂºÂ¦Ã¦ÂÂŸÃ¥ÂŒÂ…Ã¥ÂÂ«Ã§Â™Â»Ã¥Â½Â•Ã¥ÂÂ',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Ã¥Â¯Â†Ã§Â ÂÃ§Â­Â–Ã§Â•Â¥';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_policy`
--

LOCK TABLES `password_policy` WRITE;
/*!40000 ALTER TABLE `password_policy` DISABLE KEYS */;
INSERT INTO `password_policy` VALUES ('1',6,15,1,1,2,2,6,3,10,1,'password,password1,admin,123456,666666,888888,999999,12345678,abc123,123456789,111111,1234567,123123,1234567890,1234,12345,000000');
/*!40000 ALTER TABLE `password_policy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `remember_me`
--

DROP TABLE IF EXISTS `remember_me`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `remember_me` (
  `ID` varchar(40) NOT NULL,
  `USERNAME` varchar(50) NOT NULL,
  `AUTHKEY` varchar(200) NOT NULL,
  `LASTLOGIN` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `remember_me`
--

LOCK TABLES `remember_me` WRITE;
/*!40000 ALTER TABLE `remember_me` DISABLE KEYS */;
/*!40000 ALTER TABLE `remember_me` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saml_v20_metadata`
--

DROP TABLE IF EXISTS `saml_v20_metadata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saml_v20_metadata` (
  `ID` varchar(45) NOT NULL,
  `ORGNAME` varchar(100) NOT NULL,
  `ORGDISPLAYNAME` varchar(200) NOT NULL,
  `ORGURL` varchar(200) NOT NULL,
  `COMPANY` varchar(200) NOT NULL,
  `GIVENNAME` varchar(45) NOT NULL,
  `SURNAME` varchar(45) NOT NULL,
  `EMAILADDRESS` varchar(45) NOT NULL,
  `TELEPHONENUMBER` varchar(45) NOT NULL,
  `CONTACTTYPE` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saml_v20_metadata`
--

LOCK TABLES `saml_v20_metadata` WRITE;
/*!40000 ALTER TABLE `saml_v20_metadata` DISABLE KEYS */;
INSERT INTO `saml_v20_metadata` VALUES ('0f6635fc1008b6c000080140977f33ef','connSec','connSec','http://www.connsec.com','connSec','connSec','connSec','admin@connSec.com','4008981111','technical');
/*!40000 ALTER TABLE `saml_v20_metadata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socialsignon_users_token`
--

DROP TABLE IF EXISTS `socialsignon_users_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `socialsignon_users_token` (
  `ID` varchar(45) NOT NULL,
  `UID` varchar(45) NOT NULL COMMENT 'Ã§Â”Â¨Ã¦ÂˆÂ·id',
  `PROVIDER` varchar(45) NOT NULL COMMENT 'Ã§Â¬Â¬Ã¤Â¸Â‰Ã¦Â–Â¹Ã¦ÂÂÃ¤Â¾Â›Ã¥Â•Â†',
  `SOCIALUSERINFO` text NOT NULL COMMENT '''Ã§Â¬Â¬Ã¤Â¸Â‰Ã¦Â–Â¹Ã¥ÂºÂ”Ã§Â”Â¨Ã§ÂšÂ„Ã§Â”Â¨Ã¦ÂˆÂ·Ã¤Â¿Â¡Ã¦ÂÂ¯''',
  `SOCIALUID` varchar(100) NOT NULL COMMENT '''Ã§Â¬Â¬Ã¤Â¸Â‰Ã¦Â–Â¹Ã¥ÂºÂ”Ã§Â”Â¨Ã§ÂšÂ„Ã§Â”Â¨Ã¦ÂˆÂ·id''',
  `EXATTRIBUTE` text,
  `ACCESSTOKEN` text,
  `CREATEDDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATEDDATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `USERNAME` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Ã§Â”Â¨Ã¦ÂˆÂ·Ã¥Â’ÂŒÃ§Â¬Â¬Ã¤Â¸Â‰Ã¦Â–Â¹Ã¨Â®Â¤Ã¨Â¯ÂÃ¦ÂÂÃ¤Â¾Â›Ã¥Â•Â†Ã§ÂšÂ„Ã§Â»Â‘Ã¥Â®ÂšÃ¥Â…Â³Ã§Â³Â»';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socialsignon_users_token`
--

LOCK TABLES `socialsignon_users_token` WRITE;
/*!40000 ALTER TABLE `socialsignon_users_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `socialsignon_users_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sso_saml_v20_config`
--

DROP TABLE IF EXISTS `sso_saml_v20_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sso_saml_v20_config` (
  `ID` varchar(45) NOT NULL,
  `ISSUER` varchar(200) NOT NULL,
  `CERTIFICATE` blob NOT NULL,
  `KEYSTORE` blob NOT NULL,
  `CERTISSUER` varchar(200) NOT NULL,
  `CERTSUBJECT` varchar(200) NOT NULL,
  `CERTEXPIRATION` varchar(45) NOT NULL,
  `ENTITYID` varchar(200) NOT NULL,
  `NAMEIDFORMAT` varchar(45) NOT NULL,
  `CREATEDBY` varchar(45) DEFAULT NULL,
  `CREATEDDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIEDBY` varchar(45) DEFAULT NULL,
  `MODIFIEDDATE` datetime DEFAULT NULL,
  `STATUS` tinyint(3) unsigned NOT NULL,
  `VALIDITY_INTERVAL` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sso_saml_v20_config`
--

LOCK TABLES `sso_saml_v20_config` WRITE;
/*!40000 ALTER TABLE `sso_saml_v20_config` DISABLE KEYS */;
INSERT INTO `sso_saml_v20_config` VALUES ('sdaf','sdaf',_binary '0Â‚r0Â‚ZÂ >Â‰ÂºVc0\r	*Â†HÂ†Ã·\r\00O10	UCN10	USH10	USH10U\nconnsec10Uconnsec.com0\r130509143800Z\r130522063135Z0O10	UCN10	USH10	USH10U\nconnsec10Uconnsec.com0Â‚\"0\r	*Â†HÂ†Ã·\r\0Â‚\00Â‚\nÂ‚\0Â„[0u`35Ã”KJÃ©ÃªÃ¼Â§%Ã—Â‚Â¾ÂœÃ˜O6Â•zÃ§Â˜Â½Ã¹Â…Ã¶ÃºÃŸÃ“JÂ»Â”0Â£Ã®Ã±\0Â”Â”>Â€1Z\"ÂÂ£Â“ÃŠÃ¤Ã‚Ã¥UÃ»Ã¶	pVÃ¯vÃ¸ Ã¹%e\n23 ÃªlÂ¹\rG6Ã¹Â´ÃžÂ„Â’*Â£Â‹Â±Ã±MÃ”ÂŽ<Â„Ã¿@<Ã¨\0XÂ—Ã¤[Ã‘.ÃŽÃ´h5Ã©Â·Â¥Â¡ÂWÂ±Â¦Ã­N-A9ÂSÂ’Â’Â½ÃÂ³(,Â¦b#;Ã„\0Â¸Ã¦Â•)SMaÃ±C7Â8Â¿7Ã»JÃ­Â¤uÃ˜\"`Â 0ÃœÃ·Â€Ã»Ã§~ÃŸ#ÂœÃ›Ã½7ÃÂÃ›7XÃ Ã Ã©ÃaÂ¨p	dÂ¸Ã¥5Ã©ÃŠY`ÂÂ’Ã“,OhÃ¤<\nÂˆD>\\#Â°1Â®ÃžjÂ¹t{NÂuÂÃ¿Â…Â›\'=|ÂŠ8\\ÃºÃœ\0Â£T0R0UÃ¿0\00UÃ¿Â 0U%Ã¿0\n+0\ZU0Âconnsec@163.com0\r	*Â†HÂ†Ã·\r\0Â‚\06ÂŽ)3Â“Â¸ÂŒÃ 7Â£Â»Ã“ÂÂÂ› mÂ¨gÂ²0Ã Â±HÃ›eÃ¬Â†Ã‹Âš~ÃšBÃ¤Ã»YÂ“SÃ¶Â£Â¼Ã¦3:[Ã´ÃºÃ¸\'ÃŽÃ­Ã³ÃÃ‚/Â–Âª{	Â›Ã¦5.Ã¼Â¼Ã‹Â›Ã‘fÂ»ÃŽÃ‹Âµ,Ã·HÂŽ_Ã§zÃ‡Ã0Ã©Â§?Ã¨Â¹CÂ€Vf:ÂˆÃº]|pÂŒÃ˜SÂƒFÃ¾Ã¢Â¿Ã¼Ã†vÂŠÃ½MÃ½\0Â¡fÃœTÃŠRÃ¡Ã»%ÂŒ2cÃª?RÂ°Ã§qÂ€ÂŠÃ’ÃŸLÂ¹Ã¨Â”BÃ¸Ã‹Â§Â¦Ã´Ã€ÂžÂ¨Â·Â¶9Ã‹tQ&|Âµ&ÂŒÃ«`Â©oÂ·Â´Ãµx)Ãž&Â¬Ã¦b73Â¥|Â5Ã¼ÃºÂÃ‹i`Â‡Ã¯HOÃ•Â¤0Â»v\0ÃˆÂ¨ÂžÂªH)&~Y?;Â—Â„F)Ã¢Ã®iÃ°CxÂ“Â¥z$/;Â­',_binary 'Ã¾Ã­Ã¾Ã­\0\0\0\0\0\0\0\0\0\0sdf\0\0>Â™@Ã°ÂŽ\0X.509\0\0v0Â‚r0Â‚ZÂ >Â‰ÂºVc0\r	*Â†HÂ†Ã·\r\00O10	UCN10	USH10	USH10U\nconnsec10Uconnsec.com0\r130509143800Z\r130522063135Z0O10	UCN10	USH10	USH10U\nconnsec10Uconnsec.com0Â‚\"0\r	*Â†HÂ†Ã·\r\0Â‚\00Â‚\nÂ‚\0Â„[0u`35Ã”KJÃ©ÃªÃ¼Â§%Ã—Â‚Â¾ÂœÃ˜O6Â•zÃ§Â˜Â½Ã¹Â…Ã¶ÃºÃŸÃ“JÂ»Â”0Â£Ã®Ã±\0Â”Â”>Â€1Z\"ÂÂ£Â“ÃŠÃ¤Ã‚Ã¥UÃ»Ã¶	pVÃ¯vÃ¸ Ã¹%e\n23 ÃªlÂ¹\rG6Ã¹Â´ÃžÂ„Â’*Â£Â‹Â±Ã±MÃ”ÂŽ<Â„Ã¿@<Ã¨\0XÂ—Ã¤[Ã‘.ÃŽÃ´h5Ã©Â·Â¥Â¡ÂWÂ±Â¦Ã­N-A9ÂSÂ’Â’Â½ÃÂ³(,Â¦b#;Ã„\0Â¸Ã¦Â•)SMaÃ±C7Â8Â¿7Ã»JÃ­Â¤uÃ˜\"`Â 0ÃœÃ·Â€Ã»Ã§~ÃŸ#ÂœÃ›Ã½7ÃÂÃ›7XÃ Ã Ã©ÃaÂ¨p	dÂ¸Ã¥5Ã©ÃŠY`ÂÂ’Ã“,OhÃ¤<\nÂˆD>\\#Â°1Â®ÃžjÂ¹t{NÂuÂÃ¿Â…Â›\'=|ÂŠ8\\ÃºÃœ\0Â£T0R0UÃ¿0\00UÃ¿Â 0U%Ã¿0\n+0\ZU0Âconnsec@163.com0\r	*Â†HÂ†Ã·\r\0Â‚\06ÂŽ)3Â“Â¸ÂŒÃ 7Â£Â»Ã“ÂÂÂ› mÂ¨gÂ²0Ã Â±HÃ›eÃ¬Â†Ã‹Âš~ÃšBÃ¤Ã»YÂ“SÃ¶Â£Â¼Ã¦3:[Ã´ÃºÃ¸\'ÃŽÃ­Ã³ÃÃ‚/Â–Âª{	Â›Ã¦5.Ã¼Â¼Ã‹Â›Ã‘fÂ»ÃŽÃ‹Âµ,Ã·HÂŽ_Ã§zÃ‡Ã0Ã©Â§?Ã¨Â¹CÂ€Vf:ÂˆÃº]|pÂŒÃ˜SÂƒFÃ¾Ã¢Â¿Ã¼Ã†vÂŠÃ½MÃ½\0Â¡fÃœTÃŠRÃ¡Ã»%ÂŒ2cÃª?RÂ°Ã§qÂ€ÂŠÃ’ÃŸLÂ¹Ã¨Â”BÃ¸Ã‹Â§Â¦Ã´Ã€ÂžÂ¨Â·Â¶9Ã‹tQ&|Âµ&ÂŒÃ«`Â©oÂ·Â´Ãµx)Ãž&Â¬Ã¦b73Â¥|Â5Ã¼ÃºÂÃ‹i`Â‡Ã¯HOÃ•Â¤0Â»v\0ÃˆÂ¨ÂžÂªH)&~Y?;Â—Â„F)Ã¢Ã®iÃ°CxÂ“Â¥z$/;Â­\0\0\0\0connsec.com\0\0>Â‰Ã”\rÂ”\0\0\00Â‚Ã¼0\n+*\0Â‚Ã¨Ã‹Â¯aMÃ©ÂºÂ¡-Ã¿5Â³Â·ÂžÂ¿ÂHfk<Ã«ÂžÂ“Â­nH	Â„Ã%ÃÂŠ{Â„4Âº#Â’bÂºÂ¨Ã¹zÃµÃ„;Â“WÂ¿Ã‚\rÃ‹ÂŠÂ£2Â­?0ÂšÃ¬QC#6Â¢ÂˆwP@\\Âª09CJ.Ãµ*Â†Â™pAÃŠaÂ§Ã»Ã¡Ã„H/Ã­Ã¹CÂdB\\ÃºO @Â‚EÂ¹zÃ®=Ã³\0h%Ã¬b:}ÃˆÃ…ÃƒÃ‹NÂ©Ã Ã­Â«Â¼R\'Â˜CÂ‡Ã+Ã†Âš*ksÂ«Ã©wÂ¿xÃ«Â .Ã®GÃ•	Â±k[Â¡wÂ—Â³n_+Â…Ã™\0Â•sÂ—Â†Ã‚=Â©`wdÂ¶@Ã‘Ã±ÃšÂ‡HÂœÃ¼teÂ·``Ã½`ÂÃ¢Â±Â‡(5\'1ZÂ½Ã™4LRhy_Ãº23yÂ°Ã‘Â¿Â™!Â¬Ã‹MÃ¤,Ã©&Ã½ÂCÂÃšzÃ’bÂ†_Â£PÂ¡Ã»Â±B5ÃÂ´ÃÂ•%Â¤Ã¶ Ã½Â¹ÃP}Â”Â³Ã…Â•cÂœÂšÃ—qÂŸÂ‹Hb~Ã„Â¿7MÃ·ÃªyÂ¥ÂœÂ’ÃÂÂ‘`ÂžOÂ•ÂÂŽ=ÂœYÂ»Â`/Â«rÃ¯Ã‘Ã«Â¼:Â›PÂƒ~ÂƒÃ‹Âž4VoÃ¼Â£Â·Â€ÂÂ´Â´AÂˆ7^Â»ÃºÂ/ÃKÃ¾lbÃ»ÂŽÂ‹rÃ€aSÂ‚)Â™$Â¯ÂTÂ‹C?\'Â¤V#Ã¹Â¯iÃ‘Ã„Â¤ÃgÂ”wÃ<eÂ–\nlTÃšÂ®Ã±ÂºMÂ¯Â¥Â›{\0\nÂ¡clÂ–-Â‹Â§ÂƒÂ”Ã‰\0^:VÃ‚Â¦Ã¸#\Z<_Ã¬Â¾Ã“Â‚ÃœÂ•Â¹O+l^}.Â¶Â¼k1SÂ«Ã¸O\'=F6}Ã•Â¾Â³e2Â¼CÂ©Ã¢rÂƒÃŠÃŒÃ˜tÃ³Â²iÂ´Ã·\ndÃ˜	Ã¨Â GJOÃ¶~ZHÂ®QÂeÂ¿Â¦Ã4{Ã‰Ã­Â¥tÃºmÃ€uÂ·ÂÃ“Â«`Â¥FÃ‚YÂ°qÂ­Â¶Â”Â¡tÂ§|k(Ã¸Ã©}Ã²Â’>Â‰JÂ¶RÃ¯Ã­Â‹Ã…jÂ‚Â·4Ã¦Â–Âª\n\\7oÃÂ·Â€Â¬=RÃ¹]ÃŒÃ«lzSÃÂÂ¨uÂ¤Ã¨~ÃšÃ…ÂÂ¿Â€>Â¥EÃ³.O\0Â˜Ã­Ã˜Ã¡ÂÂ¸EHÃ¹Â‡Ã—ÂªqZlNt*xÃ’Ã­ÃºtÂ’Ã…6:?Â”Ã¥Ã¢LUÂºÂ“8Ã¼Ã¥ÂŒ#gÃ¬Ã‘ÂªÂ†;Ã–.Ã-RÂ°Â§>Â“Â„2RÃ£jÃ‹\"Â¬Ã¿Â«ÃŸÂˆÃ€Â–P`Ã—Ã‡QÃ¼zVÂ±71UÃ—Â»$Â‰Ã°RtDÂ‹Â¬_4kÂ¡Ã±Ã¢ÃŽÃ¶,Ã‘ÃoÃ´UÃ¼Ã°sÂ·Â™h&p8kÂ—Â1ÃœÂ¢Â˜4Â¹ÂªÂ—Ã‘Z8Â‰5YÂ‹ÃµÃ”1ÃžÂ—Â¦Ã°ÃˆÃ¡!Â°EÃŸUÃ°OÃ³4/Â 0Ã½ÃœÂ¡6$Â‹Ã¹ZÂ‰hN: Ãˆ_yÂ­\rÃ§]MgÂ…Ã»UÂÃƒ`Â–Cs\rÃ–Ã›Ã«Ã’Ã¹8CÂ—ÂœÃ•lÂºWÃ°Ã‡ Ã¸Â“8Â¾X?uhu=R$ÂˆNÃ“AÂ—zÂ–Â‰Ã˜Â¯eÂ¬Â›{Â˜ÃŽÃ•\Z12Ã£iÃ¯Ã¢:;`ÃŠZ-\0Â²)z\'Ã”Ã¬6]Â¦`Ã‹	Â¾ÂµÂƒaAÃ Ã³,(LÂºÃ«Ã†qbpÃƒÃ¸Â°Ãš\"Â‰Ã·Ã”EÃ¿j%YÃˆÂ¸Ãƒ8Ã‰Â­DZÃ¥ÃÂšÂ©Â´O6D|Ãº<MWÃ´Â€Â¬TÃ¾\Z]5Â¹ÃºÃ˜z>xÂ°Ã¾Ã„Â¿Ã“Ã¬ÂˆfÃiÃ­Ã’vÃ–NÃƒÃ…mÃ’Â¸Ã¢K8WÃ®qIÃ–$ÃŽ	Ã‰F\\ÂŸÃ°LÃ¬Ã½2,jÂ²ÃœÃ\"Ã{AÃ°Â–0ÂˆyÂÂ…Ã¶Pa=kVÃŸ~9Ã…W>Â´oÃ’Ã¢ÂµÂ‹Â”Â®Ã„U^Ã¼ÂœkeÂ‹ET3|ÂŸÂ·n`Ã§Ã¥Ã¯8!ÂQÂ™jÂªKOÂ´s{\r=lÂ¢Ã¸Â§K/ÃJÃ‚.Â—Ã¥Ã¤,Ã¬Ã‡ÂÃÃœ ?QÃ£\"ZÃ·hÂ¶9}ÂB,Â€Ã˜Ã• ÃšÃœ:Ã™ÃˆsuÂ‰ÂŒuÂ˜Â©Â»Ã½s	)$Â†fÃ¯Ã¯Â…?Â¤ÃœÂ¨Â¥-Â¼glÃ¸kÂ¥Ã^ÂŸÃ‘Â¼	OÃ¥Â±Â°Â›Ã”ÂŽÂ¹ÂlÃ±Ã•IÃ$Ã¯lÂ°Â¼KcÃ¸Â¾Ã¯0Â›Â±Ã”Ã–?\"Â¬,ÃÂ†8Â›Â²Ã¨.ÃŽÃ•ÂŽIuxtÂšÂ©?nm]Â®Âœ8Â™JÃ¬Âº2$Ãƒ\0\0\0\0X.509\0\00Â‚\Z0Â‚Â QÂ‹Â¸Ã»0\r	*Â†HÂ†Ã·\r\00O10	UCN10	USH10	USH10U\nconnsec10Uconnsec.com0\r130509145555Z\r400924145555Z0O10	UCN10	USH10	USH10U\nconnsec10Uconnsec.com0Â‚\"0\r	*Â†HÂ†Ã·\r\0Â‚\00Â‚\nÂ‚\0Â›sÂ~Ã¬Ã¿Ã¦g9hÃ‡}Ã²@Â³Â‡Ã¶Ã§Â«Â¦Â¦^ÂŠÃ›ÃšÃ˜kÂ²Â¯Ã¤:ÃªiÃ­&Â^Ã©WÃ¼Ã¶+brÂ‡Ã•ÂšÂ‘Ã¿<KPÃŸuÂ®ÃšxÂ¨7Â›Ã¥ÃiÃÃ»Â¢ÃˆÃ—-.ÂˆBÃˆ1Â¢Â‹lÂ´^DÃ~.Ã—Ã»Ã¸ÂÂÂ„jÂ¬Ã´Ã³Â¨Ã¦Ã‚Ã³FÃ°Q)Ã·#Y#\ZÂ Â«Ã¢Â›X0`ÂŸÃ˜Â·ÃšÃ½Â¯Â©Â‚\ZCÃªÃ·Ã³ÂŽ~Ã€<Ã†Ã‰Â”#Â–PktJÂ²ÂŽÂš$uÃÂ¯ÂEÂ¸Ã§ÂŠÂ›fÂ xÂ‚ÃÃ¶Ã¤Â„Â¿Â±Ã¼ÂºÃ£:Ã¹Ã¶Ã„\0Ãžt6LÂ©ÃšÂ³/8Ã†Ã”ÂˆÃƒFQMÃ¡Â¼zÂ|,Â‰Ã³ÃœÃ¯J9uuX?ÃªÂ ÂŽÂ¢Ã‹DÂ™qÂ–\'Ã»ÂŒnÃ£ÂµÃÃ„kÃ‚a&Ã­Â$sqÃ…\00\r	*Â†HÂ†Ã·\r\0Â‚\0ÂšÂ¤\rÃ…ÂŽÂ²Âš8Ã·xUÂ°Ã‡ÃÃ¨Ã€\"Â½Ã¼Ã§NÂ‡KTÂ Ã™FÂžÃ˜Â€Ã”w^UÃ³_:Â¶ÂŒ!C|ÂµÂŸEhÃ«.Ã¾RtÃªuÂ«Ã¬dÃ¾Ã„Ã±IÂ¬wczÃŒÂŠ.Ã¨=Ã®eÃ‹Z\0Â‘Ã£Ã…\nÃ—ÂŸÃ©Â½Ã†OG-ÂšÂ²#qlBÃ§ÂUÂ“}EB\nj^ÂŸÂ›jÃ‘7Ã¢Ã‘Ã¾ÃºÂ°x[3Ãƒ%ÃsÂº2HÂ‰Â…Ã™ÂˆEÂ¯Â°Ã½Â¥ÃšÂ‘pÂ´zÂŠÂ”oÃªZÂªÃ¢:Ã¦Ã®8pSÂ¹Â–SrÂ€RÂ«)Â‘Ã¸Ã»Ã´Ã¨+h^Â–ÂªÂ«YÂ§RÃ^Ã˜WDÂ˜rOÂ£u Ã¶Â±sÂ¨YZoÂ“Ã¾Â>Â¾#ÃºÃ®Â¢Ã¡NÃ»zxÂœÃ´Ã©Â°)YÂƒ|ÂºÂ¼ÂwÂŒ-}Ã£nÃ¯Â—Ã”mR}(Ã¡GÂ‘jÂ“f%Â•ÂŒÃ¶Â¹ÂŒÂ˜MiÃ•Ã—Â¹CÂŠ','connsec.com','CN=connsec.com, O=connsec, L=SH, ST=SH, C=CN','22 May 2013 06:31:35 GMT','sdf','emailAddress','superadmin','2013-05-12 06:59:31',NULL,NULL,1,234);
/*!40000 ALTER TABLE `sso_saml_v20_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userinfo`
--

DROP TABLE IF EXISTS `userinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userinfo` (
  `ID` varchar(45) NOT NULL,
  `USERNAME` varchar(100) NOT NULL,
  `PASSWORD` varchar(200) NOT NULL,
  `DECIPHERABLE` varchar(200) NOT NULL,
  `USERTYPE` varchar(45) DEFAULT NULL,
  `WINDOWSACCOUNT` varchar(45) DEFAULT NULL,
  `DISPLAYNAME` varchar(45) DEFAULT NULL,
  `NICKNAME` varchar(45) DEFAULT NULL,
  `GENDER` tinyint(3) unsigned DEFAULT NULL,
  `BIRTHDATE` varchar(45) DEFAULT NULL,
  `PICTURE` blob,
  `IDCARDNO` varchar(45) DEFAULT NULL,
  `MOBILE` varchar(45) DEFAULT NULL,
  `MOBILEVERIFIED` varchar(45) DEFAULT NULL,
  `EMAIL` varchar(45) DEFAULT NULL COMMENT 'ÃŠÂ¼',
  `EMAILVERIFIED` smallint(5) unsigned DEFAULT NULL,
  `WEBSITE` varchar(50) DEFAULT NULL COMMENT 'ÃŠÂ¼',
  `TIMEZONE` varchar(45) DEFAULT NULL,
  `LOCALE` varchar(45) DEFAULT NULL,
  `PREFERREDLANGUAGE` varchar(45) DEFAULT NULL,
  `PASSWORDQUESTION` varchar(45) DEFAULT NULL,
  `PASSWORDANSWER` varchar(45) DEFAULT NULL,
  `APPLOGINPASSWORD` varchar(45) DEFAULT NULL,
  `PROTECTEDAPPS` varchar(450) DEFAULT NULL COMMENT 'jsondata',
  `PASSWORDLASTSETTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'ÃžÂ¸ÃŠÂ±',
  `BADPASSWORDCOUNT` smallint(5) unsigned DEFAULT NULL,
  `UNLOCKTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ISLOCKED` tinyint(3) unsigned DEFAULT NULL,
  `LASTLOGINTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'ÃžÂ¸ÃŠÂ±',
  `LASTLOGOFFTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'ÃžÂ¸ÃŠÂ±',
  `EXTRAATTRIBUTE` varchar(4000) DEFAULT NULL COMMENT 'jsondata',
  `GIVENNAME` varchar(45) DEFAULT NULL,
  `MIDDLENAME` varchar(45) DEFAULT NULL,
  `FAMILYNAME` varchar(45) DEFAULT NULL,
  `HONORIFICPREFIX` varchar(45) DEFAULT NULL,
  `HONORIFICSUFFIX` varchar(45) DEFAULT NULL,
  `FORMATTEDNAME` varchar(400) DEFAULT NULL,
  `WORKEMAIL` varchar(45) DEFAULT NULL,
  `WORKPHONENUMBER` varchar(45) DEFAULT NULL,
  `WORKCOUNTRY` varchar(45) DEFAULT NULL,
  `WORKREGION` varchar(45) DEFAULT NULL,
  `WORKLOCALITY` varchar(45) DEFAULT NULL,
  `WORKSTREETADDRESS` varchar(45) DEFAULT NULL,
  `WORKADDRESSFORMATTED` varchar(45) DEFAULT NULL,
  `WORKPOSTALCODE` varchar(45) DEFAULT NULL,
  `WORKFAX` varchar(45) DEFAULT NULL,
  `HOMEEMAIL` varchar(45) DEFAULT NULL,
  `HOMEPHONENUMBER` varchar(45) DEFAULT NULL,
  `HOMECOUNTRY` varchar(45) DEFAULT NULL,
  `HOMEREGION` varchar(45) DEFAULT NULL,
  `HOMELOCALITY` varchar(45) DEFAULT NULL,
  `HOMESTREETADDRESS` varchar(45) DEFAULT NULL,
  `HOMEADDRESSFORMATTED` varchar(45) DEFAULT NULL,
  `HOMEPOSTALCODE` varchar(45) DEFAULT NULL,
  `HOMEFAX` varchar(45) DEFAULT NULL,
  `EMPLOYEENUMBER` varchar(45) DEFAULT NULL,
  `DIVISION` varchar(45) DEFAULT NULL,
  `COSTCENTER` varchar(45) DEFAULT NULL,
  `ORGANIZATION` varchar(45) DEFAULT NULL,
  `DEPARTMENTID` varchar(45) DEFAULT NULL,
  `DEPARTMENT` varchar(45) DEFAULT NULL,
  `JOBTITLE` varchar(45) DEFAULT NULL,
  `MANAGERID` varchar(45) DEFAULT NULL,
  `MANAGER` varchar(45) DEFAULT NULL,
  `ASSISTANTID` varchar(45) DEFAULT NULL,
  `ASSISTANT` varchar(45) DEFAULT NULL,
  `CREATEDBY` varchar(45) DEFAULT NULL,
  `CREATEDDATE` date DEFAULT NULL COMMENT 'ÃŠÂ±',
  `MODIFIEDBY` varchar(45) DEFAULT NULL COMMENT 'ÃžÂ¸',
  `MODIFIEDDATE` date DEFAULT NULL COMMENT 'ÃžÂ¸ÃŠÂ±',
  `STATUS` tinyint(3) unsigned DEFAULT NULL,
  `DESCRIPTION` varchar(400) DEFAULT NULL COMMENT 'ÃžÂ¸',
  `MARRIED` tinyint(3) unsigned DEFAULT '0',
  `IDTYPE` tinyint(3) unsigned DEFAULT '0',
  `ENTRYDATE` varchar(45) DEFAULT NULL,
  `STARTWORKDATE` varchar(45) DEFAULT NULL,
  `AUTHNTYPE` tinyint(3) unsigned DEFAULT NULL,
  `NAMEZHSPELL` varchar(100) DEFAULT NULL,
  `NAMEZHSHORTSPELL` varchar(45) DEFAULT NULL,
  `ONLINE` tinyint(3) unsigned DEFAULT NULL,
  `BADPASSWORDTIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `SHAREDSECRET` varchar(500) DEFAULT NULL,
  `SHAREDCOUNTER` varchar(45) DEFAULT '0',
  `APPLOGINAUTHNTYPE` tinyint(3) unsigned DEFAULT '0',
  `JOBLEVEL` varchar(45) DEFAULT NULL,
  `QUITDATE` varchar(45) DEFAULT NULL,
  `PASSWORDSETTYPE` tinyint(3) unsigned DEFAULT '0',
  `LOGINCOUNT` int(10) unsigned DEFAULT '0',
  `LASTLOGINIP` varchar(45) DEFAULT NULL,
  `GRIDLIST` tinyint(3) unsigned DEFAULT '0',
  `QQ` varchar(45) DEFAULT NULL,
  `WEIXIN` varchar(45) DEFAULT NULL,
  `SINAWEIBO` varchar(45) DEFAULT NULL,
  `YIXIN` varchar(45) DEFAULT NULL,
  `FACEBOOK` varchar(45) DEFAULT NULL,
  `SKYPE` varchar(45) DEFAULT NULL,
  `MSN` varchar(45) DEFAULT NULL,
  `GTALK` varchar(45) DEFAULT NULL,
  `YAHOO` varchar(45) DEFAULT NULL,
  `LINE` varchar(45) DEFAULT NULL,
  `AIM` varchar(45) DEFAULT NULL,
  `DEFINEIM` varchar(45) DEFAULT NULL,
  `WEIXINFOLLOW` tinyint(3) unsigned DEFAULT NULL,
  `SORTORDER` tinyint(3) unsigned DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ÃƒÂ»';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userinfo`
--

LOCK TABLES `userinfo` WRITE;
/*!40000 ALTER TABLE `userinfo` DISABLE KEYS */;
INSERT INTO `userinfo` VALUES ('44D64694BADD4423A336C05D49469B60','superadmin','dJbALpTupUiaiWsbgp/TVT/3mXQK5Q2RwyEOa7Vc3j0=','c0aca9ea77b47ef681862d5a328b2366','TEMP',NULL,'è¶…çº§ç®¡ç†å‘˜','SuperAdmin',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Asia/Shanghai','fr','zh_CN',NULL,NULL,NULL,NULL,'2015-04-30 02:07:25',NULL,'2014-01-20 08:00:00',NULL,'2015-04-30 02:08:09','2014-01-20 08:00:00','{\"age\":\"0\",\"cardno\":\"0\",\"oldname\":\"null\"}','è¶…çº§ç®¡ç†å‘˜','è¶…çº§ç®¡ç†å‘˜','è¶…çº§ç®¡ç†å‘˜',NULL,NULL,NULL,NULL,NULL,'AFG',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'AFG',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'101','ç§‘æŠ€éƒ¨',NULL,NULL,NULL,NULL,NULL,NULL,'2014-01-21','admin','2015-04-30',0,NULL,0,0,NULL,NULL,0,'chaojiguanliyuan','cjgly',1,'0000-00-00 00:00:00',NULL,'0',0,NULL,NULL,0,1,'127.0.0.1',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),('7BF5315CA1004CDB8E614B0361C4D46B','admin','$2a$10$Z82fPt57zJZ5WTAc022fS.uYgS4N1vbEorqay7n0mL4XukMf04MOC','admin@admin','TEMP','adsystemadmin','ç³»ç»Ÿç®¡ç†å‘˜','ç³»ç»Ÿç®¡ç†å‘˜',1,NULL,NULL,'2342342343242344234','13705130848','0','admin@connsec.com',0,'http://login.maxkey.org/','Asia/Shanghai','de','zh_CN','5','wusdfdsf','0e6bea8d16229f0df9ff644efaf4e749',',41065fe3-ae67-4172-a460-fd0079e88294,52f0002d-4ef7-4b27-8c5b-41b9ee80835d,3f57d0b2-99ab-4e66-a938-718befb55369','2019-11-07 15:55:05',2,'2015-04-29 02:10:51',0,'2019-11-09 14:48:25','2019-11-09 14:23:38','{\"age\":\"12\",\"cardno\":\"11111111111111111111111111111111\",\"oldname\":\"Ã§ÂŸÂ³Ã©Â¸Â£1d\"}','admin','admin','admin',NULL,NULL,NULL,'shimin@qq.com','123123','CN','åŒ—äº¬','åŒ—äº¬','åŒ—äº¬',NULL,'123123','123123','admin@qq.com','123123','ä¸­å›½','åŒ—äº¬','åŒ—äº¬','åŒ—äº¬',NULL,'123123','sdf',NULL,NULL,NULL,'æ€»éƒ¨','105','ç§‘æŠ€éƒ¨','ç³»ç»Ÿç®¡ç†å‘˜',NULL,'ç§‘æŠ€éƒ¨ç»ç†',NULL,NULL,NULL,'2014-01-21','admin','2015-05-05',0,NULL,0,0,NULL,NULL,0,'xitongguanliyuan','xtgly',1,'2019-10-20 12:17:05','6e0cf549e8271c6081525dc3c92acf1412f8a3c5a9aa75eabe367bc9896da58b1e698f87c64032dc548d6ec7dc3c4863','0',0,NULL,NULL,0,649,'127.0.0.1',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `userinfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-09 22:50:27
