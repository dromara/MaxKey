-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: maxkey
-- ------------------------------------------------------
-- Server version	5.5.23-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
INSERT INTO `apps` VALUES ('1327c121-cfad-49ba-bf61-afd3a1e09d5c','LTPA-Cookie','http://tokenbased.demo.maxkey.org:8080/demo-ltpa/ltpa.jsp','FINANCE','d6227a3d7756c255874ec7029678b8d1','Token_Based','ÿØÿà\0JFIF\0\0\0\0\0\0ÿÛ\0C\0		\n\r\Z\Z $.\' \",#(7),01444\'9=82<.342ÿÛ\0C			\r\r2!!22222222222222222222222222222222222222222222222222ÿÀ\0\0Ž\0–\"\0ÿÄ\0\0\0\0\0\0\0\0\0\0\0	\nÿÄ\0µ\0\0\0}\0!1AQa\"q2‘¡#B±ÁRÑð$3br‚	\n\Z%&\'()*456789:CDEFGHIJSTUVWXYZcdefghijstuvwxyzƒ„…†‡ˆ‰Š’“”•–—˜™š¢£¤¥¦§¨©ª²³´µ¶·¸¹ºÂÃÄÅÆÇÈÉÊÒÓÔÕÖ×ØÙÚáâãäåæçèéêñòóôõö÷øùúÿÄ\0\0\0\0\0\0\0\0	\nÿÄ\0µ\0\0w\0!1AQaq\"2B‘¡±Á	#3RðbrÑ\n$4á%ñ\Z&\'()*56789:CDEFGHIJSTUVWXYZcdefghijstuvwxyz‚ƒ„…†‡ˆ‰Š’“”•–—˜™š¢£¤¥¦§¨©ª²³´µ¶·¸¹ºÂÃÄÅÆÇÈÉÊÒÓÔÕÖ×ØÙÚâãäåæçèéêòóôõö÷øùúÿÚ\0\0\0?\0÷ú(¢€\n(¢€\n(¢€\n(¢€\n+ÅZ¬º\'†ouZh”l\rÐ1`?Lçð®ÀÞ4Ö¯üMŸuö˜nC}åU(ÁK0cë4QE\0QE\0QE\0QE\0QE\0QE\0QE\0TRË<²º¢\"–fc€\0êIì+Ë<Sñ6Y™ìôòãåZí‡ÌßîÐ´yô­\0z¯â=\'CMÚ…ìq1Xó—?E×¨ü\\IM7L’OI.(ÿ\0¾W\'õåÒË$ó4³;I+³»f>¤žM2ìuZßÄ\rc^°–Ââ;H­åÆõwÉcÜ\nÁÒõ;R†þÐ ž\"Jï‚GÐš¦`Hä¤r\08\0“èhèvµHÈš}¬Ê:˜Ù£o×p®¯Iø— ê%c¸y,f<bqòçýáÇçŠñA8E±ôìr$±¬‘°taÀäê\rI_<h>)Õ<;0k)É€œµ¼™hÛðþî1ø×²øgÅö&¶ÌÉºAûÛg?2ûƒüCÜ~8¦#¢¢Š(\0¢Š(\0¢Š(\0¢Š(\0¦³ª)v`ªI\'\0\nuyÇÅµ­¢h–¯‰nuÃ)åcì?àDÀ{Ð3ãŸ\ZI¯Üµ…“²é‘¶28óØw?ìúÄöÇEwðOöìƒQÔP:6Â§O=‡Qþèïêxõ¤Q•á¿êž$a,J-ì³†¹”P£«ÓÞ½CHøu ih­%·Ûg™.~aŸeû£ò5ÔÇC\ZÇ\Z„E\0*¨À\0t\0v-1\\ç¼O¢6¡á;Ý;O†8ät8ÕB©*Á±Ç8Åyÿ\0€¼)¬Ûø¦ëËm`µY¦w¥@Qß®sÓŠö\Z(—¨øwHÕ‹Ý:Úbˆ >Œ0Gç\\¿ð¨i´)Û#Ÿ³NÙÏ°oè:õ\Z(æ;›Yì®Þæ†hÎpA§YÝÜX]Çuk3E<M•u<ƒýG·z÷oxJÏÄÖD0X¯cSä\\Èÿ\0dú©ôíÔW…_Y\\i×³YÝÆbž*ê{oPzƒÜRî~ñ\\>&Ó²Ác½„ž!ÓÙ‡±ýÓWÎ\Z³>ƒ«Á¨[’J:g‡CÔ¯ó¾‡²¼†þÊ»wÈwf˜2ÅQ@‚Š( Š( ¥•-á’Y,q©f\'°$×Î:Î§&³¬]ê“™ä, ÿ\0\nôUü^ÙñôØø2ý•Š¼ª°©íéšðZOhÒkúí¶ž„ªÈÙ‘‡ð ååÀ÷\"¾†µµ†ÊÖ+[xÖ8bPˆƒ P0yÇÂM45\rQ‡ÌÎ-Ðú\07êGå^@0¢Š(QE\0QE\0WüPðê]éÃZ·@\'¶fÀûÑ“Ôû‚\"kÑ*˜#»¶–ÚeÝ¨Q‡¨#ùÐÌ•ë\n5ƒq¦Üé2¾ZÕ¼È?ÀÇ‘ø6ïªò»ÛF°¿¸³“ïÁ+DßUb?¥t¯MŸ-q	p­{år?U†{½QLAEP\\ÏŒ¼R</§E4py÷¹HÔœ(ÀÉ\'àqù×MXúÿ\0‡¬|Gaö[Õqµ·Ç$gŒd§­\0xÿ\0ˆ¼u¨x“M[›kh£Y]ÑnÉ $ñÍrÕÞøËÀv~ÑVþÖêæf3¬l%Û€<ðp?:à©u¾ñõï‡tÔ°†ÆÚXUÙÙ™™Y‹žGÓ¥{6•¨ÅªéV·ð±ÜF$Un£=Ó¥ygƒ¼	¦ø‹EŽþâîé\\JÑ¼qÊxäŒŒ‚+Ö­ma±´†ÖÝpB¡@\0ÀÄÉè¢ŠQE\0QE\0ÃxÇÇÇÃš‚iö¶‹q>Á$†F!TàrO®æ¹OøÃÄ·QÝK4¶×»ÆÜ£>™<ÐŠêÚ‹jº­ÕûÄ±µÃïdRJƒœgéMÓoŸLÔío£PÏo*ÈªÇˆ9Á©µË´­röÂZXíå1‡pb\0Îqïšfaý«¬ÙXd,e”d€O\'ðŠ=Kø¯-Æ£ÚtQÁ+2E#¹8r+ÔkÓ~i¶7ðÝM{qr\"`â&UU,FqÉÕßS$(¢Š\0(¢Š\0ç<u`uj1¢î‘L£ÝHoä\rxÒ¾ždWBŽ+==«ç_i\r¡ë×z{²7&2‰*.> Ð4w?	5EY/ô©±q\'®>Vÿ\0ÙMz¥|Ù£ê“èÚ½¶¡oËÂÛŠç—£õô6›¨[êºtÖ¯¾”2žãÔpx?J—h¢ŠQE\0QE\0Vúò->Â{É›CHÇØÕªóŠ>$U‰tgùÜ¬—Dº½U~¤àŸ`=hÌnnîêk™Nd™ÚFú±$ÿ\0:ê¾X›ßA!\\¥¬m3{m«~•ÈW±|-ÑMž‰.¥*â[Ö2:F¹ó%åHgESQE\0QE\0Wñ\'Ã\rªé«©Z¦níïP9’.¤}GQø×yE\0|½]_‚üe/†®Œ¤ÓflºLgûÊ?˜ïõ­ø¬e—WÒ¢-hÄ´ð¨æÝ”w×ÓéÓÏi}3g{o¨ZÇsi2Mƒ(èr«ó¦‡â]OÃ³™,.6ÆÇ/ŒÆÿ\0Uì}Æ\rz^‘ñSJºUMJ)l¥èX$gñÄ~4ÅcÐ)¡ƒgWâ¿ØËàûétZÝ§eU_*Q¼À àŸ¥yÿ\0ÃÝQôÿ\0@¯t\"µ™\\M½ö©Iäã9Ÿñ ,{­Ìßø÷ÃzrÚŠNãø-ŸÄqùšàµÿ\0Š÷èÐiQOV!¥#Û²þ§ÜP#²ñ—íü=ZÚ²Í©¸ùS¨‹?ÄßÑzŸ¥xœóËs<“Ï#I,ŒYÝŽK1êM1ÙÙÝ™™‰ff9$žä÷5sJÒ¯5B;;Œ“?àª½É=€¤QsÂþ›ÄzÔVh`_žyð <þ\' ÿ\0ëWÐpÁ¼Á\nŠ5\nª½€cøgÃ–Þ\ZÒ–ÖžVù§˜ŒúÐßnÓ$(¢Š\0(¢Š\0(¢Š\0(¢Š\0B# ×x§á¤W¯%î†R	ÎK[7¹ÿ\0dÿ\0	öéô¯F¢€>g¿Ó¯4Ë£m}m-¼Ãød\\gÜ„{Š­_K^éÖz¹†öÚ+ˆðJ‡áž•Çê\nôK¢^Ò[‹6=•÷¨üŸÖÜñŽ§\'­žµÜx“áÌú•>¢5(ç†¹SV;˜/©ë™Ðô‰5Ýf\r6V\'›v aKtJC3©\'¥z­ŸÂ(T†½Õ¤qÝ`ˆ/êIþUÕé	ÐtfW·±Y&^“N|Æã<À\nsÊ|=à=[]d•ãkK#Éže °ÿ\0ezŸ¯Þ½‡AðöŸáÛ/³ÙC´œ%n^CêOôè+^Šb\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€2üA¤&»¡]i¯)‹ÎP¤AÇ~@®7Áÿ\0nô=u5+û¨É\"HrrHÆâHàž+Ñ¨ Š( Š( Š( Š( Š( ÿÙ',1,NULL,'2019-11-09 10:13:11',NULL,NULL,'','','',0,NULL,NULL,NULL,'0',NULL,4,0,1,1,'org.maxkey.authz.token.endpoint.adapter.TokenBasedDefaultAdapter',NULL,NULL),('38c8a544eaa04aaeaa49d9c77ace40cd','Token_Based_Json','http://tokenbased.demo.connsec.com:8080/','FINANCE','c1f6adfcadd8ba23f73395f16a45dbe7','Token_Based','‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0U\0\0\0X\0\0\0„k»„\0\0\0	pHYs\0\0Ä\0\0Ä•+\0\0\0tIMEÞ!5ž½òÊ\0\0\0tEXtAuthor\0©®ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0Àæ‡\0\0\0tEXtSource\0õÿƒë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨îÒ\'\0\0 \0IDATxœíyœTå•÷¿Ïs—ªê½¡ÙZQ@EPÜ…ã“˜Ä%Ž˜èä}Í˜8“è¨q‹3.˜u|\'!fÔD‡à›ŒÑà‚ˆŠ‚,*‹¬‚Ð,Ý@¯Uu—ç¼Üº—ª¦é2ïÌä|¸}«nÝû,ç9ÏyÎùó\\ÔÑG-žç‘Íf\0D¥¥ýSÌ\'Á¶mÒé4}ûöE2xð`&Mš„çyÿÙíü/I\"B*•âõ×_§µµ”Rríµ×Ê_éãÓôéÓEk-¶ˆËåÃ ÐZ÷zê‡aXr–ÂôˆËSJaYÖ_Tµc‚\0¥Æ˜’¶h­÷ùÜ\nÃÛ¶Éår(¥°ã´Öh­{ÜiÁƒˆàºîG>[Ü±Þ’¤ð)ú,(Rô›`PDƒèºî~Ë‚\0éUßµÖÉ9¾f%ÒÔmG\n\n9¾/f¦eYIlÜ¸‘Í›7³~ýzššš’F|ö³Ÿå¤“NÂÓCÉˆX3.bbtÞËÈÂ\"/|hllä ££ƒT*@EEC†áÈ#¤¾¾žaÃ†%5…aˆeYûô5îWŒ-æa‰¤vþ¡«‡ã‚ãémÛ6¾ï3{ölfÍšÅ[o½ÅöíÛ»eÍQGÅÉ\'Ÿœ0õÀ¥\"â (D)Œ$RE´ò#–‹B”BhmÓÖÖÎ]wÝÕmÉeeeŒ?ž‹.ºˆ«®ºŠ>}ú–e†a‰vÕÞ®øeïsWwÝ*2·Â0Äq6nÜÈ%—\\Âo¼Qr¯eY%SÉ²,Œ1TVVî£g¬î¢i­@¤PIá3‚26¨hÒ\0-‚vdÏž=%}‰×ŽŽ^{í5^{í5|ðA{ì1¦L™’XB½ÑµÌÔb&h­iooçâ‹/fÑ¢E¤Ói‚ HF6ÃDš‹©£££Ûi´?éN\0BÐ\"‘Þ”‚2D¡” ´¥A)Â0dÛ¶m]–ëC¥Žã°~ýz.¸àÞ|óM†’¨­žÒ35¦ p]—™3g²hÑ\"\\×%—Ë%Ó9CÈ˜1c;v,555‰š8þøãK”û¾ÒZ¼Ô|V+Ö›!\"!\"\ZŒ ´EÒ‹ƒØ(Êj«úpï½÷&ÚÒÒÂÚµkY½z5ï¿ÿ~T®RäóyÇaçÎÜwß}<øàƒø¾ßkGH¾öµ¯‰ˆˆïûbŒéÒ3Æˆ1FÂ0”0å”SN¥”(¥¤ÐOqGî¾ûnillìÖ–Ã0)«S\r…CDLá³)|£³	C1atMN²~‡dý@‚’çø¹@üv_Âv#Æëº¹\\NfÏž-ýúõÛ¶“>(¥¤®®Nššš’þvÝ^I®ù¾/\"\"—^z©X–%=’ÔXÊ¶mÛÆŠ+’i¬µÆÃå—_ÎM7ÝD‰:\"s#6Ùº¦\"iˆ%Cí½.ª ÁF!Ê\n¤íX×ØÊêMMXÃÖrD}\ri ôŒâ+Œ	E«¤_|1¹\\Ž+®¸\"QK\"Bcc#ï½÷§vZbƒöÄìSã‚W¬XÁîÝ»“†c\0˜>}z²\0Ä6klžÄ÷ÄÛW¯My‰ïÕ€ ¢€\0#šÀRŽ Hñ›ùKùÅC/²}Ã&Äå¤HgúÐ¯Oš¿¹ô\\xÖì0ÀQb,Ð -EäÉå.¼ðB\r\ZDCC@²ê/Z´ˆSO=5atOT@%U)EcccÔåÂ¢P[[Ë‘G‰Ö\ZÇq’>~¦XB»×§1SÑ‘TF&hbrˆR´fS\\só¿3{þB¾tÑ™|ÿÆË¨VËÚ†væÍ_ÇŸÞÚÎÕ7ýŽ\'>¹ŒÝúe†d@‚\0Ë²1:’ôhòhªªª7n\r\r\r%fÞ¶mÛJìÔžX=bj\\I[[[É5¡®®ŽÚÚÚ¤%ìêô½³#Q¸Jì¡TÁl*,TJaD£ÐXÊáËßœÅ‚•˜óð|ú˜A¼ñÚ:®ûé|­ÝA«åµuÔôÁÓ/l%Ìþ–ßÜw9å’+‹Ré‚]›\"”h!\Z4hÐ>míèèHÚÚu{»§^9¼íííû\\K§Ó¤R©Ä#)¦b§¢Ø÷.%UrˆèÈc*øì–•æ¾Gßà™…Kù¿¿ºŠO3ˆÀ÷øã›«ù?­cO&H;lkÝI³R3h$Ï¼ÜÀ¿žSVÚr@Á+00vc‹Û3µ¸J½bj¬‹G/þ/ZÅŠÿÀ¨`N¦{ôQP\nŒmi¶7ûÌx|!÷³8cä ðÁv\\²õP—F¥-|rXN–GÞ´“®«çÑ\'ßfãÎV´Î`\nÎ‚ÈÞÁ.VâöÆ}ì9Õc;µ¸¢ùÆÌí©rOV%(Q‘§¯\" F¡PZ±ð÷(¦_t*ï®[Ï¼×W³%7€‡~·”tY\Z?ô±Åe\"-B?UÁ¦æ¯/^ÉðOŸ€	}”%(ö.œ1#‹Û\\,,=¥^1µ˜ŠXÌÄžzN1 ¢$ZH0\n”ÂˆH€%«ß[Ê1C`Pßr¾ôí™·lT Ú²q\\Á-Ñ\Zg€\0•RøaÀºõ;d!¨\0°’¶v×æÞHjïAÄƒL‘lJ!ÉƒÊƒÑ ÀèH¿ín5”÷íÃ²\rM,[³“Úú£ÈTçœ˜ª\0²„J!hÀAÄÑä²~äq)Ð‰¿RJ]	Ao$µWLí®òÓ(A%pâÐ&‹ƒ-_ÕUlßÝÂªµ;ðMÓ…A@´°)DÅ(U\\&håRQA‰ÂHX`ô¾k±¤ÆçÞ¨%©Åu‡<èô\n¡²	•\r€ñqhSŠE\ZÙ´£•vãdÊŒB¡QK^º*fL`p”0ê¨AˆD Jöõì–NíS‹WË}\Zÿ-’\\‘½ßE\"í]*ZÜöš¨$7 ø~\Z×©ááçßæ¥Å ]Ãœâ–U\Z…B¡E\"tªP».°ã8˜öŒRÆ)ÇE J[dvÿôÑÎÊ~øs 7XìEÅ.(Ù¢²(Žæl¤Èâé-bŠ9ý3E”Bƒ¡¶pËÊøÍ‹ïrÛ=OãTÄØåìjóÉÊ*Ø—ìµ 2‡Œ1Q„­ùÖå\'2°<…\nàÖÀ}ÕUñô÷<¯¤o=‘Øá©±T577\'ï¬‡DE¾»…Š}ö\0,K£J\\=!B”V…0‰ &Š-‰ñq\\‡Ž@óÏ¿|‰=üªb(¡±ÉçòäÀÛCºf\0Z[…AŒ%U¡-\rbhÙ¾o\\z:_9ÿ‚|ˆ¶¬Ârhv9ÅãkÙl¶×±´¦~”±W²~ýú’ï\0™t\n¥mP>øA§[°œ:\0>ÜÕÆî¶,–­9l`_ªm0ˆThPJƒe•æù·Ör÷ÌWxuE:Ý`Ojm&ß—‘GL¤½Åç/¿ÃÖšLíá(Ý†ˆJ\ZÁÎmæŽ¯Oá»×œƒ6cE¶®‡	UÁ´°O7lØÐe¼*æSñy¿Lý¨‘‰ÁåÜcG­¶¢ŒKÎo%Už¡1_ÇÌ_çws–²z™O{K@&å°!çœ4ŒKÏ;•GÀ¶,ZZ/_Å¬g–óÌ››0x¤µÍ\n‡«þæd>û©1PCŠH³,½äDùÃRÿãR:¨A”ƒFfwq×\rŸåºó\'†hm2‚VšÐDƒ)\"Œ9r&mØ°]»vQWW·3;ó«óõ}¦gC8þîû>¶m³jÕ*/^œ@d±B?vÜ”J<Â@á–•³äÃ]üïžfáÂML9iw\\;‘£†¢µ¥W­æÉç6òïOÿÃê-Ü´CÃö,M»,|×Eeê(Ï6óõÏâº+NgPß*\0Œç„X0~ø Ækµvÿôèû”÷;‚Žæ&Î8®WŸ?¼×Š­5P˜È0P‘ÉUœƒ0räÈD8 \nh6551wî\\.»ì2<Ïë2üÞ]\0sŸéA¢+‹ñÒt:\rÀwÞ‰ïû	3EKkN;ýÔHŸ\Z;e±z»á’¿}ŒÆ]üûÏ¾ÈyS¥aWO>ûó—ì Mê¨<¼œ=Û¶°lG#è\0Ä¡rà@‚ÝmU-|ÿž/pöÄ¡…AÍ¢tˆe¥±m\rxù,X).ÿüTúãV\Zƒ€ ìàÓ§@FßäÐvyABe\nã\"Â±ÇËGÁºuë°m;‘¼3fpÁPYY‰ïû%|ŠÃóqÎC1•¬þÆÇ!•Jáº.Žãàº.étšõë×3}út~ûÛß&±(×u	Ã³?ýiNœ</ÑÊ%gl¾u×ïÙ²©Ù^Í—Ï>/Ÿçêëÿ~0—§—læ¥åo±bó\ZB2eý)K¤*SIvÏVN“âÙ/çì‰CÉgó˜0e» Ëm!ÊÅ¨Êq¥2¨–£èG.ÛŽ¶F­ŠL&ApáØ‚ƒPäRÇý(++ãª«®B$J4SJaÛ6Ë—/çœsÎaÁ‚X–…ã8%<q§KœµDR-ËbÍš5Ì™3\'Zt‚€íÛ·³dÉ.\\Hkkk2íòù<C‡å_þå_BŸ l!åöåå×¶òÜŸßåŸo½³Æ…ö,}Ê39÷Ã<éÊ*°0aˆ	Z*q”…ÉnaL}Š‡î½„Á5):ry,Û ´€h,cGè•.¨(*ô°µËØ‘õÌ]²•Êš4ëªøL\Z£ml1˜HLŽÂ^=í×_=,`Îœ9ÉuÛ¶Y°`§v\Z“\'OfâÄ‰6Œòòr|ßç¸ãŽã“Ÿüd²ï’©¶móöÛosýõ×ïÃý˜é±Þq‡O}êSüìg?cÈÐ!ø9G¹ †ß<»‚au‡ñµ\'°vG;ÙŸwoeÞ‚pu_üœ­5ÊÊ¢MžP:ðD!áN¾û‹^“\"çûXndW\nv„*©\0”ƒ.ï¢öÚ¦uÕ@\'mWá¤2‘‰l\n¢¹-Òýšá8³fÍâ¶Ûnãá‡f÷îÝ‘j+ôùÍ7ßäÍ7ß,áÇ•W^É™gžY*Ú‡©\"QJ`:NF0Öq,_)Å´iÓ¸ãŽ;8ùä“Èår¸–‹Aáá³hå&¦LIMÊæüëg²q[Ï7•Ø®è_{(±°ÄFiÊ+ÒÔ÷©Áˆ`[ ”]p_£l\n-VäjÆÉ*Š9¡eå),¥#ÏL[i3‰ÿ!}ª$¾“Ö\ZÏó°,‹þð‡üÃ?üwß}7¿üå/“0uœÞKp†TWWwém•(¥¹\\Ž\\.GGGù|Ïó’¬¹øá·Þz‹«¯¾šûï¿Ÿl6‹eY&‡QYD¹äÛ,†ÓŸyK6òîªv<†ò\Z$SFP˜‚–€*T¯$Àµ¹¶:²ùÂ5ƒ&À\"À’0C”*¸ø…œ½Z£‹¬çÓZ÷ˆ6GcÚÅ/6™L†ßýîw|ùË_æñÇ/ñÃ0Ä÷}òù<íííär9ZZZºô¶ìÎ…9’«¯¾\Z­5Ùl–††V¬XÁ–-[’QÝ³g{öìá†nàÏþ3³fÍÂMiŒ†”(RJhlÛÎ›Ëódý>¸™Y/@‰ÂÒQüI„J*²m‰€”-9´Rä=E¤¦B°4‚ÆˆFL#ˆ0Øˆ!šŽ\\ŽÀXäÅî–¤€HçªdP¤ù3ÆJ¥øÞ÷¾Çí·ß¾—9K CúôéÃ¨Q£8òÈ#©¬¬$CÎ8ãŒ.ÃG%L\rÃ‰\'ò‹_ü¢ä¦ææf^xáîºë.–/_žH­ëºÌ™3‡o¼‘Ÿþô§4çÚH§á”É‡±xù\nÒã§ l	¢ni%!J™(EGT7X„VßÿåŸ8uòH«pÉvx¤]½7ÃOb¼ ÀTc\nñ|‡]-\nT\n/\'lÙ‘%u4¡€­ã¨wÌàÒ>Û¶ÍO<Áí·ßŽëº‰g†!õõõÜzë­I(»+)ïÌÔ’éÛ©¾ïãyžç†!UUU|á_àå—_fÒ¤I‰\'Û«¿œ9“5ëÖQæ–Cpþg†±êÝfV¾ßHEM¼ñ°È¡ñRfGù£beð•ªìÏòÍ>_»éßØ²«…²r— T…Ù[@¢T  @iÛvQJX¹b#ÚªDYU¼½d%JA€Bë½‘Y)\0)J\nª¬È#œ1cF2•ãõcÀ€Ì›7¯ýë\Z4ˆ •˜Ïç“\\«ÎÕ>:5ÎÞ(ÎÜ²Ù,µµµÜ{ï½%£ä8žïóâ/âh…Ÿmcê‰#8çÔq¼õÆ2ÊÊ28ÆÆ4–qÐâ ŒB‹\"…¯l;…-\Z	Sdªà¥wòœwÝ¿òü¢µ¤ÒZ;ß†BÞ€…\n/×ˆmåX±±‘Åkv’©Ê¶-.ÞÊŽVŒcaÂ6D¼Èü21˜m<¢•+W²lÙ²Änýýn¸£>šl6‹ïû	œè8NIVãG.T¯xét\Zc§žz*cÇŽ-ÉáX¾li4mÒ6åÀnü#†Ú464ã¤mB-øZãkEh	båpË\\–Æ”u4nÛH¶i#¡rx·Ááâ¿ÿ\rßºïIVmÚí¸¤lÑA(aˆ›qØ#·ýôm¶ï±hoÛLÝÌ;ë›¹úž?°+pql?	<CI\r‰ HaíÚµÉ*oŒÁ÷}***¸è¢‹0Æàº.¶m\'üˆƒ˜7é¦î¥Šú˜1cxï½÷’g\0Þ}oET™¶	Ïàþ5<öÀÕüý³yaÙ$]‰å¨ÈÂ\"ôBÔîõŒèïpÍ×ÎàäñƒÉ‡†Í;\ZyãõU,XÐÁ»üä?6ñëù¿á‹gæ’OÍ¸Ñƒ)ÏT yiÙnúÞoY½ažPÏ©“£Ï,ÝÜÄKó?àÜ+Á÷þîSLr89ÏG“Eã eÐˆŠÜñ•+W–Hœˆ0dÈ†Zâ®w¦îÐªeRÇçQ£F%L.ÎèˆÒÅ5–	¼,#ëk™ý¯ËsWóûgßbÓÖÝ´uø¸VŠºÃª8kÊ(.=ï8Ž>¬ïÞŠÆãŠOLdë¶6~þÌr~>g5Û·µò¯ÿ±Y¿_Á¨¾us9ÝÁ¼?-fÄ <ùÐeL›0œõ›w1{ÞRví0z$K¯æ’oý3¾{!—Ÿw<Aè¡ñlÄD‘Z€>ø ¤ÿa2jÔ¨fî/\\´_IÝF‡¬Ÿ÷0E1ÅÒ®\"4y*•æ«gâ«g¢©µƒÖö´J3°®·°hF!FE§\rõÓÜyÕÉ\\~öXþùÁyüÅ-øµÇ°¤¹‰·¾‹´\ZN;îXÿÁ\\aÖ‚¥\\öÇ0þ‘ ªÀÍ‘©¬ª:n¼ïi†ëË´c‡†9”Vq´¦¤oÅLìÓ§O\"8=Ý±sÀá”b·®s¤1úbPª`dã\"b£•F$$Ìf1¹€¾eå>°CTb‘4“ÇÒpÚÖàXˆc“<o#¥xøŸ>Ï­_?ÏÛ¨ªV*Ëª©¯Ípÿ-ç3¸:Ž:„27Ce¿*úÖ„ÔgÚ(óZ0VÍºšûÿÏh÷\rJ¹(”\n¢övŽ®¢©=\r§ô(ð·¿Hc¼‹ÒMw·rG““€l7âzX–‹’¢4!(ÁÓ>¢Ž.»OlZ=[¯œÂw¿:\Z«u7Á¶2.ÿÌN^Ãæ]»hêÈñû§–bL¡ÕN^í$§ ¯*IUT³tEKV6 µ…1>\nÎ6kç\\¯ÎÉ!J½ÊPé:­0rãD­¢\"Ji”àŠ.DK5(;Ê,<iÇÉ½XQšŠ²I,¥Ð„˜Ððí+Ïã¯4°xIçŸó^]¶Ž¯Üô<ºº‚-\rØé:Ä3UxDŽE:Øm94U¼²d-§{„\nÑ.@ Waèýòç@oì,•û^+¸Ø…ÐZ„’v•è(t,*b´è‚4HÉY£+sˆ@p#BÆ¶9ñøÑèò2ú×ÖðóYøpën¶7yXéÊÈk“ÈFÀ*xo\n/v5Ç;T¬BúODû[˜{C½ÚÒUîQ!‘Xú¢Ä²Èç–’…¡x¿DI-ºP¸oïuCœX[–ÆˆÇæÍÛX²Ñ§²<…Ö6í€E€I¨h`rmK\\^­#ð¡àuÅÀÞFRá $¨•„§cq%B ö2KÑeU|R/”~/„@PpÄÀ2ÈíäÝ÷iñ«fB?DÙN!Ï\'ž\'Ñ³¡´„X*`È€ºþj\nöò¾}è®=¡^e¨|TJLW9I‡°\nªãäñƒ©¬\rxò¥÷qÜjr–`Ù\Z%a´ßª°`Æ¤•`©€Úr‡Ó§ŒF©¥tÑ\"Õ•jû8ô±ÔºZ-;›^g\ZÅ¤\nå›À0jhÎ;w^ò¹Ž<¡-xvÂŒD#¢pmMÛ®&¦ž>š1Cj1A{ÄXJói;÷ç/ž oœ-–ÈÝŠ½¬ƒÅÐ˜¢@ž Â€»¦ŸÍ‘‡	Û›Öcë>d)ÃÒ/C%ø(ŒÖ8—|s;GÕYÜþO EmqA\"J·ÒwîcoúÒ#¦Æ•VVV–TæÚÜÜÜ¥¹UœˆV|ô â‚E Ï°¾•<2ã\nF×ûø›ÑA3NJcÙ\Zí\Z´\0mìÙ¹‘áý¿úþŒ¨«ˆt­Jç©Ä:{çÎ%ýëÜÇCfüÞ·oä«^CC7nì’aÅªaøB·u°O¥,Œíây>\'ªçùŸ_Ë7/>šA©mä\ZÛÈîðÈ5ìÁ4î¢?>×\\4™§~u-\'ÕŸÐÑÆSF´o5ÂRóùü>àDnj¿@=2©âcÄˆ”••%;8b ÷…^`òäÉär¹ÈîœüUŒ’÷„±qìÎ\0iÛzmÔWgøá·?ÇÿºòS,Z¶‘MJÕ¥˜tì0¯«BèÀË7£%ƒï”­0: =ÊËªX´h|ðA	@\r0aÂ„í\rðÞÔx_j2aÂ„’½©J)©­­•gŸ}¶Ëç‹Ëén¯g÷Ï=\'\"Æ\"aVL“ èfó©ä$ôÅ;»-wÍš52nÜ8Dk[ÒRUU%›6m‘ íÞÔ³m›/¼%K–”\0Õ»wïæ3ŸùgŸ}6§v\ZÃ‡\'“É$’§×H¿Š£¡h§S(›\0¶a|ÂA4 4J9hÕ‡ŽŽv^zùx^Ë±Éå²4íÜÎ+¯¼Ê‹ó^¢©©	Û¶K@÷³Î:‹Áƒ\'ùc½¡í¢ö}_Â0”-[¶H]]\0âº®h­“èò˜9s¦ˆˆxž×CI5Ñ.jŠ$Ç3FòFÄH(&ÌJvˆ1y	Ã@c$£½ÕÖo­œnÛä8NrŽgÞ«¯¾*ÆÉårûYÝIj}­5AP__Ï£>Juu5žç•læŠwÿ¹®‹ëºd2\\×¥ªªª$—é@)º7Â¤rP¸JP¢A§Ñ:ƒR.ZG LœˆfÙšº~µ…¶”¶)I8å\'?ù	§œr\nAôZJ{åû[–…çyœsÎ9¼òÊ+üà?`îÜ¹É ºz+D¹WÐ¯%\nI>ª`Äh–”Þ\Z×¡T´yn÷îÝ%Y{©¶¶–)S¦ðío›3Ï<ß÷?Ö+•z½ã/~ÛÄ¸qãxä‘GØ¾};Ë—/gÙ²e¬\\¹’ææf:::Á÷}úõë·×u`\nñVžhó‰*½F’¸’|ˆëI¥RL:•öövD¢×<UVVR^^Îˆ#˜8q\"ÇsÃ‡OÚYEîghÃ¾€BW’ÔU86^€D„0mÚ4¦M›Ömea–$ôLb%þÇÞÅ×,g¯³P°©Ÿyæ™,=îGqö^WÑŽ’u	½—ª¸ýbÝO³ÎûTãnq’0”¾æPQÜQË²’\\0 ÄŽÛÇ º\nuÛ©«W¿Q¢8“¸§Ôæ\Z›*ÅŒîNçl*–²xÀ‹ƒx]Å¦zS~œ\r˜\rx\'Oüw¤CÝ¯¸üØ&·!-ZT’Áv(§æŠ¥Û÷}\\×eçÎñLPO‘ÞNû¿Ò^!¬®®Æþ+3[ÿßì÷ÿïDÿe˜ú_EÇGaö¿\0Û½ûËKêÊ6ŒßÄS¬¢âÍÅåu.søÂ¡ ¢îŽâäØx‡‹çy%[ûôéƒmÛIÆvGGGbÇ\Zc8Îó<lÛ¦²²Ë²Èçó´¶¶–ì¸‹©8®×—ç“Æ¯Ì;ÔÔ-,öqŽ|PÖ®]+Ë—/—±cÇ&×\'Mš$Ï>û¬lÛ¶MZ[[¥¹¹YÖ¯_/O?ý´Lž<Y”R	—N§åæ›o–Å‹Ë®]»¤½½]¶lÙ\"sæÌ‘sÎ9§º1b„¼óÎ;òþûïËÊ•+eÊ”)I£F’÷Þ{OÖ¯_/·ÜrË!éo|ÔÔÔÄôÁ;âHÀsÏ=—à“\'O@†\";wîL®ïÜ¹SvìØ‘|ÿêW¿*€Ø¶-òüóÏ\'¿yž\'Û·o/Á7¯¿þú¤Þc=¶ä·¹sç&Øî˜1c’ë?þñ9SºNu_.—Ã“œ¾øÅ/Ò·o_ÚÛÛùÊW¾Â°aÃ=z4gœqwÞyg’|7ß|3Ó¦M#ž{î9ÆÇðáÃ¹à‚hhh@D˜1c\'œpBR·ïû‰Ê˜:u*çž{.y<ííícøñãÒGéåC\"©O=õ”c$›Í&’zçwŠïû²gÏ9ýôÓ»}¶¶¶V6oÞ,AHcc£Ô××\'Èµ×^›¼×tæÌ™È¸qãÄó<	Ã0‘þ…\n cÇŽ•|>/Æ¹ÿþûJ?‹ß¹Z|IíŠbé}ã7°,‹ÊÊJæÍ›ÇÛo¿Í¯ýk®Ä&Ó¶\0\0—IDAT»î:†žÜ7hÐ ˆRŠeË–±uëÖ”ÑZ3þüä\'cÆŒö‚BJ)fÏžÍ²eË˜<y2çž{nI>ÂÁrtö\n2¦Ch1ê>wî\\î¹çòù<–e1~üx.»ì2~üãóúë¯sþùçÑ+ãc“(~­hŒ\0chiiIÀïâwøÇ«ýºuë’­IÿøÿHŸ>}’i°À•ýÁ£‡Œ©]Á€arË-·0iÒ$¾ùÍoòÈ#°nÝ:|ß§ÿþÜ}÷Ý\0‰yƒÌÅe(¥(++£¬¬`ŸÿïE)Euu5O<ñ›6mâ¤“Nâ²Ë.K^OúqÂ$ÅÔyCZ1rI-Æ2cF¬X±‚x€éÓ§3vìX–,Y‚1†¾}ûÒ¯_?6mÚDSSS2½û÷ï_€Ÿxâ‰‰MºjÕª¨#EÌŠz3fÌ@kÍ5×\\CEEEI»>.ýE%µ3r$!ŠÏþó<òÈ#L:•þýûS]]Íá‡ž0!Þ½ÝÒÒÂc=†Öš¾}ûò«_ýŠ#Ž8‚t:ÍÔ©S¹ãŽ;ð<¥?üpRO}ˆ¥÷ÑGåwÞ¡ªª*ùý/ÌÃ²,QJÉ“O>¹ÏêÝu×•Ø¨6ll6›\\›1c†@dÐWUUÉ‚’ßÚÛÛeÓ¦M%¶èÍ7ßœÔ;vìØ$›äž{îI®_|ñÅbŒIVÿýèG‡ÜNí–©ªÓëæ{z¼ôÒK†¡´´´ÈèÑ£“Ž?üðÃ²jÕ*immcŒ´¶¶Êš5kdÆŒ’J¥Jê-++“;î¸CV¬X‘Ü¿cÇ™?¾|îsŸØ›®3jÔ(yÿý÷¥¡¡A¾óï$fmÛòÔSOÉæÍ›åÃ?”Ûn»í 0¯;Þ|$Scð@Û¶å¸ãŽ“»ï¾[ZZZÄ#+W®Ë²JîËd2R__/&L#FH:Þo¹étZ?üp?~|’ÓÕÉd2R^^.™L&¥”h­¥²²R***’ûO‘Ô˜I=)Ð²,Ù°aCÉýÒ—¾T\"5®ëv[WWí< u=ˆâÄ¹CÅÀî˜Ú-JÕ9\rò£(É,[¶ŒíÛ·³fÍ\Zzè!^~ùåêªØûquEÅK1Êµ_ã»è¾âˆjÜ§CéH6ØtÕ°ž4 nx&“!ŸÏ\'*Î\nüŸ@555OÍf³ÀÞ×-ýObhLÝ2µ8ù`é@1uþí\"3cú¹j¶Íï\0\0\0\0IEND®B`‚',1,'superadmin','2013-05-25 03:45:54','admin','2015-05-06 15:30:59','sdf','For Test','For Test ',0,NULL,NULL,NULL,'0',NULL,3,0,1,0,'',NULL,NULL),('41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','http://cas.demo.connsec.com:8080/demo-cas','HR','27d258510c99f7f9b3301292b11d72c4','CAS','‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0E\0\0\0@\0\0\0L@ºš\0\0\0	pHYs\0\0Ä\0\0Ä•+\0\0\0tIMEÞÙÓë¸\0\0\0tEXtAuthor\0©®ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0Àæ‡\0\0\0tEXtSource\0õÿƒë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨îÒ\'\0\02IDATxœíšk]Õuçkï}Î¹·o«»õlI	#@Œ0Œ\0;€xØøÛ”qÆã›xjìrò!±Sãq1E2å”\'¶ó°\'N°SÄØ~FáƒcF„dˆ‡­V¿ï½çì½Ö|8·õ¢ÉL>Í|èÕ­î>}Ï9ûü÷zþ×33æqÜÿëüÿˆyRæÀ<)s`ž”90OÊ˜\'eÌ“2æI™ó¤ÌyRæÀ<)s`ž”90OÊ˜\'eÌ“2Âáfå•¤\nâP‹D-É] øœdÆÓ/¼Ìã/ìæÎGvðÄó/±ml†‰½{1\0ïÁWýKŒ“9eÍzÎÝtg»Še‹‡03œs¨hçJ{¹Ë¿îØIŽb*˜ËÑq*´cbÅ¢~Î;i=Á;0¤^¯‚TV‚AŽƒ13]±uç³l›àé»™í\Z9‚ÛÓ¼éÈå¬^µ„áo^w,}€•˜!‡‹L¦´ÌÔ!Ï<ŽÛ~õ4×Þq??ú$»^Û¡	yQ¯Ì¹ú“ˆ ÒÉ8$…ŽBjsÍx\'WìC”1áE@g ÞñÞÿþ-n½í.( 	ˆf@ú`¦ÃI§Ä½_ú†r%© ÎpêH.QughHòÀ3{Ç¹nËÝÜýèËl}nãccà2 «Ù³^ 3\rYÆÊEýœ|ä\nÞû¶Ó¹â¢³hú¹,EgŽŽ¯È1úó&¿~ò9®¾ñV~xÿãÐÍ wøå‚¥>ÕV¢Z_Ä	yJDŸ!8X8€Ž°|ñ0fµ5Šs˜8áž_>Æ½=ˆ_s&UMjRð¯©Õ¤ìL3>1Éà’~À FŠ,§+¯~ù£ÛØ5:\n)ƒf,èÇ{‹†Š &ˆ¡µXuxy*òòc/ò‹Gžâ§­çèåKjR6APSZÞq|õŸnçškobo7‡Á%¸Á.”*],vç15!Np!Ã’Ò\rˆ”xšÄÎG.mpÑ¦ àÄafh¬èd\r¾ò³Ÿ31Åº%h†wÉ’jŽ‰±Š©ñidÉÌ‡bxŸ3Ñ5>ùµorýí÷C>@^,\"‰Ô¤%E;ÎyÈUCºj`¡¹\0­f8{Óé/^HJeMŠˆ\0µŸ¦¤x/ìžèðû_ÿßÝr,ZBh*©3Šf8ª$ó9ê<I­v˜P5AE&r4uxËÑË9jx€#Á9ª¤dyÁýlåÖžÃ÷/FËI‚S¢oa±ƒËK¤SaYÎHµ}Sû€á:æ™ (3eÅ§ÿü:®ßòÜ¢aP%•3¤°€Ã!Í&•yÒ:Þ¹ÌH¤Ò}óO­<§ê´kRRª™oSÒòžÝ#3\\ú§ßæG¶â— e…fr¥á\\gBU¶!NC£ høFmÓÎeTí.ÚñtªLséo‡%#Š’ÄbÄ²ÀŸß¼­fðE‰Š¸DrëxLG5©Œw3ÐTá\\‰M®¾é®Ûò ~p1)XÄû@(íÏÑ‰¨&€‚®Hòi4!Áª#Vó¾Ó7¢fH½˜bZ›µ%Ú•ç¿ñ=xð1ü²ehÙF-B	Ï<©3ƒjâ­Ç®á‚“×²yÝ1wÜ±.ðÄ¨äž™`ÛÓ/qßöí<ºó	Î|ójshªð‚~ô~öØd v»¸F?ÝN.‚ÄcXmÅæDÔŒÌg<½s„¿¾ù§Ð¿€d\nš@ŒD†}èÞÝ·t	N9™£aQéZâÕ‰O<û\nÏ¿¸‡™©)Þ¾éŽ$¦DÂˆ)UêÒÌšüñ÷oäº;ï£¹híÉI(bøàIcc¬Ó\Z>sùÅ\\úÖSje\0(u¦PÎX3ÌkVðÑwœÆžêÈ…”Ž)_ÿñí´Û>¤àIVBAM‚ºÚ£]/æÏ¶»0;uŠHà†Ÿÿœé©I¤±ÓXg—Þõµû\ZŸüÀyüá¥³zÙÐëj‘ß¼6ÁSÛwñÝ[oçCçžŒY­¡Q“Rv»­&<úöýÛ¥+°Rñ¹\'y VäE“rdï;ïl¾|ÕGX»b¤SU›\\Z8zÁÍtDqUM,:’)ª‰,/¸këvn}è	ÜÀTd$BYâRhÒKUuzvÂÎ½ã¤ÁA•{ž||˜a\"uá<63ÍE›Žã+Ÿ¸œ\"•Äéó‘è#qôâÀÚ¥ÇsÑÇ!¤I|èGÍjR\ZÍ&¥ÂçÿñöuúðY¤›f°,«pyFùÚ88÷\\þî3ÿ‰¡ÂQµgÐ`8ß ÐK¡R?„`–7À ÛIä™P‰’‰‘¾ùÃ{éVžÌ+êXûX¹`O¾¶·Þy‘ÚRœc×ø8f¹F÷FöŽÕÁÔèåg©“†%å¬\r(Ìh—3ÅB¢Ï®NIõ¥.™æˆ(É	˜Ã[¯ÌwÞó£ûçŽÇ¶!àº3¸žo‰ÏÐ™6\'³š/~ú\n†\ZMÐx_Ð§9ˆ .ÃÈ@2Lr\ZAÈr>á¨ðÞqïã;ØòÀ¯¡¯…jÄ4’`²ÃIÇ,çÒ³Î„n¹ßÌj+|qt’¤	ÈÛ7Â¾}£€Ÿ-nAÁTáùWÇ0ç0—Si$:«¡‚ 4Ã¤“\0âA\Z5¡ôHét:\\ÿ/ÃŒ?MÕpˆH	¨GÈ¸úª÷²v‘§J‰Âedä Nï\rç­Þ0\'uàãÀzSrd*tÔóõ[îbº[áDÑÜ8\np\r.{û	œ¼jp¶B¨ã‹8áµ™D7)fÒPÂ`Tk\"ÄpNêSŠŒ·ü7>¸¾F‹‚„hD\\]¥+†ÈTIà=ž/‚óR“²ãåÝüò±­H#G$ŒØØÄ>Þ¾açŸº”ÞÕ…—aõÏ^…:ëþ®·³Þ{œs¤”lc(øù¿>Ç-¿z×,(KpŽ®ŽqÔÊ®¼ð\"Bæ Ï@ô8ˆPÅ6;_ÞƒYbåð*‚\ZÁ{Ðz=Î{È\n&ºW~áüÉng<9šAP*:Ö!ÒW‘ˆûcûŸÇjRÜþ¯ŒŒã›MÔ´ØÀ¬\"kd\\væf­*DäßüÔ)´Gƒ*âÜþþî‡·Ó.+‡K†ó®îEÚ“üÞ%o£åýÍ€ø³Õ¶eÕåù=£8ç)2cã±+!– ®¾wŒÄCBdJŸ»ö&>xõ_pë¯v’ù&Mß TŠXQc¶t…¸çñ÷¡(XÂp850XÐßàÝ›6b1bÁ#‡œ>7D¤¶\Zç¨Û“ˆw»Ÿx™ŸÜ·ÉûÑ,ÐÕ\nU;rìê5|ðìÓˆª¬\\¶˜f–ÍæøÞO*±g¼]ïh>ù®³Y>P«ˆs—Õå˜â­ƒ³\nZýüóÖg¹ôê?ãª/\\ÇÖ—öá\Z-P‡FÅõÔé­[¤ç>¿~öyÈ,õº]3\\!*+–.fõâ–JÔ{Ìä\r¨èmlÏÁHªL:vùö-?e:	®Ê1KX#G“aUäÊÞÉªe‹i[ÅÚÕ«È²¼Wü8ˆƒJxñ•}\0t;]6¬ZÎg¯ºªšïÁêc¾!øþ>º}×Þû ~öüÕmw\"¡ \'@RL8$8€g&º=Ÿê‚P¡Ê=T‰KWâœ`y Sw˜¡½¥ ˆ•TNˆšð÷ïØÍîÿ%ô^µ:jbåÊ{÷©˜)\r—!ZÑjzNgÈ©p¡Dž\Z­ãKæHQøÝwÅç.»€4>FRË\n‰¤†¦\n¤\"ÅpJÑìc÷xäS_»OÿíM¤ƒ$TJe†YªIiOO×MœR·±˜8ÐÄŠáe=\\Ï¼þO„Ôq%#˜âÅ/|íŸn£Ó.pÚBQLêŒ@9ÍeçnbqAŒ%•‚xÇQÍ>(B?)\n^=d]¦ª1Ä\"Áw1’\"tå%üÍï_ÁpšDÇ\'±¾Åˆ4ð4ð© Ä?¥˜E|Pðµë¾Ï—o¸r¨ŒÌ^uÌjÅLÓ)!½t²ph˜Õx¿küßÀ!e\"øŒ»žÚÎz\Z-Ì\'Œi-Ëûó©Íg’‘Ñ Ï;2ïlõC\'¢¾Irý¤T 2ÄK/—LNu)¼”¤àª’O¼ç\\núãßã’³NÀF^Ä:“¨÷¨@–åTV‘¨ Ëp‹WpÍ?ü„?ò|‘¡•aZÕ9\0Þ÷ü±÷ÀÁƒ«-bïèè¬CôöàtüúÏlQ¡ˆº8þú\'÷2=ñ^AÆ!WÄåÐáò÷žÃ±«—1Ã>v—/óÌÈs¼0µ‹æÊœ0¬¸Æ(äÓ$ºXÙój‡½ãb9¦‘”*±SrÖ†ãø_×|šoîw8eí\"ljf%©™Ó‘ˆùº]°ªBòí(|ãÇ?£]V8*	›-óIŠ9©](„Ú2ªÎñâÏ#âd)Òû›ÕÙ,e=^#y^ðËm»¸åO!Í¤2$/°ª\"u„e+û9ñ­9ßÙz-;G·1+T\rï§Xµ¡ÁÇ7\n T{•í/LñÂHAw×ÊN‘!ŠR­ ¤\0e*iXàŠÍgòŽSNà[·ßÏ¿s“íi$óXŠHRBpÐ!5šÜÿÔìœØÇú%ËHªxëÉ‘!Ñ²ºwH]Ìe8/(Çv¿B\r1!†DHqµ:»‹PiûYB/K}åÆ;™šš@2’µ°*ÐRN<uŒ68{ñÆZ¤Ê¦ð¤JÈ\\Ž½–óË—ŒÎøYªb9?ÐæÎÝ?¡oáé¬:‘ñXRL|½.…Ø‰µøoº˜£—®àS_¿ž1í\".€sT±Äy˜2>Vòìs#¿x)!ehˆ5)k–-ä7¯L\".IEÙ¦\ZüfÏ>¾wïC|tó&:Ý6©Èpx„º&%$hÏ±@0RŠäyƒ[~„Ýw¾	jM¬=ÎQÇTœñ¶&«ŽPH]RJ„ÂHÚNÁúEÇqÁ	çsß#ûøÚ7¿}CP	„)ˆ	l†¿u1{FGØ¶çvN<rK[G‘bIæòºœ®ÎzÝI>¼ù-lÙË5×Þ\0­VO6ÍêI‚dM^Ù=‚\0ž\0TuL9kÝÑ01†¾¶\n\\mÆä”1ð¥[·0ÒíÐpM‚Ó„WCTjöqu`5©ïée7ò¥Ÿþ‚n•(ÌaÕ>N?kŒ÷½?²bÍ¨t»	‘®ÍÐŸ†¸ôäð±Ó>É	Na8_ÝŒ\"/ÈƒC¼#oöÃtöê\"6»™#W®ç‰ÝO³sbš\'¢UÄ¤8 “€å-’*—y|]óHÖÛ:ë	^Dú†š€¨=Â¼ÿÌ\ruÓ$ìS8°ªDúxbÇ+\\ýíèàèß™F«I’ÎˆÄdTjD5Ô9œübÛ³Üõ«pý«Q™äô‹àœ÷‘\rd´K¥#;±Ö(“)£Ï-ç·7|ˆVüÅTš±¸UàûU5¾Ä¤$Ù(4fxé…@ãâŒ5§°wr”]{v†®.’Q™áE˜ì´ñ>ë)sôá\ræø£Vab˜³^ÞÎZwo>~-qzBÍ`WÁw±4…0ÀßÜ|7ÿòßòèÎ1´5@h, Ëû>O<yVë›¢Ê5ÿx¤	œ$\\Ã3±§ä‡×+7~«Cxu\'¶Ö“Æ”en9Wœð	Þ¼äT´cÐTœ(,£Ñð¨(ÑhŽÅ~pƒ<7²‡NRÊX2/ç­GnÂWŽéé¢Fº©‹H¢™y&þèïo!õdŒÚPÌCJ,]¸ˆEÑnÝÂˆÔ1eÙÂÿñÝçó«¯_yT.T^ëÖ¼ú‡ùîó³G¶sÑÆã¹øÌ\rà¼\'˜’F§Ú05Âð’E<ôäó¸‰:†vŒm{ˆ-ú]ÉeŸü§4½t}…qÒÂõTI±¦4‘‘hö1Ð\Z`ztòfÝ<ZD™a:kpÃÝ÷ò7?ÈU¿ý6,]À‘kŽ¥UH…Ó]ØöìsüÅ¶pë#ÏàúZhêÔ.À»’4ÞáœsÎdéÐÉJ<õØ$\0hêrå…gpó÷s÷SOk	¡tu:Î:¤nÁ\0{cÅ÷îz˜ïÝö\04[„,%¥-\n#|þ—òÔ/0½ošlá\0šº˜¾åI>xá&Î9ñ8’Vœ»ú|PP”àQPË1sô…ðŠË‘ÔÆI$Y´ä¥Ý“d}\r¶<ý[®þ*K±fõ¬XXÐp‘Lã£‰Ÿoš‰™\\ˆv_KU	y¤¯ð\\uÁé„ª”‘ùº=ì8¹ã¯>óQ.ÿüWøõó“d-š¹P)TÚÅ9EÍƒkà|Ð„«ó†„6o9åN[·–?ýâ7a #¥H&\r*5RÈÉµÃÇ.~–fÐÑHfõXCguV@Äh5\Zô÷5!N@ žìV029w-™²ÀHeŒl}b=×!UÐh5qCƒhU÷_âr\Z±Kô}töNò;—lâ\'MJõ¦ÌVë½¾9PÎL²~å\"~ð\'Ÿáä5+©ÆF™ÑD-pˆöõd¼Dª*ÊXQY¢ô×èÃºS\\qÁ™Ütï#tÛ†+§‚• Y¼ëÔ·pÆÚ`\'u9ï¼ÇfõAUé_P°dÑ@Ýå9¨‘,AŠL·;ŒO3ÔWO÷\n›À7•0”áõ–-&+–âR-Î\"FtÌäM:{÷qáÛç¿~üýX¬z…¨*@‚ÌS•mÖ\r/æÿów¹ü]gÓèLa£û@r’z|VKv>ddEŽóÉ•Îø§u<+VsóC’\r6pI	.Ã‡çJš>òŸßw>ypDb!µ¶\nëU9³q0%ÅÏ¢M|UâDEŸò,§c¦ÝfÕ¢¥H‚¤\r¼ôc1C;‚–U‡>¤„bJtt¸`#×~ö\n–\r4Iø:\0TšzY¸#\r2 •‰ã–4ùÎ\\É=›Ïâ+7ÞÁOn§c3t§ªZZž$ÔæÚpPE.=}3×Ý±…‰WFaQ€²\"ÎöŽcûxÛ™\'sÞ)k©bIå`‚Ÿ`Èlw]/nV@öAHí6iŸëMõrR9Cùê%ƒCýXYáúû0ø 8¯à±iÖ© [-[Åÿ¸b3ŸxÏÛñN‰1‘9ÚþVï\0)Þ{úöQÌj=dóÆulÞ¸Žm»F¸ãxøÅWØ¾gŠ©é)ÄyD„XU±`§ž}4Ïüð>xÉ¦^à¨²@LEÙá¿\\r1EÔ2<9âzD˜? î×¨©\"®[Ïø;§p\rƒ*y¢O0=Î‘G,gíÈ}ÍŒ™N¦¦êI§¯I$ƒFàèe9qx!ž}\Z>ï–´š½æÕ‘…Zo–Þe–±\ZM	êŠ6’Ð*’‡¼ö¹Ùÿ+ŒOLâ{RC,KúrOæ¾¹ŸÚ9P¡1!¡Ú[ˆØ~©UGý\rMÓøÐÚö^ë²ïµIvŒM±gb†ª¬[çëÍU\rÞ4¼Œ£V,©Ç°@J©Žî\0!‡w¶ûI¡WÝÍÎ=¢Õ£4õ›Pu8ÉðÁõ2œP#VµaH=¨«_¹PA¨™82\'=âRrÐ½{Ë¢£©.¸T1|=Á³Z@Eó Iãc—¤BÂî~ÿ¨ý,³¤²=;£7Nõ¬Û)j]Ü!ûeÄ¤ž©ˆõÎJ/xKd,G‚ žÒÃáÐÞŽ¨AL«ú Ì9ÔÕ‹ÕZ@JõkÉ×\"»Ñ{.®—â³Z8rBfà0a®ÛÏâuo2‰)ê\\ýZ–\Z<\nxóàuJ\"á©Í4¡8œÕ³â:(@ˆR—è’pAMô^%xÃÅ¨Ø~×ª|Âœâ\\BÌƒ:*dj˜xÚ¦äâÈEðN;0gNZ[®‚ˆçP‡sXJïÏÞy&=w0Áî~³_m<gu\'g½÷Ö\0×{å«·HÜ¹ïá˜ÕcêZ¥g…Rß[¸¬§w9zcRo\\zógéÍ¶é½ù$ˆ8xÉ¿ƒ”×¯tö*ûüa9è¡zÒÓaìÄàþo¼+Ïyù×Áçà¯Ëa÷æÀv{ƒã‡ãuîÃA¯5Ô+˜ý]™¤üØ‡;ðÐ‡Ó8»{o`)=BfSä¡g°„ƒIŸ%ëmC	Ø\r¡.7äßé>ó˜ãzNÌ“2æI™ó¤ÌyRæÀ<)s`ž”9ð¿HI±’zo¸\0\0\0\0IEND®B`‚',1,'admin','2014-09-16 13:56:03','admin','2016-10-11 20:50:57','','For Test','For Test',0,NULL,NULL,NULL,'0',NULL,13,0,1,1,'org.maxkey.authz.cas.endpoint.adapter.CasDefaultAdapter',NULL,NULL),('525d261fa3b04d19af0debabbd5a1e2d','SalesForce     ','https://login.salesforce.com/','SAAS','e8297041ca3347987bc739a2c9f33e7d4909881759ea590b0c091d4f645202e71e698f87c64032dc548d6ec7dc3c4863','SAML_v2.0','‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0O\0\0\0@\0\0\0[b*S\0\0\0	pHYs\0\0Ä\0\0Ä•+\0\0\0tIMEÞ&ëëR²\0\0\0tEXtAuthor\0©®ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0Àæ‡\0\0\0tEXtSource\0õÿƒë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨îÒ\'\0\0 \0IDATxœ¼÷¯gç}ç÷zÊéßvûÜé…Ãá°Š\"Õ)S–%[²½ëUÖF^`±ˆÝä§ý!	`{ÿ€ A€\0Ù €ƒ7X¯¬µd­Š©BI¤š%±‰}È©wnÿÖSŸ’Î÷Î%%6ýÎ½sïýžö~>½	ï½ç`Íÿç8n.‰CÜúˆùÄßòYÁÛ×­«­CÄ!Âùù5-Nx¼·(wË™B‚8©ðH@¢Ä-w™?‡ý…{‰Ï}óMü·i¿ßüú«ŸýïZúÖ+[Ó^Í\npòàf…C9‡‚ö)n+H-€E1%‹¤ÐTuES[d\"•Â	Jb¬CXK$à”%4%\"ðHåÀÀîë¯±´~’¢™f˜Æ!¥jwÖqc­†FA5ÿ•4EƒÂÜ€­¶¯4’ÄÏ/ áA	8Ø3ñwC©oýAÍ·ÏË›àµ7p,J¨v«Ü|›¼lùzI¼Å6†(‰B\Z ²5\nE¨$¡’(g!Ï¡jÚ›I0†kŸ~øc~þì³œ»÷.>õßüKXYÀäu@€<\0n^£¸‘ø…|û¨Í?\'ð8Åõù·“îßc½òð€­@K‘ƒÇ!h¤Æ!‘\n”Ò/nÜL:ç;òSÔLë’R8¢^B B\Z\n*[âë†ôàóÓ	\\Ù€-žüÚ7¹úò«ŒwvÓ1ûe\rXB ±Æ`”EFÌ7Œ‚H œo¬@€×  ðHœPhÀô ì,xZãßïÞO8¦½š„@ßØ75q.shYCù4y€mÏi€Ç1mfL¦›tMØ4DµÆÀõ]ì3Ïóìcßåâ“ÏÒÖ,UŽãIÂÅÖ›?ö½ÕÏ\"³+\Z<\ZGˆíŽ;á0$‚ÐË\rYŒ8h¢•ã¼åÂÎƒŸÓ¬p n=ç‚g‚\n!<B¨[XRÍ	ìíDê\0íi¶Ýxj*l¥I‘O°uE\'	é\nOWØ¶ØÚfòÄyæ¿|§Ÿ\'«jÎ$=V\ZMÔxDU3” kÇÏ¾öU>ú{iNÅÆ	‹—­\\ÄY”0(ióm¾)ö%Ð>»3IÝ€Öè+\ZÎÌµ‹|Ç\Zã\"˜‰!Ò+B´²Å	¤Ð8\"XqSgÌ1»Áò¨…À ê’¨jæÚÔB^Ã¥\rÞ|üqžúÚ×¿~ÃRs÷`8/ñ{ûØÊ HUŒV6Žxáç/sõùç8rú\rƒEx7ßA‡Âk;—c\rBhà…Ç	»Å\Z²ÕöÎ·\ZåRÍ5ºä¢w<ÑÊ†X(„Ph¯À)ð-v.]­hFÂ£% ±L¦;„µ#Íú ll±÷ø÷¹øìÏxê[ÑÅÑõ–µÎ2IScó\ZcQ–ôÊ8”U4£!vG!Š§ýG~ý‚žkwËûÖž2$$ÒãA#^û¹)â\Z¸i²$ÔME^7x$*HÂ7‡!ä¦¶}Gà	$!A„$@ÁÁ„: ¸¹¬ñ8¼0Xe‘Â!½…YÁ 1à¼y™×¿õ8O|é+ì½y‰µ$!6o\Z&¦bfj”mP¶A{Kä\n–”%­àtàˆ…àHÖ\'Y_æ«?}–êÕ·HV˜ÅvÊ\\69À\n¤£\r7ÔCà-š\ná(AXM™M&X™u—a«ïQB ßÊ½žB¢¦ž4MÁŸ-{dÈv£¥‚i1D‚Tk4ïršªDMtÀÞ”§žåñ¯}ƒ×ŸyU\ZºQÂ´2t{}–®‘ö3zƒ.NXÊ*\'5r²C°}+¿Àz\0IPN\'ø«’Õžæé/~Üý.êÙ>ÙÒ\Zª\Z“OÑK=0†¢iqB„GaÐ®DÛÌšÆ»\\xé%j6Ç5ïùµßF§K\\Ù²¶0huÞßÃ¾û%ðpÆv\\£´Bd	(f3j×v#Fûû¬,ôXÆ»W ®H»9ù[W¹øü|ïËòôž¤Ÿõ8wæ,Ççø©“=}’ðÐ\nZDC\'<ÔDŒ·Øú³ÿ…+?{ýI6ŠnØ¥ÓðÊp‡‹ÿ-ïùù«,<x?“á>aÖ%ê&èXTäeA·»HƒEºŠP¸V#Û\Z®_¢Üx“ý«o¡ªŠ(ì°ô¨¶®‘\rÖéEÎµ:ã,}à	ù° $ ag„=ý~‡M‘ï±ÜaªŠ^’Á¬fïKßà\'?ÁÎÎ./¼r8írïÞÃÝ÷¾‹{|Ûnƒ^pX,jqÐj¸@ƒuPFÆ`*6G9eBGøPR•aY±b=ÅµmžýòWyà]÷!MI£R*JŒo¬$ítÎz+Åp“rç\nvï*ÍöEÜð\Z‰(YÊ:Œ¦C¢X±ýüs,.ŸæPï0EåQ±˜;ïÐÃðÒ…„º†F\ZºK}<\rž[ŒH‹L‡­¦¸¸ÁäéŸñÒOŸáÅ§Ÿf{kÑéÐ__ã¿÷9|è!B8zÉh:Fv(•PS 	¬G8A pW^ß ™B°ÔAyË¸Ü§WL8‡ ¯}û»<ð™O‘½ënŒ´äD¤$‚r¸‡¬-ÓÑ.;W.2Ü¸„ÌwXð9=³(¤t\'¯*vö¯2}ýe:÷.6Æx9÷Küß\râ\rÊó@‘W„q@\0Ôfã}ºÎCÃ³¯°ó½ñ³o~—íK(¥è%	k\'ï@?ÂÿèŸŸ>«+à<y]Ó43|˜@‘)\rP;‹‚È	”•­½5®YW}FJR×\rå<eŠ!ØI|É+_þ2çn?‰%Ý¸C¤(§\\¹¾ÃôÊ5ªÉ„ñxF]daÈÂÊ	z¢\"®‡”ù.³­ët»]\"%YLBö¯^ sò,J%à^Ê»°÷tÒê’ÝM¼™±²¶iJóØ÷ùñ_|ùÖu&¯]&¶’3ƒE.M\'©àÝˆSðOàÌñV	J	: Í$\r‚‹\nå Ð\n­%jÆÑ©^¤AàÐ!4¢Ä;HLÍR[?ù)ç®o–™Ùa¯1ìnî1ÚÙ\"ªrÒ8ayu§¤qF7ÈD®‡0Ýbk4&Ò!ÎƒÃál_„£g\0‹÷·ø\rBà½ÿÿR¨²w})=ËƒTŠñ£ñƒ¿üOü-­gÃF¢â„›ÛL–RÞÿÏ~Sø9L£…‡ Æa\'Î9‚(¢Fx…­PÊãò\n¶¾$®jèzF¾½Oä$0LµEÆ`šƒ$$¯†×®â¿ó=Ä»ïe{:c«2HŽ,tX9¹Ê`u–‚H°&˜Ò(at…æâF¾DIÏ¤Ìñ:bcãUÖßûÿëW‚€5ÈXÂõëL¿þ~øõ¯rñégèTçƒ„õ…Uù€KW¯»†ÎÑC,ß}š¥÷ÜGq}k#Y‚¨=¨•EmäÄh,B	R\0ïñÜF°¤#Œ‚(\"o\Z„’$iŒh*šÆ7Ž%\"òí?øü_qp+ú–WY;{Â”‚\Z*,FFè@c,è²AæScÑÎ±ØëÐ#¢4csã\Z+»{èøÐ\\å¶qEs ouùn_´RŠ¢((7¯ðÌÿý°tõ:Wþú{1GsÆ3hJ\n/‰BÇ†š±|î¿ÿïàÜ9(ŽfÇ(7ÇÄ«KÌŠ™Fi	bE½·O¯“µZ©(ˆ…i#®¦„]˜ìQŠš0ÉŠ€hæ	Bt\Zé\Z¨$²¬W6ö	g%§<ŠxÏýL6v ßÁæ%®ÑÑ€HJ„mc”R;d`¢†|ÊJ“M\Zl.@A/è²}m—õu\rNà„aPJµ@¶‘œwà5N´û®x;RŠ¿}ü;tž‰÷åëzq„ê¥Lí§@5I7¥”\rw}ìCphŸÄ—€ÄKÔÓÕK˜63:qJ9Úe¥0ÁtŠ»ü&¯¿þ2Û[(×°˜&JzôœÀNv ÎI¬%qi[WKxE(¾ªXº\\M¸úâÏ¹í÷¶wè^¡PYé4®¨qÆ#u€Šž(1õ„À7èZ‚­	ši\rxÇl¸C±³I¼¸ŽˆÕÜ‹´¾¹º|¢•™\0J)trÿûÞÏ‹/¾ÆÒ‘£”o^ÃOgÄV2,Æ€\'DÑ¨.¤)w=ü	èt©\'²5ˆØ¯*::B{Oì\nâº‚«[Ô?yŠoýùÀMF“!M9AYË–÷\\²‚ÌT,º†¨,É¬\"Bâk¯æ~;5ÎåD•áòóÏsÛö>,/ñJ‘Wc²(E¤YÐd\rõ>˜	M=$NZjÂ[ThÑ‘EÙ‚bzáåWYÏ2H{8cpè6Vci ç1ß6žho¥<Ç|äáß`ãÑ\'í×ôµ .Æ¨&&\nJ‡¹aX9VîºNß2Ä®FjMá$ñbŸÂWôƒ\0FûP–¼õù/ðÚW¾Nº±IÏ4”oeOà=‘uha¨›í ðŽÀ·¾uÇ‹u–$¼˜°˜EŒ¶6¹ð¥¯pú_ÿkŠ­}’#‡(ò)Yä¡qØý=l]PW*3!Óžro‹H4LTIABI]ŠkoÀÑ£0ˆÂcÚøÚÏ|çÚX–p7†tH¸zŒåãgyý¥ïð¾~ŒFiK”\r„#ã¹ëÝïƒ¤:A¤·èP“›†‰gj¨+Ê\'ŸäÇŸÿ<é…·8›ôèÕŠL…³MÑ\r4\"„B¶bñ`I¡	´¤Vµ5Ä\n\"}\ZmÍ³_{”Ó¿ñ;$·ß…¯ËŒjo“ÑÅKì^¹ˆ™îƒ¯Ñ#	AY\Z‹g,¾)ñ…#%=Sì_Ý+°ÜA&=‹F©yˆ¾¶@U*¤÷¾µ¦¥†#\'yð¡Gðý¹–4Ú1«+Œ©Èó)	¹×ôœbuÔ¦‡dMu@%&\"ŸŒAIžüÁl\\x•Ó‡á‹)ÚVDÂ“ÄQ\"´¤v–im¨#¨b¨CÅL„ä:\"C¦aÀDÁÐBÜU;–<èÍ=^úÒ× îÂ¬o\\àê+/RïläûôšY™S]»ŽŸ–ÄDx€Q*\0ÓÐÁ± fBµuÆÛ(i°8, •ÀÛ¹¶­Q¸ÖHn\re	*dù½ïçØ}÷±ñÔ9œH¨!Íb¬„QcqaÂÌz²¤ƒf® µžP[\"$ãqNØË‚fS¬ÐK¨zé²WSQ›#=:\rIƒ+vì>Zjº>E‰>Pä‘‡¨ÂæŽN¾¾s,Ö–×ÿç7÷½fc¶ßº†Î+/Ð… ’– Ø™YŸÈŒ¦4B¢ƒ\0ìáR\nzÚ2Ù¾„Û?B²~G„ )¡-‘iØ›ày!y\r\'oã¶¼—\'žû‡ã3¬)ªŠéÔbâ>q¿Çþ¬$ë/àÔµ¡+%Â{\" sœ!Îz4Û[¿÷~ººÈ3{›\r\"B‚ˆ0ÂS;i<¢ñØ¢ÆtR²(aAuñ²Ms¸\\&	„£VC\"ß²nŠ\"žæ;CÊÇŸ þÍ_‡ÊÓä-C¤\nÙŽlÃ¡C‡IÒê& ð1³:\'\"² ÄÙŠÈzB’T£=ôtDâk„oóè|]×„ÎAäÁyªªDýñÿñ¿uÎÈ$ƒ½=’^ÂO|7Ý#Ó *‡ð\Z-óÆhÊ=¿ý‚ã‡‘ƒ”Q>£†¨Æá&Ž´“¢•hóqH§›Ð_Xd;Ï¹R–l\0ã¥%â»î¦{×½„§Ï±xç}œøÐG¸óS¿Á»>ýYŽ}ü·8~çý¸•5†iÈ¦4ì™O\'‰)vkš²¦Û[¢p‚Û;œþÐû!\n‰³„Æ\Zv‡{t(½ ë¯RŽ,!ZYg&»eA(Ð!Öz¼iˆ{}®Îr:ËëèÎ€ýá„A’ºM+6×®^âÒ•+·(1/èvá¶“Ü÷ŸàGÿ×ÿÎ ‰‘¶¤v(¼%T0ÜÚ Q’z<¦G:\0ç‘IÜ&fŒaÒä$±\"8s–{ÿ`{~¿?ÁÔ\r*K‘K+w öàÈb\rL€÷w8§œnfä?ù?ûÁ£\\}ê	þöçoðð™5Ò©ãÚÆýõŒÑ•7á¥çá×a +§Ž\09v6ÄV9a….B€Ðtm¶€éÃ‹¯±ýÖ+¬F±ìoí]ºÈÑµÓégÔù”½ÍlB\'K‰£Œõµ¤¥<!BH<©¤ŠÅ~ÌÓßz”žPD…¥#&¹!W\n·¾Ì¡G>†w%i\0N`G3d´•ÂPÐ0³ï¡Ž ³„XXA­B,†îD=º 3Ð1dtûÐé·iÁºåŽ­sìû8~ìe™³qá\Z%F»V×Vyáõ—X>q˜ÞÝwQ(A¸8€~‰Ò¨Åtw:‹Ðà¢Œ\"ÔÔiDØëC¯‡ÑÉpo”¦Acœ \nêªb2FQ’ÐÈº:qõ\'ò\'-xRH%¡C7Á\\¾ÈÞ…Kjˆj‡GbãMQsÛƒ÷!W! 2Hã 4 <²\"Ã¦X%N¡UQIâDØú¡ÝVø\0l.Ò\0GÐM Û!:s\'ÎßÇÅ§^äâkorlí©˜•Cöš‚S÷ÝOtúÓº@¦Š(Äé.V¦TŒÐÔRQ)I!!Òî½ÕuÆ»Û8+H’SÖÌ†û„ZÑô¬­e]T´žQÈ–êZ¶µsÚo*P‚÷üÎ?b×+J4ÖIH/€ýK¯qñ‰ÇÀ60™AQ·ÓK †K\rm*ZÆQÚGæ	(¼£ðÚCÁ4wŒ§020UàS SäŒ·w˜íNàì=|üßül¦}ªN½mn?u„ë¯>Ç‹}”&¬-Ö{4}¦h\Z\"*¡)š6u¯‘D„*µ‹A÷8ù¾‡h:Ø\ZRc¦#VcIg5Ã‘SbÕM	Xyx@ij\ZÀ:Œðpï»é;É¬ñhRÎ¦PŒIš’k¯<³14\r4sSGƒÓÛ’±Ï»‚YU3›6˜™o)LAÆŸ\nê\0ˆ$ÐíI2ˆ58\ZÆÕ¼¢{)½#\'°ƒêI	÷>ÈoýÑ¿âµý}T–0™ìr´ñô×¾o\\$´‚¨‰¦È\Z!­Ñ®?Á3CãÐNàjIUJX:‚ì¯`­ Šžk`º¾`V)1\\ßrñò&—/n·àµ›E*hð(ÝÁÆD1|öwiD€\n×¸|Ì ’ì]}‹ÝŸü¸uø…‚¢¦)¦Ì\\ŽW%B2™Ð‰\"ºY€Næ\n©ñÔÆPÉš‚’#vØ§S¨À-e>Âù1ÝH¦”³É„¨¿ÀHÇ˜iÎúü×¨Õ‚…>{{3õ:›×)¾ùTJè\0‹A—Š>ŠØRïèÙ†ç\0]’è˜(PŽåc§	£í µ†×_¦¾z(–”¶bo4ckkÈÖµÝ›åiÂC\Z„TÅ‰B‡	TŽôw~—¼—±@¶¶‚ÖŠŽi˜¼ü\nO~é¯áÚµ6©ë+‚T	š`^FÐ²)åpGˆ$¤MH+<Jxp\rJK:qB*bLã°…!Èz¶F#z«ôú\Z„š»>öÛyÍ¡•>fsÌ)\"ÞüÖwaã\Zì\\Å÷hC\\Î ŸÂÖL„R‚1˜é¸%€²BKEÿø	ži]g)ÓÙ˜Éî.!šD…t{«ÇÖ9~î4êOÿôOÿm[p(Vè\06YÕPUœ][åÑG¿JH\")ÐMCâ[W¯qâÐ:áú\ZØœ&ò˜@\nMd5º’ˆB Ì¼XIZàÅ»š\0G*5)!Š\0¤À*…·ŠH†T{Ž(é·\Z°Ê‘™¦°%qš êš…(áÙG¿ÉáÒ³T)Ôžg6É9|ÇQXÔŒì./¿Dqý*ÍÆu‚¢¦\ZM	:]ˆb†MN’FˆÈã&;¨ÀAS`g{4¶ ·ÃºF¦}úë\':¥ÛY\"[ˆ	;ªƒXimÛ–÷H97\n“lC±uÙæ&‰±¨¼d5ë‘O^~åeú‘¢s÷9T\' -Xªh¢\nJÓCú%\rÚTcE\r³fElc(gS	²4-¨òa7!(¨©­\'U\n¼güâ‹ˆ\rúgàB†Ww_\'\\Ô\\Úyƒr²E½yfg—Ý«×\'DK=¢AF9*SiÐó¨„ÛW™Nöq¶ ij¢(!\rt”Ñx!JË›à!ÀË¶®¢q¡@¸y¡mÈ¦cžýÎ÷XK3RÓF˜Ã$áúö6Óª`²{ÞB—@:„\"‡áª\n0 + \0f`rÈ‡0ÂpÆ˜µÁR™eDÝÆÖèn€U°3Ý&L,‚\nCm<Y …¡ºø\Z/ÿôût…cAk„6¼5¾È‘ÛÖX\\JHEMß;d•ÓB†“}š\0k+¨ @ÌóÈ¤ÜºF¾w žº†HA]Vt ’RH¡oÉÛ\nh´h`ë‘Bb½\'0²”…óç	ÖÖf,§=ò­Ö:]XZáÒÅ·øÉÅ×xìo¾Âù¾Ÿø ‹K«@ŒCÒØšY9c2bê³ñû››ìoìPÇˆZ`•dñäIN}à}œÿì?Â ×Ž(ŒÈÖ\Zp‘L	\"MÕÔ¤ÊÒ=¹ÎSNÅ{[;4¦favž~Š;Ö35IwÝ²!]X\"ñ!ÆÖô°Ja¬F:‹“\nm\rÎXDÓØšXyBW1Þ¾›—ðÙ2…*Öêí%f9u+ÆDJ ’¤Õ$K+pþ<~úÓ<ùgÿžÃQ,ŠÃ±¯é\nËýkKãšæ?ä‡_|ŒýqŽT!i¿‹P‚¼š-ÚZDc‰+Ï¢éÆ]|·Ë‹¯¾ÆÎÅ7¬®²þÑ³[M‘¡ ÕaËµ#‰\Zµ™‘’•ÓG—ºT§d‰joHØ	Ø|ãMÌ«ÇÜy<$qÈ[×.‘ž8ËáS§;ecÀkBò)M]¡¬!²†LY\"[Sä5[Ï=‰X=Å0X`£o¯In¼#mœZ6n´$9ö‘ðÜß|“Ñæ˜X(|>c!\0GÇ;ö¶† .ˆQ:c1µ¡®*tímp»öøÊâ«\ZEÉ4ØçžÛñÔ›oñâ÷gý‘‡éJÁ¬*ñBâ%%\"@1mjÜ¬„8Bt{ôû}šë›lN\Z˜•ÌÊ“Â…Ÿ¾ÄíwÜC=6•¢êvº§î@Vi¬¦dXµ©HChÆ»Ô“QûœB@m@6hcÉ§cÒÅš4Ud^¿<-Z¶¾­ÛUJP{‰­œ8Å?Âå¿øÏ¤Ö±\Z(ÒDŠ’ýKÛœèeà^\Zë˜Mv©lÃ \nèvbŠÉ)ZL¡2ÀG\nï4£@ñÖæ6¡ö¼ö“ŸòÈÆÁÂ\"Ý¤3¯AŽÚÊ‚Z\"ˆDÆÁÆ>iå‘µÇE˜¥¤ÖÒëvñUª>áÝ·s<KQwÞƒM™EY@šhêÉ-äCF×/1ÛßdIyBPç3´H• dL¤tWÑOo±óh„”õÐX¤€0ŠqA.ZçäÞÏÇ&ØÏµe)\0‘Ï(GcÊáÈZÖ:VÂ˜¨ª±»cBÐq rp¹¥.K\Zg©¤§Ò­[wþ¾{ètzÐxDåÑ:i}ÆÒÀC7ŽévÀH†o^COjº>$‚Ö=£ëS†[%—~ô¬ŸG{…ìQ}t²@ÚéHðµEÔ½ÀaÇL·¯PŒö‘Ð	¥(EŠ‹l\rkvÇ%Nh:ƒÁM÷L!èÊ„HU@h„ms£ÃäûßMçŽs»£@â4„©në}#ÔbîH8†“œi]ÆR‡Ô¶-¿hI)fIÌ°³ßÏ.t˜­.ðÃ‹opÿÃ‡ÃÇ!êÂ¸µõÑ‰f{gt³æzsÙµ]Ô~ÙR_c(Gc\"¡Xí¯ø„W_½6š GCÐ–¨7\rz:$3CúnFoòúÓOÐŒ·tSêÆ3m6YaÏ¥ìš”ôðY’ÕcRÌ¦·V†¶Ús_íV›ÑýEp’û~ëÓ|÷û_Y\Zô©f#úÎ¡4L\nÓúþa\0a€‹S$’™‡étL¿Ûc’O(ÁE3¥Øm**U°“…|âþ9wþW¿BSÌr’åeP8K=Ñ_é#€üÒ©Ž¹þÚ„ÆÓ	T›ê\'”Ã	“I_;D>š0|úiŸú$I2¶5¢nè+±3¦ºü2×.½‚™ìAE¸F2óâ-gÖÎÞA°¼î<ÀÀ-€ÝhñmptÞî <H?·v;Öý×Ðó_¸rõ2+YÄn^ÑS@¤2¤p0ij\\“(Êšíªb!ÉØ3#=ñòMš°ï&M‘GÖxïo>ÄŸþ¬¬PŒjäBBm,r)Ã7BP5¤Q¯\\àÊ+¯±â,ÊY†»\'W\"š„u”ÚRŒ6ùîW¿Èï¼ÿ>‚å”¾­Ñ®Ñ´¡·Í7_|‘üú„L(}@íQ<`qù8ý•c4*e¯¨Ù³‚½7/3k*¤ú… eî\nœpó|AèÚÜù´¬è¤	=ÊûþÉïñÿùbqi…òò:BQKÃX)v¤äÈ]ç8óÑ‡ ëâëŠW^{¹»Íl:$Ïbº‡×9ÛiÎÞ÷.¢{ÎÁÊ\0â£aU1½N@éa¯ž°õˆCE1\Zš²¯}ýopû{mÆ× %ÞV˜Š|nrñÕç¾ú,ƒ•¨6”6ÚaôÖ+\\¿ò\nŽ	Ýn#»¨°OtI»k\0\0hIDAT»ËôßK‡ŒDoíráú^KÒNÆÒRÿWt\0I°ÒSÏ»\n^!¤a§eð@süSŸÂþ‡?gg{›C½\\‘SÚ¥„‘VlÅšCçoƒÏ|F$	wLÇÜ*ÐóöE›ÇsŽ©3€\"$\"hš6BïDÝ€Q³Ãb¡\';PwàÙ—yõ›ßdÉ;\"mÛ&–ÐãÆ%ÆCÔä‘ãèá¯í\\æñg|îŽcwvÙÚÚ¦®+„Ëq*F†š™ÎX9|K«\'ý°²¹¶=,\ZtIòëG1XXD¾mo±EøŽˆ9Ëz‰Œ4užã…$Z\\àÔ{ßÇ¥ÿô—Ü÷±£œ4ˆ* P¥õLƒâ´½l q+Ë¨PaëŠY>EYÖAèˆÏÌ\ZšRÝîÑ,wˆ\"$:ðÄ¶‚8‚7®ðä¿û?	®^g9pHSµÏ\\@Œ\'ô±gg<A,Žé°éužþâç‰×ÓY\\ƒ(eof J8yî‡ÎÇå\Z‘-¶•ë\\íB‚–DAÈ‰“‡I;Ö–ì&·v\0Í±kSµTóPQ8±ÝðBaÂˆ¨q¼÷×a÷Û?`¼1bÕ(´­	„¥I$‹Ha²„Dw‰Ò4(­ˆuF/îµý^¾\rß‹ÆÒëtiJK±[$	ýL2«JªhÂ²-ë}ù2;ùmªŸ½Àá¢fÑ×”®5ÝešZ²k{Ó	KYÂ‡¸‹äôI’¥e¶kÃöÎˆîÑSÜ÷Þ‘¬Æ Ø.JúKT¡)-ZHâtÞJ!\Zç	ÂhJ²¸¸ð”7oðÈù?÷ví[4DÝÖ™&òd¼›“÷ÞÃÕ·¾ÅB·‹*r<Ò%m÷mûU)U«‚€Ú9|S“¨yp´ªAdÔÁìåÝA.%$¾¦ÞÝAD.\\ãÍ¿ú+~òïþ#?v–ñÖŒ0”ø$`Z7ì\nÍÈ:êÀ³tæ,¾ïv²ÛŽ`ö¤¤Š3NœºìÄíø¬ËL†ìW%¨d±±„R§ºmî´PUZy‚PRS¬÷¨ l+[ßÞ<\Z¢M/Ü Ì,ÀW³¶òH˜îñàïÿ.ý£ŸrÙä,dméÖLT”&bæ\ZHBÔB‡Íº &DR\n¬Åcp‘jc‰€V-‹‚£BÊ\Z)rŠ	<û\ZÏýûÿÄþÏpÄN±{—9vd½Ù>»y@Óïòó­ëœyàî|ïƒ¤‡Ö‚ý4!X[%\\[cåüÝ¥më‚ÒÄH‚yÛ•¤ŽzvÚWV “„ƒß(Yx¥ý2xó“Û^Gæ]×lmq@ ì¼æ!õ5ú÷žãê3OHAOÆˆB\"\'ñS\ZzYBÙÔx¡ðáqJâð8\'ÐÖÍ{Ê44%ÍÞ‘¨¡š°óí\'xê_düÔS‡Ž¥´Žg&›Œ%>´NçÄ:Ÿø§·#l¯Ï0ë WX?~uâ$ôØ¦Á!BiÜA“ò¼—SÑÌû9Y\rÌ­é·¿RaÜèGöÜìÅàœChAƒ_õ{`<·}äüèçÏS\\¬F®Y1·w)Á~EÅÄAˆŒ-±ÎR;B\nE $t­ËP\"¼ø:/}á?ó³o}‡ÙÆUî9{Ž¢ÎùÑhÈÃ±îäÝ˜•ÛNQzËþî&AŸÞÚ!Äò\Z,¯´9`©¨GE[ü(ÚTš7ìzL»i¨wÔ.ú+Àk›fÕ¼wVPÜü0Îcñ”8¼wDiUÂÑ<@öØYf»C2˜9BãÁH(-z:leA,çõm–@b©Zÿ¯ªÀÔïR_¹ÂÎ³/sùÇÏpý§ÏÒllr¤ÛçÐ{æ›?ý!‡ßu§þ0ËwßÎ©÷Ü¼ë<”%ÃW_&]_¥¿¸Ø†Ð’TØ&Ó•&Œ5Vi„Ô7¤¹Ã ]ÐÎ!xmS¿\Z¼¹m\'(/n6!ÏA”aÀÄäÔÚá•¦¤5³ã;Ïqâcä¹gŸbYAœ…È©…­«°qz\\—¯·²RµŒB^ÁÖêÊ“á6Ï¾ð#.¿ú“WYp!ÇÒzq­ñˆçžOþ·ÿŠÁ}çÜu;v©KÓëe=è-p¨»€´U;“@k0`ŠË£”Ö€ý…Òl§ç®í\\WïŒòÄÍéŽyÚ)ÂÃQ²mIß7SÌ<âœà°ÅˆÅ¸Ãô?äËÿÃ¿aecŸcM‡ÑÆ>¾Ó¥ö4˜a{1ÇOÁƒ«ê²¡É\rå~ŽÛÉ™ÍF«1ûã]R³ÐÐT–F„Üù¡qî³¿	wœ‚ARõÞFpSã|ÐM\0È9‹jªÒR%ý^!æ©óó1	ï”øÞFyîÆÉn.ëäM?w¾$ŠhÞ@oiÚ–#Ð9sŒîýgøùÅ¯È%Nï“ïÐW_aY”×-;/ü„º®©k‹ò’(JYÒ]>Æ*Å«W®ÑtÉ•à*Ç?ø Ÿøìg‰Þý\0$iÛrÐé‚ŠÁX¦ùcišÆ	UÛ @Iƒ­-‘ÒÄJÆ\Z­:7üô·­íøÎ¹öíÍÊmÇ»G!Pbnß²šÆ Q8,µoU‡\Z‡Š3þÜ?æµï|›í=Îb’bBÜL	BOnkâ$¦	Mà‚§|ÝPfìG¸ºÄ¡»ïâÜG?Äà}Âmg ×/)Oœ,0ïÇÐÍ-GË°žQIO cBBT(¾­Z¿1\rÃÍuÂ\\¦{Á¶€[­Œwž£­ƒ_¡”çWŒµ&?*b·½	Ö£“•£|ìãŸbãÿù*vÜ¶É÷œ&õžŽ’TE4¹†}S2ò*IYY[¥³¸ÂGÿð_À±#pò¬,Cœ`¤Fˆ”ÙN9˜b=Ž*hÈ…Ç„IŸžû¨ƒŠõTn‘ßŸËx;wÂ›Ví;ï\0Àh„—oîÀ€ÖJd>!±¾­ñåðYÞõ¡O2ýÆ‹L¯8½|3ÙÇØšIYR\'!¥ˆG1»ÚS.ô8q×yÞûÑ‡à÷C˜ÂâzhJ4¹÷¨y_Fxë½çÓ8¬tTX< 9°Q]«ðÚœêü%ì-Öƒ›¿óÁhšþ!Ó-8 çù…üÍ‹xy#àÂÁìæÃ¥\0™@] îû0½Û¾Ã…u\"¦¥§³¼†íDìÕ\rû@=Â=}ˆ³ŸyÎßMƒÝ¢ƒ0 ÄÒ´Ñ‚ƒdËå <F{Ìüõ\'´s«^Ü²ënæ-†ëMÑwà¼ÃNe~ÉÎ“­µ}ëË›£5üÁ³;Z¶¹4xàÒj)åäGfã•+Lã€ªŸ±iJ®î9÷îyï>ÈÂ#Ãù3yòºDÆñ™5ÊÂá„yÕE$%ZÏg·Ø›à¥k{@8¨PS7)óÆòoãœƒ?¶ƒgÜ÷ý‡®¦ÊÁØ\"D³RæR´lM$4øÖµ¥q-Iê¶±Q0œLYµ±9âÏÿåñÔßäŽÓgxðãá]ïÿ úü}tçµ|).“äÃÊV»÷Tz# ë6l.Ô½ðHÑ\n.7§69}{<Êƒvrýq7µÀ\nntqßüzS=´Üü”ys³µ¥ºí4xC{Øù³„‚¹±Ôždd|§Ûaçê+k<ð™GX=„OþÓ‡× ·\0\"•´Ï!Ø¸}Wç\rÒù[ß¢µÞ<Xg±x– h[O[ù/^\"ý\\>ß ºù¦Ê›9„f7·_oqüý/ˆ¨wh²¼=äæ\"á–`À·Ü¹!%­§0gëZ€Á“âé€Éñ	ì«œ<,	ã\nÝk•K«Ò\rÆ)*×nB(d[h^Ï£8óAeÆ[<%<¡(\\K†n~ã[æYÝ@å .)Úþ0nÀÝ¦üvå¹9ë€Ëïhý¿Cƒú0)ýº\0\0\0\0IEND®B`‚',1,'superadmin','2013-05-23 13:50:19','admin','2016-11-06 16:37:22','CRM','SalesForce',' http://www.salesforce.com',0,NULL,NULL,NULL,'0',NULL,10,0,1,0,'',NULL,NULL),('78917a82-1c86-4020-b86a-3b1b350357e3','JWT Demo','http://oauth.demo.connsec.com:8080/oauthdemo','COMMUNICATION','985e805bd49770e7e797209db3cc2767','Token_Based','‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0\0\0\0T\0\0\0»šd\0\0\0	pHYs\0\0Ä\0\0Ä•+\0\0\0tIMEà\n\rÆS×\0\0\0tEXtAuthor\0©®ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0Àæ‡\0\0\0tEXtSource\0õÿƒë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨îÒ\'\0\0 \0IDATxœíù“Gvß?ïeVõ1÷=ƒ‚äî’Ü#$Y+ÙÞPH–ì°°C’C¿;üƒÿ9+B!ÛúAa)dGè²­•´Ú{¹$â`f0WÏtwe>ÿYÕÕƒ	€ Ü‡hLŸUY™ß|÷{%ãq4QCUÁ\0!p€‚pI—ô©(ˆ\\¢ã’žŸü%p.éEIE¸K—ôB¤\r3ûYã’¾€ä£T”\Z>BR™ýYŽë†>iã¾Êj…W\r$ËJ@C.eÙçIföJƒäi¤H6º!Xæ@Òz\\Ò%]LjQ0“†Û\\Âå’ž•4é7—:Î%=\'yÍºNâ9Ò<«é’]ÒÅäE†t*4‰]‚ç³$³w•¼ÊŠ´73\"’/È/¡ø«;þ/<‰È+	\ZÐç-Áúµˆà#ÖØXéCÉàÉÜçÕ»®Ÿ+zÓà¹è}ÁËâI,¿j$\\èÚ >ðúµNR0`ÊT½tõ\\ÒÇ’‰Iç!‚SÛ1‹KúÅ\";÷÷üû¾~eõ¿¬ó`–­6˜ÚG’\'@u‘Íð|¸³Ž!­ÿ[ºÙ…g0äÜLòw¬ýÍöyràrî:ÏñÉQ_<æ©WÍ—ì‰S>¯köyÃØÓç†c˜Í¬ççÒúÀƒCPMQ-©Àp¤æNù1\nu\"™™-‚ÑRžG4C¥v=Ö\Z{k@õlr&†›ï™iÒÁD±hˆ$ñ*³eè0Én3Äbv:L\\\r†#\nHÔÈÀˆ ‰Ó\ZŠ™ Òœˆ˜Ÿöf\Z‡SÛ¯Þk(XD•Ø\\WöÝ[>µf¤å÷‰\0j+®&Ò¬ÄÔ†¶zá%/¿¥€L¦u2áS\'Hkny\\&’çßŒh†HZs|Œ\'.½‘\'\rn:<Ú€ÇZ¼ÊÒÄ›¥Éªbd#eá±)\\RÂ1C\\›JŽ¤å	µ„ÓžÀD‰w€#šaqœÀ¨«†@$j—Q,(¥\n.&`‰óA%hÄ2\0e„YÄ‚t0óT&˜÷RaTˆ¹4“\Z1«& ‘ä\\Miºi¾Bšl3O¬=öf¨\Z*b5F½.$ ˜Q:}&ðÔfrDÇ@\r¹xbˆ Òà»-D¤až&L/¦Õ0g`!Hª:Í?Næ-ÿHò’N£çõåúUš &H;M\"^ÓÁTõ	QCs©íg“kiØbÞ¯VÇø-‘\06¡–~í\0£QE¬5Î¼KA¤2¢\rQ?Bd˜=ëƒuÅ‰atŠWØ§!Á“2ŸÙg=D*˜8lÞ¡f$ Q¥óé¢êˆxT\nx\n×9o\"7ÁµH\'Ê”/N8S\ZÆÝæ2O£ú{-¯Œ‹à4oì(]Z5D”cJk÷à¶a8	N\\|ÞŒ23Ð´[ª`Üþè&{‡G Ž*B¯SòÆõkÝ–hV_ç’Ò:Qâ:–A“\'4&q‹Cöï38Þ¥_D<‘Š.Åü&Ý¹-B¢€—Ç˜øÅ_O²ØÙ1{{·ˆá€néïç˜Y¼‚–ó„¨à˜âdÌølŸ“ƒ{àÆ	PV0;¿M·\\¦‚ˆ¡$ŽEtDÔ¹$Â	(\'œÝc<x„:a” óÌ/¿FÙ™{úÚfN3%²bääÁÇwaUDT°hxç!äÙmd0’%EPˆRë€‰4‚³mÎÄ\r–¸—*Qà`a{‹Þú2rs-“ë9n³›©÷C4ˆ‘“ÓSþèÿ˜¿üë¿¥ìöŽÇ|ùKoñ_þóbîµí¬€‚²™õšÓ$žå·ˆT„xÆ~Ä‡?ý\'æ‹\n¨¤Ïë_ýu®¿3K°ÐÕœ%©yšPëdQuÌ{?þ6ü˜…¾ãôä”™Ù5¾öË¿ÉÜæ—®³€à °÷á?ðƒ›±SÝî:_ùêo²}uƒÌ Òå‰²WÄÎÆƒ=~øÝ¿`ÿÑˆÂ°ê²´öomNgþ©œç‰°…áýwüß?úSâ¨BTˆã€jâ\nI¯›Q,f¬5#¿¯-à8KzQ­f(B©Âh<&8áÌ¡çù?ø}¾ñÛ¿µµÕÖÛ\Zu:IýAœ(Pªx)<¾ªØ{|ÈÝ{èÍÌp62Óï3\Z¨BL|Dd\n”Óó‘”ä$Éke?I$V8gÌö=Žvé„Ãªäàþ2áõ·p½5Œ‚(é÷Z§Ö©0bO¯£ŒŽw9‰£!ƒá!G;W˜]ÝÀ»%¢Dˆ§ûìÞþÃƒ‰Å˜ÂÆÆ‹ó³™å\'í²UHÌ‹“_‡1ÂGû·Ø{øcF§w‰@èn¿K·Û°9?/’ÀAQ¿g!TwögCœs„qÕŒ!2QÒ§7hF8HÊæ\n@°‰R¯YÙ>#P©a‚¡3Î8=:iÄŸŒ–‰lƒ§9}#wò…¤Å	f¨/™™›G£Yæ74éH6‘ÛÓTƒ\'‰¬d9ÕÌ(_E õV–f9¼s‹Ù\" 1p°ó>G»7Y¼:G  šk&:YY¨‹$å´Óckû\Z{ÍÂée9pÿæX¹þùe‚%°÷ïqøð\'”rŒ‚UÊÒÒ<ý¹ÙÉ\\ÕMôEEˆXbrÌñÞ-Æ§è¸L<Ò/ØX[¡(\nªq¤(ÝEûª™¯¶¾cfH0\\ŒÄ*àÐf±›ÿMhYÚh„Á›$…ÀŸ¯ÔcTY¤‰$^­–Ö%8ÅcÅØ	¾ÓÉõ}éJ}íòh»6DZssÁzÉ<7ª˜ÿ†dš‡hø¢HƒVi·1.ÏOQsÔÉóÉnãˆú1¡;;ÏöÕ×<ø1q|ˆC!³së‡,n^G‹.%1ï	_+[f„ÎwÀâÂ&«ì?‹ô¼p´·Ãé£»tf¯#1qÙÝûââ1W16Y]ÛB|•L†míkq‰ëÙ_FÎ?`÷þûh<Adsó,¬­¥oû‹Yr[ß©ÿ†:Kst®¬¢\'§e‰ˆª*‹gC$úó¡KƒòpÄpgŸ²ìÌ„ÈüÒÃB8+„Q²}s–—3b¬Xèw9\Zaý’îÂL“lÑ8	‡@K|Õbªqí«±é93”ZU£Ž7¢jz÷\\0MÔNÚˆ·–ÇEÅ!jH,A{,,n13»Hµ·CYt8Þ¿ËÙñ#:s¨tˆâ‰Ö²ÜÔ	•DÅw—XY¿Áþ½Q‹#œöîßdaëm´œáôñcö|€Åˆà´Ãâò6½þ*H‰©LR¿%OžyŸE}…1àhï\'‡;	ÈÁ¡®ÇÚêUÊnœNdÉ9º(¶$\"à”w~ã×¹ñ•/†c|Y¤õˆd±eòäC»\0?üoÎwþäÏ98‚\n~¶Ç7~ç7¹ö/…ªëÉp\r†š`bŒª18%	NXÚZ‡BAÁKk—·mþ\'ÁS#+/´Hv*IvÔi*?ýDûðIèh>v­ñÔ–WíÒ1f¯±¼v{ûïãDˆÕÁã‡=¸EonüŠ³#M…¼@B†§\0ayó-z?ù>Ãã1Q	<¼û>›¯ß¤¿9ËãG?áøè¥\Z£è(ÊeVW¿Œï¬`V`š&·ÙWæK‰uØtH¨y¸ó>§ƒ}ºê‰•Òí®±²ñÎõ‰ªD&¹œOO­8;UPE×q+ó à4ùÁÒtÉ”•ÓìÝ\nª¿ý•à|‰zÇ‰Tk³t¿ñt•Q‘êZ‰vQÈY$uÙnsŸúo-³¦ª[àÉœÁ2\0LžÌA|’Ú­™«6’\'.MœÍès¸îË›o¡½y†!R”ž0\ZðèöT£#ÄÆÉÒjŒ¼¤«à½€8b¥”KWXXq,çŠÓ“‡ìÝÿñè&G;ßGä¼0®\nz³ÛÌ¯¾\rºB‚Q¨1‰ó‚a6¢:yÀÑÁ=ˆc\\ì ,²°ð&½ùkTV22cDh¼kç©ÖqÚi¢JP{eä”¡ÂHaä`èŒ‘KÏÇùïHèŒ¡IDid`C1ªRÁU§àL’YoÞ Ìƒ9£’Ša5¤\"fŽÙDZàh¬«—zê™µ‰.æ<O³Ô›·M’’XÎd4\"Q,­‰%îcÖf˜[{ù•+T8Ô9œ\Z»îpòè^OZ $ùjã(¹Ýh—µ«oRÎ,PÌEÐÞãñGßæpï=SFU:Ãêê—(g®‚ÎaÚ!ˆd„ÉˆÚÆ![ˆf‚Å3>ø€ÓÓ]J§X,èk¬o¼‹k˜ô¨Ä9,}pÚ\0j“’_Ÿ‹zÖkÅ¹~(µá‘ü_N5!1R¨£PG=9Ù·›Ž—¼\ZÉ˜´@À+RxbV®cž†E¶ì¹©•Ÿ†ÈÅ Å‹În*ÝEÝòÊÚ”}—¹µsK°¨„à)gW™]¹Š+çÃ‹qv²Çãû7±x†È$Õ06,M†úÄ£g×·˜_YgTED\r•1§ÇøèÃd8¸2\"˜Ð›Yf~õu¼Ÿ\'˜Ë\\Œ€eó|Â‘ó¿ñ€‡÷oRãÔ‚§Ó[eaý-Œ>*Lj\'è“sÛæ:SŽB3œe„2\Ze0:ºÊÖ£à+(‚ QðdXÑ5eÞu(Ç†\rF¡k0cÐ7ðµíS<kŠóµ\nD5Æ”!(IŽ›¤À aX.¤yLXÉj‰EÔ\"’kÀjà4±¸óàiû\ZÉ(Yö\'keb‚\Z*¹¦L\rQAdŽÕoPt7W†ÈˆÒŸ²{ÿ\'œÞEmˆT#Š¡Ó#ODTÅTÑ¢Ëúö\rœv‘ÊQ˜#ž2x¼C¬ŽQU(™_ºÂÌÊ\Z£8fLÅXª¤«X‰]$zHÔŠ tÀéã{œ=¼G·\n¸¨T”Ìl^G—ÖˆXPºQèš öäîªS=UõÂTÕ†ÃÈä»’i\0Yç£§­\Zƒáƒ$Gx0¤,ÀK¾–ˆ·4G)ê)¨S¼÷¸jR\'ÚÄÔÔÄšSÔ)©–cIù*ÎFž¼\0RÀ±ŽõN[bì«ž:ò[«U2ÍóçÖpP“³7˜[¸†Q`:EÅàø>ÇoBu‚ZH^ÓìáNó¡8—c`®Dµd~u‹ù…\rbåQóh0N\"¾˜gqm›ÎìÑ¦Æ8ß¡YQ6	˜1w†Ø!÷o¡g§øÊ°ÊèÌ-0¿µ\r.æ\nÅ‡|¼HC˜Ä“ÉÎ¦övóH›-édi1Iò“[¶p)@«–”ÿÆKÑ%=VÈ Es4½‹.¿ÑXNÚz‘Ns1‰MÌí\ZoëD-Ô]àÖ™þ\'ùØù¢¬Ékph§ÏÖÕ”½9b6OÃè”½{·Ÿìã}@°,ÒÓ%kžø(š‚ºErn…ÕíTV|¦Š˜áDVBw~…«àËäÍµ1ÞR²‡¶®Ç$Å¸¼Žžì±s÷b)\n_cumƒ…•Ìõj3½VêŸ‡ä“É\nn=HYi³ÊDœ·Ö½žÿú3›,é…§Ñf‘˜D‚!+°öŒUëOñá<É«Î}ø,$“£Ô­!˜ï²¸}ùåMª ÄÊpxüðƒý»H<ªô}qIéŽ1ÏŠÃ¤À¤ƒv™Ý¸†ïÍ\'q‚ 1»´ÃìÊ6½¥5E!Ž*Ôg±ñ„×\'jCwïrðøU¨R\0Ù,­oQÎÌåïNüf¦Âl/.Ä”<ùùùççûq¤V»ï³¯@¦ù2.ãåQZ(!D¡Ší/³²u-fÑ(UŸìsøà&álÑ*û%òÇˆEK¹G(“.ý…\r–V·‰R`!G¢QôæXÜ¼†væˆÑ¡¢”Þã$â²UÇÎ\"DÂø€ÇoªÓ¤hÉìâ\n³Ëëˆë$cÇ,[,Ó)0_4J[&žµÄÃÏºmOÛâhLVSGÔè°ºuƒþü\ZàSÒUòøÁ‡ŒŽ 6Kš\\›wåì *!z:3K,_¹žÂ ¢Í÷ÊNŸùåuDŠËJ-²´s;Û*Î°ÿà&*c´ðŒÍ±¼qÞÂ\Z&p)/†”<÷¼ëÓÎçÏ?Åù5ÉAi&¬ždxz$üó¢\'EZÚµ#˜£\\Ø`aå*¸>˜à38xÀñîG(U:\Z“3ß¢)Qþ,ˆâ´Hò\\•*ÆÙÏâÎb:9›°´€Å\ngœîßåôøª1e=Ö¯¡åf’³6k¿ÓçÅtŒi“¿yþ)ÎŸtKÊ$Rwç‘gzŸ17U%?7¢¢|1ÇÚ•/Qö1õ˜ªÑ»wÞ#œí#ŒÒâ‹5b‹FOI.}µÈàèÑh„()BàôøG÷o\'`XÈ©%’°\ZÛ4)æqDñèîûH8Å©r:,o¼ÆÂÚUŒNÂ\'àÏÉåŸ=†sŽ˜“«5§||:ðH­°M¢Þ9ÐùjR²PÓs3t7™[¾ÊY¥‹#ÇìÞAm„×Ä¦ÅIzd+ŽpR1²sç&!ŒqÞSUÞ†>¸Éðpu-,¥¡ˆóˆ‚Šaá”Ò9;ÚáñÃÛˆU)Y¼˜a~õ\ZÚ]Â´YŠö‹µµéùÙRml|\\1ßó’NLÅ‰íÜX¯ uM)žDIg~“¥Í7¨\\sï…áÉcÞ~$Ž!ÌÅHyÀ)Yëäà‡{÷q\Z1‰É/Fagî|Àñîm$ŽrâZJ,’³ÌT§NØ¹óSFƒC¼Â¸Š”3‹,®¿†¹>Ä2Å¾˜„\rZËøùOäK ­ÄÛ‘Ýq~>”Æ–’ÊSÃ#Å<3«¯Ñ›_KFŸ²÷à6ÕÉ>š+/ ›Éb”ð2\"Ž9¼Æ\'C°À(T FÁ)ñä!Ç;7	ƒƒ–ÎUÍ,bšBÐÕÙ{nâcfŒƒ±¼v…¹•+ }\"%†\"Qš2©­µ/(éÄQ­hÔ§<ìsÌÈsûÇ2x¬Ê£ô%½ùUV·^#ª#Ä\0Tœppï6ÄQã–+ÎíïðèîM\\!!•ìø²He8qHO‡ì=øˆ£Ýû@ Âˆ8¢(ê‹$5p°{—ÁÁbXÄ=6¶_GË9Lº E\0ÓzØ$Í÷ó¢NwÑžeTjÖöç¼ð´]ÇOR“÷Õ§^’â©ØªhøÞ<‹ë×)ÊDœ\ZO¹ç\'Øøaœ¢=dAaXuÌàñ=G;Éo#Wôé/n|—`ÐóÂñÁ=Žöocá‹ˆ0Êó&6ÂªcvnSž¤÷\\ÁÌìs+Û˜ua’nR»BÒÃÁä@½(5–¡ÕŽJ\Z7Mý¼Žƒë3 Gk\'•My.>\r	u-ÓÇÍÎþg?~ˆEó•ˆY‡ùÕ·™Ÿ}‹3˜°óèœß„x”8Ž)ŽÄg<xøSÎÎv1Azó¯sõ«¿A˜Ùæ””3{ìíüÕð%j‚#…*p§œÝcïæ•Q™1ŒŽ+Û_¦ÓÙDF}¼t“´ó‘èFD7%¼I{û|À-Õ\\ÕU¡M†bë;ÏÃ>çiÏê˜~ºÈá]»åÛÃ|NÊ’:É[°Ì9=¾X`yíhõªÇìî¼r†q\"ÙÝ9Ú½ÇÞÞmŠÂpÞ¥ÇüÚ\rúëo1»ö&æç1WàÜˆÁÉmŽvÞÃÙo©²L,8Ù»Ëhð˜BSGgv‘Å•«¨Ì ÒÉæ¹‘ª+Æ˜$}íålÖg¤ÆÇSE¹H¡½S\"õÂ“þK%O?m+šò¢ó–#Rç2¦ §‰C‹’Åík”3‹TÑ	Î*Þþ€³“}b‚TXavÆÞ£»÷)½#(;s¬m¿A1³ÊÚ•7ðÅfQádpÀÛïÃ\0OÀ…ÿŸ\rxxÿ£ñ¸A–·˜YÛJyâ\Z	‹Šo-ÒçF2™÷©ôãöÜ>ÿøâµ@­7O£7$ÖÅcRÐ]Zeiã*Ã*Í@Ç	§ïóxç1†8Øé>ƒ½ûh!óË›tW®b±ÇâÊu:3kŒƒKÙŒñŒÇnQì q„†ˆ„ŠÑÑ÷îbr„É¸3+Wñýe’2_×ä×fÍ•\r“¸Øç¢x™ôÅO³]k-M0<A<Q=Ú™eáê¸ÎfžR²»óãê(Õ‹Û1Ç;p²w‡R¡\nøNŸõ­7Ðbh}:3¬o~	ü,!Bá#ã³]öïý¢Œ±Ñû;w8;}LQŽ¨dDgv…ùµ×A{àÊi‰m¹ÖÜ¤®êóñ~&ôÅ\0”d›j/š°¤*æºÌ¯]c~õ*¢=ÂxŒÆï}ÄÉñ}°v>dt¼G¡FÂÂò³›7°ÐÃttŽÕ×¾JÑ[¦Š‚s«#Þý16ÚGô”pòˆ{7ðe’R3‹›,,_Å‚ÃÔ1	Çe‘a­tª¦:å³Ÿ±ó=_Z`ôEH­H©«¹=I.\n(Eo‘µí¯€¤r^/‘³“]Üþ12ìpôð#l< †ˆiÉòÖ\rŠ™UˆÌº„Ø¡˜ß`iý:‘’#…FŽ÷îp²âcNïp°wU¨B@}«_?R¦Þ\\D€¶ÔDkçó˜¶¬075íuó§Pº¾xà–ØÊA]“œÍÅ´ËâÚktfÖV¦@æè˜ƒ‡Ov8Ý½ÍÉáD\"•¾7ÇüÚk¨›KÑùÜ>Eý,‹7ðÝEbP\nqŒy|ç‡ØÙ}öÞ\'T‡¥cT9fæ¶X]»Q\"¾“‹Û®œÚ:i•gÖÿÏzÊ¤U}\Z-7¬út\Z»þ¬Ó.^„&±·ÔÆE²ùp¢(ú³›¬n~…Q,	&¨Nr|ëû~ôcÂè$u˜Ð’™ÕmzKW0×Çr9ŠW¸3K[Ì.^Á¬‡DÅ…3öü„Á½ï°÷àûˆ\0Eu™Õõwp%ÔõR-×\0N/¹Óz|~ÜçÉ¿/€/œÎS\'æ®d’=^j†Z@¢9¤»Ìòæ›hg‘±9œTÄÁCîøvïÝL‘o„à:,l½ŽŸ[!XSq>¥³°ÆÒÚuDçˆAé–ŽÓ“{Ü½õmŽŽßÇ8f\\+6X^õóˆänf¤@kŽ¶”šÎ‘ý·ÀÓ¾„óäÓbŸtÉo‰¾Ø¤¥_¥®¦á¸XD, 0<FÙÅ-Ö®]§àÆ\'ïÞctz€S0UÊ¹%×¯µÇ‡¨à4¥k˜(Ú]dyã\rúýUªQnÜÙÛ}Ø\'2 ˜²°øÝ¹70:„˜‹å¬Émò¤U’TÇÙÄZueŸµÅžòþ³.K£óœóŸ{}þÈÖ8º\Z€ÙjM±\ZçYvŽ/Ê2Ó\"LzþÕ#5WJÍ0‡ë/±qõM´3ƒ¡d„T§8X žÕõ«Ì.­QQ`N9\0f“Iæ–·X^ÙÆi‡0¡:\"„}DŽñE¤(zln¿/×1séú±ìÈ<—q˜/;ÕÄJ®ý,9å˜U{6åèÏN{ìüÂÂÐ¦tž–ÿmü(µRò#f`L¢bf±QÈRRÓƒ>˜X§FnÄöÜSW­•Ïæ‚b%ˆOå51 ¾d~ã5è­SEŸ*$\"N¨Ì!nŽÅµë 3Ô%Ø1¤‰ƒÊ ŠŠë,1¿ú:E‰±	¾ÐT×5¨tû«Ì®ß@|7ûrR“Ô=…©8Òô³Ï:D‘’c˜B ä‹¥é-4Õvî¦©vmÚÊ5§”4ÒTW¤=/¹V >K©@pÂm\"Î9ê(ÊÓ2écÁç£TQ*ÃÅ‘ª¥V‚ú\0\Z)æ×é­½Í(Îâ´\0§Ejrö\nË«oáu†R…‚@QFÄ+æSZ†E¥ª:¬\\y›ba›±ë ¾ÁS0G5œauíMº3‹Ä$wêP<MÓKžlÞ4é_ö9DÕÐˆ–Â©°B0\"ŒÆPEÄRQàˆÔ§¡.¨žó´|U“‡$=\"7:jx	Å5D…	K¬ó{CH9-©$Ér[´öKÏÛc|žyRúg»Â±®³­{ ¦˜Ÿ ¾Ïêæ\r:ýep}‚t¨¤Ã`¤l\\y“bv¡ÈÀ€4µç©ºÔÔuqý¥äóÑ#ÒëÓï¯°´~×›!«,ErFŽfÕŒSÌ²öP}rÝOGI/¤\n8,ª1…ó”ê X2:˜40°z¢Û/ m¾ðÄ—¬Yf\0õ—,kAfT!2\ZÓaêRXZUÚúÐ—õ‚ÓV—C·¶GÍ9¥>yZx§ëÛ,¯_g:<cë±¸z•õ+7ÐèÁ<–{¶#öfFUUˆ:¤è±µý&½™\rFUŸÊæ9tgV™YZMmÙôÍÀ´”{ã£Pª£ð>mlÑ\\¿=ÑOkýçY.äLõsÚS\r |`8<<áäd+}­SvðÞ7ZQÓðñ¥Pc³Âqú#©¥#¦Þ ôº‹,oÜ ”«ho!³Ì-_¥œ[×Šüp4©é’À£N	1F‘ÎòæWn0f‰±-#ºÊÆöÛôçW©ªqRå_Eôd}Q¼r:\Zr:JumO|ýU±O‹ó4µR4\\\'}ÃØÝÝãñÁaV˜Ó„÷gfèt:M\ryÝ~þåPÛ¨ÇiSkV‹Ÿ|>Ò¡·píor4ê!–7ß è-bAj–P‡&úŸŠP.w\nñˆ›cmû\\w›Áx‘îÜk,¬ÜÀè’íª—x/—Bá¨úžC­Ø\rgŒºz%ÖjªÞfÏBþéµzÂHð™»`XN©488<àèè‘úVF¿ß§,KBHM¦\'á„—EçmÉzÅëæJ âpZf‹0»|·ù·x´³Gn……ÕmŒñÂé\0\0’IDAT1:$ûŒê\rP‡\rT*†”Ï\\öQ«XØx“7¾|J˜]˜¥¿|=¢ž±|m¾J$pý«_ÁýÁ ãªPQ©°õµ/#^RÚ™ïfë«1Ž?fÕ><L\nÄ4£ÑÒÄÄh3PewoŸ““\\Ù„ª\Z²²¼D¿ßÃ	Í¢¾Ü;Ú÷M\\\"à\\VI¥VF#®³ÀêµwXÚ2D:©ôW:¨sLuœv^¡Që¾‹B1³Âµw¾™”kx‰Ç$%‡½Jr«nÐŽƒ«¿òU¶¿öÔyˆ¹}q™|[hîøQÛuÆa ‹É×~©&ùc TUªÞÌsìòßù\'|Qb\"ŒFcº’7^Ç»ZK7Ÿ4g§ú@ç{ÕŠúä$ÒÞ“	ï\nÅÌ‘îh“îøÓ­ž3IÍŽÒY$ëIÁT-pùT…JÃæ2P_èL5Äb×a´¦BiBêPVKÿšóäÏ^˜óLä· Î7¥¿©f[øþ÷¾Ï÷¾÷½¤PÆÔîncc“wß}7\r8€^ÜŸúSÐyÀ´EØùm¿.hj%ÝÑyblÖ¢šV¾b´ã¦éµeñ,MD<Û²{àU¤(0fÒ:º¶„ÕêÎnà4æÖ¤ ÿ©øùXð\0„`8\'8ç¨ªŠ\rWó7ó7œžž¥{Kåþ¯ýk\\ÙÚLßsIdXŒ¨Y1Øó\\>™­ILšµ”Æ1±ªjð™<è9?Zb\Z³Jº•S¬9™*\Zk=ì#‹mCô•¢Z{xÜyß5àéX›Lýy‚üÄ?å–\0¡ÉÉ×ôÈsJˆoÿýwøö?|\'YTê	YQþÕö«øÂC¤(ê¼•I.I[—a»ä¼#BÚ z\ZIÔ¯Èb}\0k*¬‰Ø§#O¢P1·n9ÞxækwÄ+ˆ1(,Ýœ¤vÖ38Åuò›éfmßëÓÈ7¿8?ÿÙòIéã*a4®øÇï|—ÿú‡ÈÃ‡ï‰UD]Á·¾õ-Þ}÷+‚sB1ÝÒ}§Ü„c´yÅ©COýQ[„Mfä‰2“öosúSÉQ9¸iµÕ™¹$‘×:üD¸\'¡÷ÙzŒŸŸš†Uî¦-u\'`²÷,GûóÌ=õ¸>D‹8ŸîMeÑ°²Ë£¶4\0ŽNÎø?÷÷üÿþ\'üèÇ?!JÍþ#W¶6ù×ÿê[¬Ì÷RO?MaZwªÿžãŒ/™žáè-ãjÊ•_Œ¤ØDÎ·DIÁx¥¥X¾Z°É”½”ÑxÑÛÍýI×SÛ©w=•&2d­7íÒÈ8&s=„À7oóÿëó—ùWÜßy€ªÒív8<:dmmÿø{ÿžw¾|§21÷òÝÿÈŠv{(ŸÊ2yê?á¨aª‰vëô/›C	©½¯›ò¨Ö)¦õ!^5n3EÙorñg­§umÙ³w¹Y$®­N©Æ#¾û½ïòÑÝ]î<ÜåýŸþ”;wïsïÞ]b¬è÷ûŒ«ŠÁñ1«küîïþ¿ù­ÁL·ÛïOvã´žóê’|ÌË ñ\ncå	zŽ±>Ïeùt×Z\ZeXŽONøÓÿùgüÙ_ücSª|<3ý>1NNNPàÚö6¿ÿû¿ÇoÿÎ¿BÆÃQ¾]Òif/éEÉ“­(ƒH@§gcF!PtKJ)Wcp¢,/.ð¯}÷oÿ\r¿ôK_Ç©âc8%³Ü}áR£/éÈ‡Pa8b¶*Ëma]ês3:=¡,\nfú}Ö×Vy÷í·ùç¿öM¾þÕwY_]b<ªp¢X)C5·\0oIªvÅKúù!/¹ý{½¬!Dœ÷\\»ö\Zßüµo²°¼ÄÒÒ\"×¯¾Æ[o¾Áæú\Zs3}ÔÀ‚Ñ)Št+åüºvÌ>q¯„KàüÜ‘ŒªSI7v’Ò<®\"§ggàWxœ\nN\'ù–;…æxHK¿iZ³>\'—\0úù\"©âÐ¢)¨oü†cÊg­ýŽìC‹ÉuïNš¨jç‘Æ¼ýâU„]Òó’r[Ú¬§d Îk%~Õï‹ž3¿ÛEkM\0å’Ãü\"‡z©­qÃKŽCi]S¹QrP&à4µ\\5»jn¶yÉr~Q¨	Œ&µ‘\nçb“Y.§r ç€ÓÐÁxI/‹¼5é\ru—Ò˜L‰#Å¬hÛ{<¹‹\\‚M‚à+íª¿¤—F^,W561#ÝŠšúT¢Qƒg\níˆšq©%ÿb‘Ä-EŽÏò´_´_œS—ç‰þ?åã7¦SŽ\0\0\0\0IEND®B`‚',1,'admin','2016-10-13 14:29:10','admin','2016-10-17 23:10:00','','','',0,NULL,NULL,NULL,'0',NULL,1,0,1,1,'org.maxkey.authz.token.endpoint.adapter.TokenBasedJWTAdapter',NULL,NULL),('850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','http://email.163.com/','E-COMMERCE','57f4c775d02eb00e4f8c62ab79037d8f949a3ac684791843026cf902ec000e2a','Form_Based','‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0}\0\0\0~\0\0\0	‹\0\0\0	pHYs\0\0Ä\0\0Ä•+\0\0\0tIMEÞ\Zq*Px\0\0\0tEXtAuthor\0©®ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0Àæ‡\0\0\0tEXtSource\0õÿƒë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨îÒ\'\0\0 \0IDATxœíy|Å™÷¿ÕÝ3#nK–uÙ–Á÷‰Å}ÄH á0„%¼	²„#l°À	,X$‡+a	$$„BX®pØc[>dË²eÝšÑœÝõ¼ôè²uÌÈ:ÆûÑO”ÕÝÕO?O=UO=õÔS*Ðö\"{]Å8R„RjÈÏZ©> \"8áñ¦fŸ®!¸f-‘šZœ@;:2!ãÊ40²³É(+Å?crZŒ·x\"Vv†i&]OJB6·PÿäS4>û\"Ö¶:<!¼Z“©5®í£´R´›ŠFŸ»¸ˆÜã–R|Ú‰äÎŸIô\0j°î]Dˆ7·°óéçhþÝñl«%ÓÑx\0• bcˆ(åå’sêW(;ïlü•SìþºÖšÐæ\Zª¯½õþGˆ+èq¤\'‚@ d\"•·ü„Â%G`Z}wä\n½ñµ¿SsíMäÔ71.ð}¶‚Öl?yß9—)ž‡•™¹Ç=}\n]kMó+o°íÚÉklÁ3*äŽc¸ V¯Eö·ÏaÊ¥aeföêî÷ºÖšàgëX÷­)llÁ\Z×ï}M“¢ë¯¦âÜ¯÷z¯N_DˆÔídÃ•×‘ßÔ’¸8nªí«È‹Û4Üy/™S\'3aÉ†€Ñó&­5;}ßÚ\rxÇe½ÏÃr[Ú©ûõ8¡p—­KèZk«VÓöÄSä  ãeŸ/þù1u¿ÿ#¢õnB·mvþþOd´¶jkÇÈÃïOü™x[ZkWèZkâ­mßx‹Ìÿ£Ýºˆ¸.d­±µC\\;D¥»ÄÄý­5Zk·+ü?âeô\0Ö¶:k>GD\\[MD¬úo[{ïA~‡\0¶c²ãD¼ñ	Y¨	É˜5ßä¼Ù9]V­¶ãDZÚˆn¬!²yFsÞ@¿ãáÉÀ4ömÎdÆâ4½ø\Zù‡’ºÖ´þã]|±ø>o«‹QÇ&ä5`öþd.Z@ñŽ göl²KKÈ((ÀðxP}	Q@;6N,FG}=ÛjiûôSÚß~ŸÈÊUx¶×ãaísž*K å£ÄÛÛÝyz¤£ƒÏÎ½ˆ¼Vî›š.‚\0QqhÏÏ&ã„cØÿ»çQ´p!–Ï7,¯ˆ´¶±íÕ×¨}è·ÿü„¼¸`*…RjŸQ”†ülfýþ!,­5NKöÎú±¦iÈÙ1S&Q~ñÌ^v\"Yeežáõ#fäç1ãŒÓ©üÊ	´­[Ï¦‡~KðO%¿#Ž§w:A\0jhÀt8Œt„Ø—1\"`;Á²	œw6Îûþââ¤–÷ž¬,ŠªSxÀ\"\ZÏÿ›îº—Ð‹¯“uÒ~ÜWŽƒ;\\¡£u×zø>!v6ÁƒæpÈÃ[9uÔIP†ÁÄªÅ>¼œÚW_£ú²«)ØÞ„i˜i;Þ+hé9„Ë>!p¡9Ã ÷GW°ä¯O‘;uÊ˜z?Ã`Êñ_¢ê™\'‰žx!ì±fQŸè”­vì}Ëns´¦mb.Ó~óæ\\qÞì¬±&©ù3¦sàÃ÷ã»ø_é0ÓwŠ/Zw/¸Èn§Ú‹óXøü_(˜1}ïêê9îkCu_‚Ô<,¾ù&Ö–”Òðï?%/ÛŸ–]}ïU6Ò’F´hå…Ì^þK\n¦ï?45R\n‰^½–Ž÷? ¼ê3b›«ÑÁ â8(C×‡gB1¾…sÉ:ø@²;OQ¡û|\nïœyáw0=š~r~;=Ô¨\'V¯ßJ\Zjºá|?3ï»‡I‡6¤*ìÖ6o½Cão!ôþGH0¦	}LµÂ²^~ƒÃÂ7»’ÂsÏ!ÿôSð”•$­¦×ÃôÎ\'RWKøÞ‡ð¥t<2H×èãwi…°Šo¸nH¥èøhO;‹šs¿Gð­¢cø|}\np»w¯LƒÈ†­Ô]3¾x*×ßLi:hs¯½óô“G§Lûp#¡Ó\0»r’VÅŽÇÈ:çtö;\'ù‘‚U­#vÝñK6~õ,B+WCb¸vå–Äû;ïU««gË·.¦þŽ_¢#‘¤i°2|Ìºþ\Zeyh­ÇœŸè5eK\'±kÂ“ò©üÁ¿uE|$íØì¼ó×Ôýøœ@cïXe(œ`˜ºßLÓîÁ¾A BÎ”ÉL»ó6â¾½£axDîþ_Úvï¶©˜zËäì7-ùA;;ïøõ·ÝsÐ.Y‹ %	…<u?ù·ÞN‰Ù\'çØ#ÐŽ3N?öP¡±n‚»¾¯Ì¤ä+Ç“’³hõïÔß}/‚F‹!{ö\"š6CÓ<½‚ðÉK‰œv%Ñ”—ADÛýÓ¦Nkˆí?¹9¥n*.¹˜¨ßã®ëO{ÂÚ]Ðé0e‹‹fÒ¹ç`ùý)=g·´°ýúŸ¡Cqw\nÖ­i.ò3í?Ê~§.ÃÌÈp/hMÛ¶ZÖÜô3ÂþX}ï1\rÂŸ|FÛëoRpâ	]m0v(u\'óç¿¡Æ€Ë=eœ–šnÌÙŸ²¯.s×RÐò†Gÿ@d]5ô§áZ›3Ã^yégü¦ÇŽãò*Ê9ø®ÿ&ë’óq@#µPwëØí¤é3ƒ²s¾ŽcöMÛhjz—Ðûº8&p4ù\'‹7/7¥Çt4JóËÓgw%\"D}&•7]OîÔ)ýêš\'3“Ù—_†ž\\ºŽ(EdÅç„>]“<\"äÎŸ‡ž˜ÏXlíîùÆ^š®1ÿ‰Ç:(8æèn÷h’ZÞòòkDª7õ[/@ÎË(;fé uûòrÉ?õx\'Öwm\nPŠÀ[ïº†b’tzóòÈ=ñKˆvÆˆ»»½“cXD:7ÿ´i]ÂL:£ñ‰? ±x¿u;:Fé×¾štKE|Þþé5\r‚«?M‰N¥ù_:mcÇgúð½)DðÎœŽ·¨0¥.0ÖÔLð`˜}ƒx3Éª¬LzÚ”5­ÓŸ‰ÂýÞ^½\'ÃH6rF)òX@­?	F’{f&Na\"±x¦×›ÒsÑÚ:tk+ÊèÿsLÓ¤æ?~ŒJ2ŒJ‡£8³†Ý¼‹xs3¾ââäMtñfYzý¦äž&ô´ÙÒKè†5c¿îq2IDk¶~C)t$Jà…×S F¹Ó¾œ;‹©oH^è€áõbfK’áFzuï\n2§Tô2Ð…¡ˆÔn5ýJ¹+k)`0\nDN8”RE#+kÔyÝ¯õ>Ö¥ðMH‰(ìP;¢Æ¼É&¤m€ÂnšžŒIÉˆS\"î6$÷á‘¢ªOh%îbNŠôÒk5:pßæU{4¹1»h¢\rnW¬“[ƒ‘D·-Èh»7M+/¯“¤@lgLùœVáR\"ŠèÖí)vï‚™™ƒˆ\ZpAM‹Ð\"ô^²ÛÐ‚r›ûsrÉ˜Tœò˜®Á½¢a(èÓzý§hˆlØˆH\nyé4ø*Êç»a0çåX…{E¢át„\0…åÏpù¤âHŠÇ‰7Æˆ×î[÷ˆ‘KùøSt8‚™‘ä4|“ËÑØ˜2ÀÜ‰0¡´„œ\r­C…ÝÜB|ûŽQçµÐÝ6Ój•\r¥¯Û€ÝÔLOŸõ`%£¬—?àZ5ÊKãÿšøÈäëÖ´¯\\…EÆÂûÚ…Ý\"gÆRä\nœŽ\06ô\0ÑWñN(ÀwTZÛýÞ%\r=NÓó/ö»ô:ÒE;­/¿ŠØýÓ9R¥çà½G¸ÔXÃë§í­·SZ½2,‹Ig}Çkö[/J¡£›.½†–7þžRý]E)w_û5Ý	i}áµ½ŽÙJé‰´rÎ\0`(Z^x…xsKòÏˆP~ÌÑ˜ófïù…=¡Àîè`Ý9²ökß¢ñ¹¿ojv€aìYÓxK+í¯dÇýËY{ÞÅ´~øqêß¥•Ÿ`7µŒøÎÚ¾Ð“-éå†M Z]Cã3ÏQúÍo$ýŒRŠ’ïþ+5ß¿\ZŸý3V)DCÛ›oÓöÖ;x&9}ófâÉËG)Ñv$D¼vÑêjbM\rØÍAw7Œr«\"ÿ *R±ÚÅ¶Ùõø“ˆ–1:tË7­\\:!Ê`×£¿gÒ™§c$›IB„É§œDÝ‹/á<û:æ`Œ5MD„X}+±úhûƒ=«ÜíßÊ›vœàšÏ“£©Ú?ø˜Öç_F_HÏoI»1]\0ƒÐºMìzêÙaÉ™¦i²àG×ž=…¸3@Tkç{:w?(å\ZŽ»v+.máu›D\\]rV»¢îþÜÝ5cdDö|ï1}¬¦2}‰k¶þçÏé¨®Néã²+Ê9ø¡åÄÎ\"¦d¸iSŠXÝ6œh49š\Z_z…¶7ßqí„±âg¡(-5]\0ÄÚ©½ó—)`î”)òÈoÐ\'CÀŽ0¼Š§9€ÓÚš-‘ºíl¼üjœH|lùÙ½¢aÓ\rÊ4izö%ê|´+Åe²ÈšTÌ‘wÞAÅí7œ9…€ÃÞ-T*F	‰Cì­&´n«”\'fëþvKÇ^´3èù=ii½÷‚-l½õN²æÌ ïðCSzÔ“‘ÁÜsÎfú©ËXûäŸ¨}òiôú\rdF`HÂ¨ê%éüQnîô˜\"^…L,bÂqG3ë”eW-vµ¹ˆÛîù%\rO?‹2¼cÎÛž\rYµ4·HhãfVþM2ZR˜&D°òüÌ¼ÿn\nŽ:r@f;\Z%ÔÐHË†jšV¯¦ñý‰îÜD£Ý7)P^V~¹³gP0y••äNBF~>–/£ßÝ3]äjÍ¶_ÞGí¿FâzÌ¦h»#bL¹ëæôZZíJoQ}åuLÿ¯›É?üÐ!1ÒôzÉ)/#§¢œ)Ç.EN4Ší±¦Àôú°|npf_9b\nš ú‘ß²í–;ñ\Zž”wÜŽzR¼[v©ôýAA´¶žµç]HÓË¯ub(Ö¬Öà8ˆã`XÞ,wñû1-I\\OiÓ…¸KÂEKÆüú2š\nüD8\"zÌù\'H_Bß NØfÝ%W±é¦[ˆG?!TV²äöÛ8îåg)¼îr\Z2 f§ÏA…i;e¨D\"aÖ=õ,;?úxhš>\nEÙÅÅ,¼è–¼ð,æ©_$¨º÷Âv¡Çß»9gÒ»Äm›&\'Fü+Gsä_ŸdÊÑKH{ˆ0aZ%Kî½—©·Þ@«¥]ÿûXIž4Ÿ²‰¶vˆ ‰M, dÙIÌ9íŠæÎÁôxÒ&³Crfž~\ZÙå¬¾òZ2·í\ZU#¯§lÇ&y ¸ÝœFÓs¾ \ZÁ…ÎÉ€â‰d,˜Å´ãŽ£òè%dôîF“E_–þ§}{¥å‡†çWw²ò{—“Q»¥Fß¬ê¡é®…7ÒS6h“(óÉ?ø t$Š\nƒRøJ‹É›VÉÄ3)˜5›ìÒIx22×£•¢WÀi~ûröŸŽ·¼33Ã\r†0Ý\\ž©zúH<7Z\0Ðšâ¨Z~/~ã\\²Ú\"£8wú¨Æ½kÑD¦–2÷êË™¼äX™¸Í…Jc÷ôan°)SŠè®Ö]öCZßyÃ›‰§(oqž	“°‹ó˜øµÓ)«Zœ\\CWŠ¶VRÿô_É(/!{Î<²æÌèÞÇ–\"}…³g1ý†ëØüÃŸâ‹î05jÎÁÎËæûîaÂìYý3)Õ®»tl¨æó+Hpåç(ÓBÛq¢;›ˆìh\"æ¬&óŒ“X0^ÒßÝ±“µ?¸’HMˆF™ž	Yäxÿþ}\nfÏJ‰>L?um¯¤ýwOº)ÃG{Xï#=Â9ŽÍ¤ožIþŒéHâ4¤‘*õÏýUgOpÕzÔnÌìÐ6ÙžÃ!7Ý€áñ$UŸc;lüùDkë1<¯eÄ[ÂÔ}¼3+kÈ´Î¸àÛD\'ä 2²Y%{\Zñ£f½YY”wìÈ­6)E¸¶–m÷=Dý“O£c6*€nOô)*.¹„…_Ð÷Á=}A„Ú¦á¹—º\Z;ßñ*¦]{ÙåeC&;»¬ŒÊK¿GÝM·cŽ \0ú´Þûº8œP^oNÎ°[Íèp˜Æ7ÞdóÏï ²­eY½¤µ&à·˜ýó™yòI)!Bó[ïRó‹ûéÑXSÉü3NeÆé_Ý«!I•ËNbÇò#4)‰?º„®ÄÕ†Óƒ!\"5[È®£7”Â	‡©{ü	vüáÂ· Zº¬òNÄ›øüéqë-ÎÀ–è£þæ¼Ãš‹.Ç‰ÄzõPŽhœÃ`ÁUWK#öææ’»ôhZÿçO˜æÈ„-ºeë]âqv>ñ\n?|è\'))…\nÑQ½‘Æ7Þ¤áùW	WoºC–{jwÈgÆ2»ìûä–”Ê©Š­¯dÝõ7âD¢{ÔŸZÊ·ßJfAþ°]e\'}…–?=‹ÄGÞ’5ë]™Mo¼Eý/SrÊ‰)ucbÛDëwÑøú›Ô?ýŸ–«Õ»å™Ñ&æ3ëº+™sòÉ\0‰,ÌÉªmÝÆš»ŒØÎ–^c¿ˆÊ÷³ø¶›È.M4¢AêŠ·µƒÖxj J1aÁ|<SJÐ·\'GgŠÐ#7¢ˆkÖ_÷SZÞ~Ÿ²3O#£²Óçíj\0¢5N8L|W#ÑÛéØºàªÏèX¿èÎfœ`q4Êðõ™nÄÑš \'ÏÑßÿÓ*S£O)ÚV|ÂgWþh}Ën† &è·XpëM”x`R\Z®cq6Ü|+Ó*™vÁ·û7bEðø3Éš7Ÿöêm0Â^ºÑMJ NÄfç“OSÿÔ³xŠ0½þ®\\©Zâ8‘0NK‡›ËÅP(eõÈãæéI£ÂvÄRXÎgÑEßaê¾ÐíàI´¼ó>ë®»È¶½A!ZÍÜ›ÂÔcIª^QPûû\'¨ÿÓóxJ‹¨øú×ðæöŸS)Eîìé´>%¨ž²MäŒi!±úV µÏ[”7#©ªñ(‘ý*XxÕåÌ<þKC›*EÓo±ú¢+ÐÚÙ£KoÏ4˜wÓ™þÅ/Þ¥\'Ð±®š-wÝ†A¬v\'‘Ú:¼sûºh¿´,aŒ¦þ	ƒ¡çB›µû¥t[iÛŽvy@í7çŸË”cŽ&kÂ„!Ôå:H¶>ò;¶üb¹»ëU©..h­éÈó³ð–Ÿ2í‹Ç%-ðÐÖm|zéØ0¢…PM\r¹sgH‹·pˆƒ0²ª¾OÄÈuz¯âJˆz½x/bæ™§3ãø/aù|]×SbÍÍlºëWÔ=ù”ÛwÚ	c0VRLÕýŒÉ‡ìjJïpBa6Ü|;¡ê­®¡	`*\"-Í‰}tý”fvÚpFDä:gÒ\r1Û& 6zZSO]Æü3¿†âÄ®-Ã)gTqw®¯fõ® ´±Ö]uëÑÚÑ„çOgÉ]ÿMþ”Éî;’6Ä¶Y{íõ4¾ò”Õ[tŽm÷z_t)ËB1ò‡üô²ÞÇ`‰Ù}wBxZ4q­±½ñœ,ü³fPTu\0s:‚¢3ÉÈËëæW\'±)í„#Ô=ý5¿~˜èÎ]]¾ùÎj:pÈ>ñ8Ž½æ*²‹‹“~ŽÇÙtßoØõÊ?›#{~½½v,6i\\GÙp£Ï1}Tƒ(/„ˆ	Ñœ,(ÈÇ[RHîÌL:ð Ê-$gR1Fgž6‘Î‡RGÂo­eãÿ‚úç^BYÞ^	„µ¡“ò‹¿Ã|·;ù@2Ÿ\"ÂÖÇ§æžûAyöÐdwC¤{ó@á&`YŒÙ˜îˆ&\\YÂÜ+.¥´ª\no–ÓëÅè\\Oï¤i¨Á\n= #Qjÿø5÷=H´©åé½ý9fÇ	ïWÆ¡7ßHé¤4Ý­ÙòÛGÙ|wßwï<YYƒ~‹Óm2ƒú\0cúèè¹AïWÁÑ¿¾‹üÊÊ‘{mÓô¿ïPóð£´¾÷1(#Ñ Üït´¦Ãk0ñÌ¯räÅ[R’Zýñ8[{‚Íwß‡ŽØ=Î|Ûí>2KÊmHvsˆÑgÃ>§l£Õµk¯É¼ÿùS§¦nˆ\r…«}›·°ñÞåìzáeWØ‰¹w§M¤²”~t-ÓŽ:Â½–\n-ŽÃ¦å²ù¿åA©¾»eÁÉ6ÈœR>hýá];Á™î}À—Ñ€¯¬”ÒªGäãÚ×o æ7ÐôÖ{Ä[;@õ.lÇ¡ÝßüÏÿY\'¦ü\'aíu7Pÿê›]ï\"‚¯ªŠŒÂ¢+5‚ÛjG%^n·ÀÈÑoR!¦×³÷Ó…ƒìŽÚ>]MÝS¥ñµ·N‘ÞÚíhMÈRd/=‚#/ü¥¤ìª×í`ÃíwQÿÂkî<¼\rï„ö˜Ìú×sý^‹ød5(cä4@dl;MíhÛZÜwO­\r…ixãÔ<ò;ÚW®I¬º™]¾zIXÃ‰*/æ ]ÃþKÚtO)ÂÛw°âÒËèX³q5€>!å“˜ºôhw_Ü\0u‡ê¶Ú¼KzLº÷pívÚÖ|FÁ‚ùÉ?Ô¥Õ!ZV|Â®—^¦ù½‰ìhpjõ¶œE„°Žãì?…ç|ƒé_ù2yyCê]aÇó/°îæ;ˆ·´w{ÚzF„Âœ‹/Xà‰{ß~‡xSÛ€G’ì\rzÎÌzr£Ô¿K(Êú[ïà€»ïÀW8°Ï\\G£D›[nÞBó»ïÓø¿ïÚ°	wPV\"#±)âúË£&èÉ“(9åË,üú×É.*\Z²Áhƒl~ðQjyÓ€18ŸDˆ+ÍÄ³Î`ÿ¿<è;$g×¯‚$Q÷0`L¦lŠÖÖðñE—2÷§?\"Þ°LÄÑnÀDS3M›hxë]Zß—Àêj´Ã°:(7®½\"BÀŽ›\\ÆÂË/aÎ	\'¸\',’¢UÞE£A`ý>¹ü*‚j0RcŠHãØ£8èŠ`$Ñ+„ëvÐ¾ú30º§“Ã´ð½+¥h_½ŽÎý.þÊr¬¬,7€\"$ÞÄé\"v‚Te`z3÷¨CDˆh›X–ŸœC«Xtê)Tr0ùy]×‡\'¡æ‘ÇØúøˆ5´&-p-B‡!”6U—\\Œ•á”q6-\'…-N}LÙFÕõ®\0eà„cÖnîû†i>zºKmÑÄ}º¸¢¥K˜uê2&Í™½×áÕ\"BûçŸ³ážûiúûÛ®_ÞØšv3O1¥pÊ\'2û’™}òIfrlû|;žq6”u#íWÙÀe¬­:´M¬¨€’c–0ÿä)[¸\0Ï×ô0Ô1Yûùoä³Gÿ‡hG¿¾~´Ü¥IÓ¡ã8SK™uÎYÌ=õT|9ÙÉOEØüÐïÐ1gÄ³OuR£Ø}•mD_›<líÛc¡\nóÉ˜RAÑUÌ[¼ˆ¢9sÈ*(Ø#Ôy¨Ðñ8u/½ÂÚgŸ\'¿jG,¿_A‘Æ&¶¿ý6µ/¾‚nowô4ÊãÁÈÉ!³¼œÂs™·¸ŠIç‘Ùy–K’­Ùò»ÇØõÚ›ƒÎõ‡Bšiº!ì10æÍ¡dÉQLœ=‹âÙ³É-.îNÕÝ‰½¸vŸ¯gÃ=÷³£ú3ªn¿òªºë:…Šƒª8àÛçhn&ÒÖ†éõâËÊÂŸ—GFNŽëò\"mk>cã}A\\F=r%}’Ds³¨ºõF*>ÓçëæÅ^ì é¡õTÿê~ê_|h È¬[®§|ñ¢=·‹àóûñùýPQÑûšú@mëÖ³òÊkˆÃ£šT°ÏyúX‰]D°½&ó®¹‚ÊÄâÇ°¿Ck7±íÏO±ão/onwbòýTìEnºÔˆÚ««Yuí	×Ö\'l‘ÑçyÚhznÕ\"öûòñÃ^¯8šPÝjžøµO>Š¸®ZÃ\0-øü¹øM6:Dh]½†—]I´¾eÐYÁH\"-ÆtQP´x!¦e\r›Æ‰£iY½†-þž¦÷>$Þ\0T¯­Ë¢@;Q´mw9rF\"Â¦ß>Æ¦!ÞÞ‘üŽÙa%¢›µcâ†í¦Ç³w-_¹*ÑÆ&Z>ø˜í/¾Hó{uk6Ý®Ú.Ø­ítlÙJÞœÔ’\n$ÑšÀ†j6üê\Zþñ¶ëlRjLùé¢éík×#Z§lØˆâ8k¶²í/O³ý/Ïc·¶ƒe¹u\r”áA)$®Ùü?±è¦†ME„hs3ÕËfÛŸBìÄw¥I|yÃøÆ¶ù5¿ýM­ è ªþoê6ìŽÁ-›ÙùÒë4½÷>Á-[q:\"€;—NÊ0Ùñü« 5³.»”ÌÒIà$¬ød\Z H×ŽY;ØAËªUlê9\Zß}ŸXK QMšH»·Vzü9P\n\'á³Ûî`ñ-7‘]9u­E:\Z%ÖÒJ`ãFš>ø˜¦?\"°vC\"ã„™8r£;h\"%h¨{öšW|JÅ²“(^rþŠ2,¿eZ½ââ;#sµí £b-­7m¦iÅJ\Zßÿ\'Áõ›‘˜Ó`1$zF‰Ôß›øè´³56Ž)1\"\Z3ÓËÔsþÅG•å\'´u++VÒòÁ*BÛ¶à£½<lZ”ÉFÜTÝ¾Â|ü3§“=¹ŒŒÒ,¿»·.ÞÖN¸¾Ží;ˆlª!ÖÚŠŽÄ]z”;KÍî\rË²˜}÷m®¦»&#·¬—,”R8á8›–ÿ–Í?Žá1pÂÑFwþ·^&š;‡ÜDcŠ6µ}çZú­ßpŸéEÓ¨ïH	‰@¢„Ð\r…3Ä}Ã(7õ¶ã8	\rê¾<j4&VCZð,IˆG)Ã0pPÄ÷­$àã4\n•˜¼\ZŠ ¤Ï±ãÄ´¡°ÃÀ0:ÈT\"ûRfåq$¥íâ ,ËÕt_v6dfa§Éy#ã&íh|99	¡O( «¼„fôi5Ç±ïC)×Ý{ô\0\0\nIDATEX)”ß¿h‚k½›–EÉ‚ùlz÷=\n/¦vö-³tB™&uÚ&¯t\nÙe¥Ý	§»Ãò°CÌ\n¸ÇèC-\nhÊ«“YXØ-ô¢óÈ/*¤YlÚL56Ëã^(…cšl×q2=f,;è‘ú;³ ŸýŽ:Å:\'JÜ4	úÆöÕÃò°^bÄò\n˜Tµ¸·Ð•i²èÂï0¡°X«cÄ-Ï˜œ12Ž½‡²¼TKœ Y†ÅâïžOæ„`·#ºŠÎcþ¿œN&ŠÂç:FÔ²Æ-ú}Jˆåe#6Mâ‰bòÂùÌ?ï›]Î·=Ôxá…çS2y\n™(¢Ÿè(¦eyÓ&`}Ã4-\"–Å\'¥I|(²L‹C¯ºÌÍµŸÀBÏ©(çK÷ÝMqi)(L›tŒ5\'dzP–§+Qîxû‚R¦EÜãe“Ò¬ÖQÀ‡\"Çëãˆ+~@Å’/ô’±\n´úœ‘o~þ%^¾âjš[[‰!Ø‹Á$Ã\"…GÜe¼ŸÜ,:×\r0Ü³Š]bÓ\"˜(¼\n²=^:ûl»ñ:L¯·w-ý	]´fË‹¯ðò%—Ñ\nCˆšD‚;ŠL2•ÂRj;­wßƒ\0¶šh—,Ü.ÛBá2QzÑw9øÚ«°|¾=êéWèà\n¾þÃxçg·QóÁ‡D´ÆV‚# éœÎõèjÆ1âè4«@%–I\r¦(¼@Qy‡|ÿ{Ì<ót<YY}×1Ð;Xußƒ¬|àaííDµÆ´ê)ðq±T\"C¾7Ï ded°ÿqK9øêgÂì™?ŸŒÐÁÕú`ÝªŸy–õy††õÕÄb1\\q÷Ú6nå/zHH%Š	xƒ¼’*¿¸”¹gŸÅ„931ûèÎwGÒBï‰h[ÍëÖÓ°b-Õ	î¬\'ÚÞŽÄìT«\ZG*°¼þ,²&“SQFqÕ\"\ngÍ\"{rÅàÏöÀ„Þ†’s}©c8Žß¶å4¥TÚ†þŽ£7þ?y	U¢xºŠŠ\0\0\0\0IEND®B`‚',1,'admin','2014-12-15 14:42:48','admin','2015-01-20 15:58:21','','ç½‘æ˜“','http://www.163.com/',3,'','','username','0',NULL,6,0,1,1,'org.maxkey.authz.formbased.endpoint.adapter.FormBasedNetease163EmailAdapter',NULL,NULL),('a40388d23cea4c5ba93bed865b81d255','Basic_Demo','http://www.baidu.com','E-COMMERCE','a3ac51c6653ec2eb0afa9ebd0ccb966f539d16e64c7450775399330aa19a8dc81e698f87c64032dc548d6ec7dc3c4863','Basic','‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0G\0\0\0G\0\0\0U°Z\0\0\0	pHYs\0\0Ä\0\0Ä•+\0\0\0tIMEÞ0-Ú…5>\0\0\0tEXtAuthor\0©®ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0Àæ‡\0\0\0tEXtSource\0õÿƒë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨îÒ\'\0\0IDATxœíœkeUuÇkï}î¹÷ö{^Ì0Ó`!LÍŒ	(F¥ ‰ Xñ$‰¦¢±ðC$V>h%¦|$)-c™ÒP1)+”šG©ñAAê †‡  Ì0Ì{úuï9{ï•ûœÛwšžéé¦[¨ÂU5Ó÷œ{îÙë¬ó_½ÖÚ[n¼ñë\n`ŒÁ{O§ÓADŒ1ˆ1Æyës!èQÇªÚ»¶ÿz\0UEU{Ÿë±ûÏÏ½nî÷\"²¨ï!5æÜëUk-FƒF£ÑûÎ¥…n·`Ó¦MlÞ¼™V«Å3‰bŒEÁŽ;Ø¹s\'ƒƒƒE7	§Ó)Ø¶m«W¯ÀZû³ûË%k-\"Â–-[8é¤“Ø¾};###¸²,Ù¸q#ëÖ­#Æp¼Ÿ	T«UŒ‘ÑÑQÆÇÇÙ³g¦Óé0>>NŒ±\'œg\"…0Æ`Œa||ï=ÎZK³ÙìP ÷÷™Bµ#©?çyNŒóóõ´%ù•pŽGîÄŒ¯‚\nHß1 Ì¯~ýgûï.zŒd¥€VƒÊÑLÉì7ýTÇgÎ·€QˆÄ‘4Dé¼8¼ª³CícFR!¬¦û©H’µ*B„õŽ\ntA2Tòô † `™ó¾ê pá{ˆE«$‚D¢¤s’¤¤Qâ„¼”\06‰Y\"ñÒ`%Åã0™55‰n!Ï¤BO0½cµ †ú¡KB‰!¨EŒƒ\"1&	¨Xz/NQA±þ˜L>y\nr2‹ZXó[ËdaäT”DpH-e¥¯pÒ øêœZÀJÃD¦ÔÒA˜ð€Ñ2%.×•5;b \r¤IÊŸlhz¹O‘ˆ,,A“\nID±RËïú(Çºêp6¢\Z	bbÈ´`0LòÆß|òšß@Tù¿GðÎ¿ù$ÙÞæØX`µ$ˆ¥”œHÆ±þäÈ©g³<Æç>ú´«gAL²€Á“02;~=Y^P­ÒÕ\nKW`·o±“Õ”ê°(@,ˆ ±Ëhi83<‚ˆcJröê(…5”ÚÀj—L»”’áM3æBN¦%Íè˜Qd5 8BŸ’ÏK®ŽEŠàqdD¨ü·\r<9¨ñDc* m%RÐ@©PJuƒø8…D!˜Lë\'°¬ÌÔÅ”n€`ÒÓ $ïI`.rj:!äD„€¥ŽlŒDŒO“.JJÍ(LŽb $*¦\Z²IÀ©M^ÎjHZ¯ŠjÄ›6~…ü•QO!“•èk+ÙGu2-\nJ®‘ROµ Š	%Ónug¨Éñ’a£Çt¼BGš¨i€võtM°HœBq°Bó9%à%cHÁ\n”v€Lyôã@šÌ•N=×ZP­ê!ªŸ!PÙ÷Z_mŸL‘ƒŠÌÆ£Õ¸QLß5TÂ0	=+\Zå$ªG½Ç©Õ¸/ržû›Î3“~%œã	!<Õ<<í¨—¸&gÿ¢_éÔ<Ô—²øåÈG+Oej/QÍîµöZ_Õ?Í½´b{öú~S‡×G%è]·_(\"\'šìz²$t¥êiÆ)f4Ã¨¢a\ZÉÛØhõ˜rš\\»X-qU^(ÄH° ÆÅ´A ¶Eˆ†€ 6BôC!bÀ€\ZsÌ¤Ü±¨65\'<+_2)8¤)°ÃKŽÚ&\Z•ÜA˜>Äj™aÀ¶þÚi<ÿŒµœqÊzÖŽ²z4Çf0=\rûNòð£{¸oÇ^¾ßÃ<¸gÓvèÚtŠ„Óhã»X”ˆD=\"K³«+ŽœêÅ*±•QJDÔâ\\A~d7gl>™7ŸwŸ¿•†¡)‘\\À1Z¡¡í Ñ ž¼™Î‹Ç9\"/áöŸïåK·ÝË×¾s/ÇQ4Ä—%Òh¡Z\"\nY,pÚÅ,+ N„A$DKNi®¹ê•¼êÜ-œì<™DT‚P†\0ÆDÀ:T\rÆÕ‚Amu\\0¾šsO{W¿ò>ñ¥ÛùÚ­?¢h¯cªô	™8Y•¶-—Äõ‰¥,ž)P\Z[¥T\'Y]ìæ¥[Oã]W¼œç­2d:‰HN\r…X•ðÐ£Ù½ÿ¦¦Pãh7-§=k§­fMCiÆÈ FÄ#£M>|Õù¼âìgñ‘ëÿ‡G:-&Q¼6ˆv2”(‡Åsí¤V9Î\Z2?Å¨Nð¶WÅU¯»ˆQr¢ÀñxÌ¸ížŸóåoßÉ<Ê®P˜\nãð\nb»ØX°*Îßr\Z¿sî.|î)%e­‰\\¶m›Ö]É{ÿé‹Ü·ï\0Gì(ÑŽ‚,Ú ×´òÞJ!ú‚™äŸÏŸ_öŒ–da€ïÛÃÇþ{;÷>´“Ø\Z¡ˆ«	íJ¯¨‚i8ˆpŽiÿ~Ïa¾vÇ7ØºqˆwüÞ…œûœ“X+6°mó¼æJÞý‘ÏòàÁÃ<R`W/)‘¶LE=­3æ½X&Í´g³ûÍXòÚ~?»ü¤³ûJøÀçná¿ïí*™\Z>•	³Š™l„B3Ôf`±ìIF,->Òu\'±¿y2Û÷Þö·ÿÆ‡>ÿmxC4–8sM›¿~ûëXo§YÓWL\"„£c™ýs<¹-“pBrÑÒ-pqo\r%Ù<6Æû¯¼u:ƒoŒq_·É;þù[|ü–GØë†	¦E”H\0-Ò¿X¤ú½Íéj‰ ˜´9ûÛ›¸î[{¸úoâ‚¬\n¹p}›k_	fæ0Ñx¬tèDµX…R,jC‰Ÿ\'HTMÍXf9ÔJzÿ›Ù·WMŒ¬jÒ4ipˆœþËW¹éöŸàM³Ê\rI})rÓ„šèQb*(ŠMy\r)Õ	xiÐ5mnýñ£|àº¯°¯pD“cbäÕ/9•—¿pÍpÓCNâ4V|¡a^tÔNjY„s<\'­aB,×}åûÜ|÷.|>Š‹1AÔ’g¬K)U\"‹tSM\nDKˆ)åY˜ŒIÚÜxç®ÿæ8›`s2…·_ñ\"66¦p=!Ì©CkLe¢ùøþ¥4¨\"ZRˆáŽGóéîà°[M´ƒˆi 1÷ÔRÙMbÄ†.kk[R•ŸcJæKÀÆ€‹UýÞd”!gkøÔ—¿Ï;0£Ðåô±o{6Y,ž(­y›_0\0æ—Ñ‹\"L)|ê‹·±W‡éš!‚fÄHª`ôk½FœvÐi.Ùº‘×_x#aŠ,Nc³¤¢£U)NRÍ¾”Ø5\\÷¥0\r8š1ð»/ÛJ®”ÈŽ¡¿:Å±Ò£!„ÔŸ³â®\\„`3~ºwš›ô\0…\"…W±ª»¢QT$\"êiøi6¸o¸àL®¾à9œ–O3È±˜DÅ%«Ô°ª¿kY‹3Ì·îÚÁ};U0Ê–ÍØ¼f˜a¢>™,Ri:©ë|,Ëòäã‘\"\"|õ–»	ùHUÏJB0šR±2-hÇÞò[/åE†8½ey×/§]ìÇºH4–HŽªÃÆ\0tÈüt*Öiƒ2ãx?¥\n¡\Zÿ¼œ…ú\"Un{í3Äeþ&e³9ÉÑÔý;•¿ší £pçý?§K­J=B_>‡ˆPÐˆ3\\rÞY¼éUÏ§å=m:\\rÎé¼þâói„éT=G1Ú¥éÊ.¸34øÞ]*^Lðœ=¾žÜ\0Þ÷•€$¡¶ú8W0°,6§Ž¨‡šŒh„˜\nñaÊÃÏö<NGr°\r„ˆÕ.PcÈ#då$£áq®ºp3zÓ¬[ˆYÆ€(ïyÍ¹¼ç¢3X;³çš^Yì0¥+øÃ¶ÁÝû¦9¤á1pÖêQˆmT=9U‘I+ó[žeJv)F=Qb-±¡K&U¤,‘ÇŽt™*êHÂHb-Éu/sÏµo¾„W<oœAª°H°X+jàšËÎãÌgoæoÿó;Ü»ãf˜X:Ô‘ÕŒì?Üaã*A¬\"sYÅn<JÇƒÆ²Çâ‘	qZÐ `ÔË¡™i¼äUS$†HPAŒÁ\ZåM¯=«Ï?“Ó²²ƒs/‚h†	é	r93üöÙØ²åJ>}ëƒ|ò«0é 1eþªÒ]Pejz\Z&Š¡ÙjõŒìb4elŽ¨l\0°xi°û go„ý?Ù?Íd°Ø¬‘¯µ¨q©5!núæ¸õ¶3h–a1d¦šœY\"QSØ”\Z¾{ÇO¹éæ»(;ÝÙÎ3©EqÆbP\"’Îël+ÛbÀ°È¢iPwÖy,]_¿ã!FZlkpýÍ÷Óµ-bœm?âªÞe÷~Ë_}æÛ|eû½¼ï-óÜáA†B,ÑL z:6çþƒž}æn¹gWJ‘š&Ð“W¼Ä”V‹%«ÆÆI3{ëlO}N9½êÃ“”LE©7\'“´ßZþõÆ{È$2#M$!„Êe‹M=˜f!äÆxäÃÿÁ\'þôr^ºi	–R:ˆmr×¾)Þú÷_æÁÉœ²¹	ÔcÔc´$’aªé(kF‡Ì\rÄ.Ö,~G°eˆs´7‹N3jEÅ2SÀL¾Š#Ù(] SV)§^²½*íKÄ»4)a”“-®ùØøái&lš?=4Yòîø?›0´ÑºÈÑTê±êq\ZxÎ©Ï¢åS!s)Âe*êY-qZàb‰Õ2A»Ù$ªI-m®	®‘\\§™íÆH1‘¢¦‹ø’¦iÑõ\r~2íyÿç¾Á~…füÝgoâž_LPˆ«r—Ó¤ÎT›TªŽiTÉbÁ9Ï;|‰˜¥æ\0“€œ,C¿Y,=°@ê”ŠéO¯ØV\0Zµ¡ÐSµæFu	m:âÁ8D¹õî_ð_wïaí`‹/ÜþÆ(QÄi’\ZçI£^[˜8ÅæÀ¥ÛNgÐyT=ˆ9Æ4áXÔ³9ËaŸØxô„ôÑœŸô‹ÌvWU·QuHc„ÏßpƒÍŒ;ˆ7³™ÆdoBJ]¨£Åå–Æt‡Ë/ÚÆÉ­„fÔ K°9‰‡iÒ^qÒJ¹b5C7,Uîø1oÇPÒt!q«“w\n®SûÙ²®Á.:›¶L`ªŽõEêV¯ƒýéµð¬zã(1¨™Û§d˜¢”QQœZqš7É{ÿè÷YŸAN	\Z\rò$®xÝjq¤ ‚jòdee¼]ôD\"Q2Ä\"¢¦ôç˜?Ì_þñe¼ø”1†´¨Ò´“’\\‹|Ä^Ýê©GNê!ìQíêI‘oÊÔÚ-«GB«F,YÕPÞ÷‡—séÖ\ri ÅÝ\r¢1Uvzñzõ4S«:¢è/øKš\Z¢iŒ¬vhú	†ãgoZÃµo½”­ë†ˆ\'žè•Ð°xÒJ§eBÛ\"TËä\r²öýŸt±D5 š ü\nÐB”êèuø?;lÕ™ªÉL;-iÆ	ÎÚ0À¼ú%\\úÂ-ŒY%!5yGdM<i…c7a÷‰“·Z(TÐ.N9œjöÓ*ã³º2C0Ù¢žg„c².DÖxNY?Æ–Í§pþÖÓÙ:¾–!…–‚WOz\"Ð<êž9‹u{«fV+Ó\0ƒÐ®ÿèµt¢Ioh¤ê¡X>Ò¾OBÐ„§¦€ShPÅ ¢öÍQV÷èÔùÒ9;µ”`:€¦¥9F{FòXÙ´¥Rÿû‚CcLù^ã±¢Dê>XKÎ|Ë‚–>þ\"rÐöê…\Zf+b)h/ë²Ž~çe!{’¿à=b3LšV®Èr’£ö²8þ•Ì¢\\ AØTÙû”Già{žey¸›s˜¥æ\'Áb¤:»|À°„@fê­}8± °~?¦*¥@èó+yLe••!¡‡\"½D¨­ç¸u}½^Ø°Ì´°Í©Ð¢}ÇÕB®DÞ¸•à­GõXO(é\n,iòt‚´ðX È¬H\0÷õ7•õl.;´b@Cª£÷a@«ôˆ,!Ž9r)K(„ð„íaJú…YÀ¹^¢i¥Þ^ºµ=Ú¦Õ¥²¾ŠÃ“¡ºï¸S\"UME½N§“|:7ïN(ÂÜÇŸ5‡+¦ðs9x\"óXÚÝçlpR©qÊ4›MvìØ1&u<—Õ\\õ61?ü0ƒƒƒ˜v»ÍîÝ»Ù·oßSÍãSFµ:cØ»w/»wïÆ9—ŒÇðð0·ß~;7nd||¼·gWK]pècÜ¿áY}<ßuýß÷C{îxýçæÛl¾ã¹4wó²¹šÍÌÌ°k×.víÚÅªU«Ò¸Û·Wc¤Úµ-011qÔÃ‹Ù9®B˜wã¢þû/F8sws›{<÷÷ÇÚM®_Ø@\"\"=§ôÿ\\ÂZFN\0\0\0\0IEND®B`‚',1,'admin','2013-07-18 15:51:46','admin','2016-11-06 15:49:25','sdf','baidu','http://www.baidu.com',0,NULL,NULL,NULL,'0',NULL,12,0,1,0,'',NULL,NULL),('ae20330a-ef0b-4dad-9f10-d5e3485ca2ad','OpenID Connect 1.0  Demo','http://oauth.demo.connsec.com:8080/oauthdemo','CRM','ade8aeb8b9513880baa804887ff89571e7fbe584acdbeff154519a5a39f6a567','OAuth_v2.0','‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0P\0\0\0J\0\0\0,\\ÓP\0\0\0	pHYs\0\0Ä\0\0Ä•+\0\0\0tIMEß47Þa~÷\0\0\0tEXtAuthor\0©®ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0Àæ‡\0\0\0tEXtSource\0õÿƒë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨îÒ\'\0\0#IDATxœÝœÙsÉ}Ç?Ýƒ)’©ƒ%je«DÞ¬½q\\•¼¸*@*Uyq9þ’¿Æ/~IU^ò–¤*oÙªMÙY¯–ZíÚ:¬ƒEqÅ\0qsu3œÌ\0ÃŠUC€˜î_wçwwÂq¥”BA”’>?y<£¯‡gä€R µßaI8Ž£€žE(¥BïµØ“$å¸¯RCžç0Hzo”R¡+HhCO9î%5„Ô°7a|ó¯ÀàKà\ZÎöZÿñO´~õ3ÌÕ!cúYQf(£¦¡€ªØ›0þ\ZëÉ¢\Z%Pbd\n†lV>L\0ÎÁÞ|„ùå¯\\àZD6‡›Fµ*àØ 8èþ9SJ\ràI:D^J öúo1¿ú5Ö³ÿr™@ŒMƒ²]àP.xC¦¾\0½oðÿÓ ,«IçßÿëÛC™í^àA®N$è}½ë$(ŽèJ”ê4\\©S Æfº*j{†BAŠRj	<©80*Õ	<þ12‰2\Z)€¨ÀíÓòÄýÖÞÀhüw˜ÈË\\‹ê+qî-j2Œ¬Iz\r“‡Tš(‰ÈëéP\ZÊ$Ýè÷ù©ÿ#ú:¾;Doœ*égDOŠÂ-[EòvÜè4)Î¡\n!\\	äÎ\"JpôQ\"Hyöòôæ—$D‰ìpöxØ¾§/I$!!.|vöÕ˜áÈii`*wV ‰p@’¢}äýüÈ‡SÎ\n0X¡{½ðpì`Ôöª\rT*`ÈDJ8zíôYÓ™g\"ÅV½»Ñ¾bp ã2	Ï+©’~štfõÀàâ¤”=‹³m)»ñU\n~®Ýsy:Žƒ){ÊÝò9½*Ò@\0[@PJõ\0fõzƒj­F­Z§ÑhÐlµøá_|B^—¨n*×\'2õC\në¬¿}K~*O.7ÎT~ŠÉ‰	r¹q4MÃÏœª~R€fÒØŽÃVc¼vÞeÙ•ý*Åb‰b©De¿J«ÕÂ´,ARJlÛFè~m¥ßþ8\nh·ÛlmíP,–Ýb¶d³Yr¹q¦§óÌÍÎ2;;ÃäädHÝ­\'ˆMÒú3iŸDÚÁ<is‡½b‘÷ï·ÙÙÞ¡Z«cš\0š&‘B¢ëz·tàVcº’&•ëª»”.¯l6Û,Ë¢\\Þ§X,³¶ö†‘‘ff¦YX8ÏâÂÌ$©®§ßúSÛÀ$ð¢µZmÞmn²±±I¹¼eYH)Ñ4lVõ	Wºq«ô~¡ª_=Æùàßè–¬‚Læ`y–e±µµÃû÷Û<}ÁüÜ,W¯-±pá<š¦…$Ò3;\0õzƒBá\r7V®36:Úä±œHÜ@o7ÞÑh4BtAËúm“Ì…ðþŠÃ§tê€Aìüü1„@×ÝåZ–Å»wß³ùýçfò,/_eiéŠ¸Ó4y½VàåË×´Û._¹ÄøØXÏ\Z2GqA©ët:¼zU`­P Ùl‘Ñ2ª9 >óíQ mÚ°;@`œxŒ›·½«\r¥ò>{¥2¯×Þpë£›,.^àýÖ6ÏŸ¿ \\® e44MÃ4Ìø’þ »wäÃ“º·o7xúì•J…LWÚ”R(Ç‰gQ¥°,¥¤”ŒŽdÑ¤Da\'Î\'Ä*À/“É iÓ4q—Ÿ&5„®zÇHµFFË€€ýý\n_=\\erb‚Z½ŽRÊ79¦ibfìVDªrVpP)%ív›ï~ÿ„7ëHáz<E@Ú¢¼„·çá`Z.@£££ÌÏç™=7Ã¹s3LLäÈMäPÍi2x½~ý\ZçÏÏS­Õ(K”Jejµ:†a\"¥$£i®yPÁž^Êµ—JA­^G“\ZJ„£v§ÞÃ«oÝR²·Wduõ1û•\nzVxÀÇóÈ¦e’Íê,..réÒçççÉår!¬®d\n	2eî.\"“affš™™i®.]Á¶m*•*[[Û|ÿ~‹r¹‚RŽoãz³…Ó)¥&»i¥,n¨Ô4Ô3Q…ƒRé·±ñŽÕGßbšæºöN)LÓ`|lŒë×¯qíê33Ó¡1º‡Ãp³Ù\reÜâÀ @ZÄÔ,4Mã\\W²oÝºÉöö.…Â¶¶wÜÞ]Õsô§úÌ›E§cøÑB*/l$¥¤PXgõÑcÀ÷þRgYš¦qsåÝ¼ÁÄÄà¦]Ávþ8\n_¢Ò§röšVP)%/.pñâ»»{<üú­V!$éëŽ‚NÇÅ¾\n÷ë”¼¯W#¥DÊ^Cz0ŽÌ¦ÉüÜ9>¾w—ùù9à\0¸tßKæúÝïOÑÌ¡T.SxóÖwiÁó¤Î0ŒX_‘Æ„lÞ£oÝD_ö±w”rpl‡[­pïîm2™Ì!;`p’–6‹”’V«Å‹¯)ÖéôŒ~èÒ¡Ã0±m»Gûbm j§ÓaõÑcLÓt;:Nâ¢r\\Ñ~ðç÷YY¹ÞµoNzà\"UÏ%7Ü‹Ã¶ÖÖÖxúìFƒl6‹®ë‘ù¨T{).€¶mûYK¢Öþž>û#åý\n#]‡ÑoÿU)ÅÜçúõå¾R—¸ÛrƒT,r/vWN199ÅíÛ·¨V«T*U\Z&†a„Ê_ÑJQœ†	!°m›NÇ`dd$´¶L´³§º¥R™Baì\0‡njôñÇw|ðÒn‘†ÏÉ.…ó ö{Eb<MÓX\\¼Àââ\0lÛ¦ÙlR©ÔØ¯TØß¯P«ÕÝªiúU)ÝBGTømÛÆ0\rw¨ \n\'MðÕëµ@¸â¸Õß\\–e²¸¸Àn}ò°©@VºU\0‡ë=JÞ#a/¥drr’ÉÉI._¾¸Ž¡V«S©TÙ¯T¨ìW©7êt:¶í ¾”:Ž›Îõ8)%µz÷ï·3<×id³:ß»“º^˜¤Âa¯{8ö„¯\'É€d³YfgÏ1;{Î¿ßjµ¨Öêì—÷Ù¯T©Vk´Z-Zí6ÍV»\'ó%0XÚÞÚ¡Ýi»+AÜŠ…ÅÒÒe¦§ó‡–¾ÁÔ_ROŽËë£›\\.G.—cqÁU}Ó4i6›”Jûnª©Â€d‚ˆzm±X\n¶0^ÁÎë¬šàÊåK‡*“\'mTE½ã 	Þ=Ny¾WJÃcëºN>Ÿ\'ŸÏ»#{µË.õ¨°eÛÔêu×;õ„³¶ÊvrããLÏLŸÌþBî/á¾q^øh$\"|í£È`#!¶eÑétººÞ\\°ôÑÅqr¹œâ¤Ù[éG½¹Åálà1‡ïK¡”3B~´Ê	õ¼ï¾tg«iÀQ\'mÿ‚Oìè™ÈYPB*7`r>èÝ½‹Páø³1éÔø„´÷H$£§°¤”h™à7 ã«¹^ªçyß´*·¡äŽì?˜WË’@†¶òÇA×uÆÇÇÝŒHz´RJ\ZõÍfóP&zá	H‡ZK¡w÷*ŸÏÌ}¥”´;;;{GVá^)<¿“¼‚<ƒó’ôÔ1Â…ósî¾€ç£^Wå¤¬¿ÝÀ¶m O0BRJvÒ«ðAa5|vù$.onñ±ªK¡0Æ{?77ËT~Ò=j!£³=X£¦iìíY»áZ…4qO8øÚ;PŸžÃ ÿˆ¯‡®g¯^]ê:ˆîq_–RòäÉ3jµz¨VÖO-âèhÉ™×wˆ60ê…½Å/_]b:ŸÇ²,wq÷$}„4M£ÕêððëG†1­Š@š\"}˜ÎÒ´¡‰uÌŽd¹{÷v*æºžao¯È—¿{ˆa~ùç4é,¶¯\rLò ŽãpùòEVn,c˜¦oèãÈQgkk‡ßüöwÔ\r_SO²W¦ú´îÉÐ?œ0j¥÷îÝaqñ†aô\rW<ww÷øâ‹ß°¹ùÞ/—§RõFv}Z‹ž°{¨t0ÞÂu]çGŸ~Âìì¹Ô’Øl¶øß/¿buõêF\0ÈðÃêìQï,\rzø=60H¢[uå\'ùcæçf]Iì·c«š¦!¥äÕë7|þùÿðôésšÍ¦¿ç7±^>Pä¸+Ü÷ôTxP’0ðkˆããcüô¯>ãÊ•K†Ñ—¹N6«ct~ÿ‡§ü÷ç_ðÍãï(KþÆUPÅ¥ü­Qå¾ïs…Æcx*œd£‚ f³Y>ûñ§ä§¦xþÇ—X–…®ë~œÛWdµ,íŽÁ‹¯X+¼afz†…óÌÏÏ‘ÏOù‡0ÝiqpØ\'‰zj7§ìD’ö|¿/µUˆBîÞ½ÍÜüß}÷Š¥2z&ƒ”2þAtp)Z6‹£{Å\";»»è™ããcä§¦Èç§˜Ìç§EV‰þ*©<?,@©ÄM¥“¤¤X0Õá¢ègŽãpáü<ó×?åÅË×¼~U Õn‘éé/*fÐ½cfJQ¯7©Vël¼Û©1*>s$ÿ¨¥€@5÷7e”ƒ²ÚÞÁ;]:Ò_O\Z3™wïü€¥¥Ë¼z¹ÆÛw´Ûm4MCëþpNJÕ¯Ø£inýÑÝ¾’(«Ó­©p„ÇBµÊ %ÚÂñæã¬h \r„þaŽRŠÉ‰	<¸ÏÊÊ2…Â:ï¾§Ñh\0ø9šÛ?ÀL¹§±¥yâ`gPH°M”QEŒåÉüÙß¡ÿè—d®ýŽ \0\'I}3Á£è&¸GA•˜œœäþý{Ü¼¹ÂÖÖï¾§T*Ó1:Rjþ×¹ð>·È¨ÂUU«ƒ2\Zˆ‰yôþé/Ð.}âMì˜Ë?>ùg¤ã(\nZÐQ ÇÆFY^¾Æòò5*•*;»»ììì²_®Ðj·±mpãE¿¸àrêêle4ù‹d?û%ú§ÿˆœ¿åèÿžÌð¿­›xF:íQh¡Èç]{såÍf‹JµB©´O¥âîi·;˜¦…ãX(»ƒÓ®\"ò‹d?ù9úƒ@Î\\s™ÙÞO?-pI\Zü‚åiñ{«¦iºgNš-ZÍ&V³QfÅyÎÄÝ¿…‰·¡mØÂ3¦AB”`u8šDÆñRÜ×Sý¶0TàÒ’°m[Á`Gr\Z”èý•s`‡Di(•\n÷³Ç™ØIñ&…Ž·)¸¸è‚Ci=ûÿòU8öæŸ€„œÛÿ©¨ÛiÐÿ½Pv‘ùYI\0\0\0\0IEND®B`‚',0,NULL,'2015-01-08 15:17:35','admin','2016-09-27 23:02:48','','For Test','For Test',0,NULL,NULL,NULL,'0',NULL,8,0,0,0,'',NULL,NULL),('b32834accb544ea7a9a09dcae4a36403','OAuth v2.0_Demo','http://oauth.demo.connsec.com:8080/oauthdemo/','HR','4e1d7eb7b14ad658e8d9066c95902c852ff6494512a742a8392d1d16adc5af551e698f87c64032dc548d6ec7dc3c4863','OAuth_v2.0','‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0|\0\0\0{\0\0\0¶ä\0\04IDATxÚíÝ°œUÙð°ŒÈ€¢ÂXÀˆ Ò¬ˆmì‚½¢‚`öØ\nì;ö†(`öŠ‘(Ö5Éû=¿ùÏœïÍî½»—‚Ü3óÌî¾ý<ÿ§ŸsÞÌ·ù6ßæÛU§-\\¸p“g>ó™ÛpÀ{=ùÉOÞÿ‰O|âÂÇ>ö±Ç?âX¼ß~û]øà?xåýîw¿Õ÷¹Ï}VÞóž÷¼ð®w½ëâ½öÚëøÝwß}á®»îºÿîp‡½n}ë[o³÷Þ{o2˜oëW;âˆ#6{Ó›Þ´ûk_ûÚ/ùË~Ñ‹^tòóŸÿü%Ï~ö³WtÐA]ß-X° {ÂžÐ=úÑîþð‡wxwÿûß¿+À»}öÙ§»ûÝïÞè]Üí±ÇÝïxÇnÇw\\±ýöÛ/¹ímo{ò-oyË£ov³›-¸ño¼{Ýr³Á|[wí“Ÿüä†ûØÇvüÐ‡>tÈ{ÞóžŽ<òÈó\nôÕo}ë[»×½îuÝË^ö²®\0ï9äîYÏzVtŸ%\0Ýóž÷<û†ôÜç>·;øàƒ»§?ýéCaØÿý»{ßûÞCÐKÃ»m·Ý¶»Õ­nÕm³Í6]>ozÓ›®¾ÉMnrÞ[lqÂ\roxÃC6Þxãë‘6Ì·µßN9å”¿þõ¯/úâ¿xÚg>ó™•Ÿþô§»øÃÝ»ßýîîío{÷¶·½­;ê¨£º|àÝ	\'œÐ}ë[ßê~üãw¿üå/»óÏ?¿[¶lY÷×¿þµûÇ?þÑ]|ñÅÝŠ+†´|ùòîïÿ{÷ç?ÿ¹»à‚ºŸÿüçÝ÷¾÷½î³Ÿý¬k…¢Ì·ÝvÛur·Ùf›u›o¾ùðûõ¯ý•›l²Éi×½îu]óš×Üy0ß._ûÉO~²ÅOúÓN?ýôS¾ÿýï¯Ä‰\'žØ}îsŸë\nôîóŸÿüØ3Î8£ûýï´!€ÿú×¿ºÿûß¡áïÿüç?]û}åÊ•CZµjUwÉ%—t—^zi÷ßÿþ·[½zu×oã7¿ùM÷¥/}©{Õ«^5€­¶Úª+»ë\\ç:]izwík_{ÕFmtÊ†nx@=úƒù6y+Ð¶½ðÂ;÷Üs/øÝï~7Ôº½+Ð»þð‡ÝÙgŸÝ-Y²dð?ÿùÏíõÙ Ñ-°¸í²Ë.ù\Z!è7ûñ‹_tï{ßûÄCm¯Çî\nì¡l°ÁÔïÃŠ¶Ì·ñ­ÌîûÛßŽ-à–©@jî¯ýëî·¿ým÷‡?üÈLó\ZTç1Ík€î:úÓŸ†æþ-oyKwôÑG»Îü² ÌöÐ%”« ¶@Œk´ß5*Ò<ºÏåEÇí0˜oÿÏto]€Y¾öbÀÀeÔÿøGþwtè¢‹.êþò—¿Œpæø¼óÎëøÀ²ûÞ÷¾Cß®}å+_•Jçü\0\ZþŽ–/]ºtìÏ~ö³‘¦Ÿ‹øÆ7¾! í\0]\\tdÑÖWw 7þÑ~th™èeL7-.3N³ì|~€µÝg€ŸQÓ¸(ü^÷ºW÷‡<¤[¼xq§}ík_ëîq{tûî»o÷‚¼À¹@^ƒ8^~ZÄ.²Ø\rŸaTs§=íiÑôÐ²¢C‹6¾Ú]ÌÞ÷ä“O>oFgžy&Ó-Rfri`£áÃßßùÎw†QùK^ò’îÔSO,†Ï¨é\0\'D|m8ÝþÂ¾0€\'˜kýþP£?üp)Ú0`«œ|è»«XÓ-Z´H?\nøÒøÇðÐéEû^-€~ÿûß¿YEÙ‡—9½ôÛßþö0Ê>í´ÓøA@£5\0çƒÏ9çœaåIOzR÷”§<…	ÆL ŽÕt$hàð‡>ô¡¢úYG¼â	OUð†€o½õÖÝnt#©ÚüªtãÚ7¿ùÍŽ;µ _Ztøÿt!ç½ï}ïžýèGK«˜C>h˜H³£Ý-œßæw1\Zèè©O}jW%Ty6 Ö\0½õåLz•UÎ—Ç‡Ë×UÙ†€W…Îñkhx:ÀÏGo¹å–CÀö°‡u»í¶[W•¸!¨34A¡ ‘[h_\\´çÿØU;¨4`¹bIUÉ€-ÝRˆO4t€3á@¶êðiú«_ýjçôé/·Ýñ\0G¯ýë;ÖåÏx†ßÃòj•gEôIÙF‚®UÌ¡â†˜s–‡b-†÷› é·ª^\0O4ÐÿÐ‡vØÆUö<¦\0ïÐ1Ç34ã‚4@Ñn4t¦àUeð0ªRj÷ŠW¼bb\rŽÐšTÒFšv~œ ÝùÎwÆh\Z=4å€<à\02°à×\0]ã¯™o&]¹V!§m|ý$¥xãßØ÷íÇ”‹¸êt5 ±Õ›ßüæRÀ®üà|jØ³‚Nƒ¸þàri‚ã;m\'\"h\0÷Ay·Íy‚6\Z€ÿ¨G=ªûà?è¸TâF‚ ƒ4Žù©{&-…`‡&m„?…tk\\ãÄúÜê*viÂö¯yÍkÎf*øî¸ãŽëÎ:ë¬\0\rÈ|§Å~ô5Ìº”¬L\0àøp4=ÁÜÇ?þqÀ®x[CžtÒIÊ²CŸû«_ýÊv@³>G‚®}ä#éªvÎœmó¬ÀåÛ\'ýŸø‹ÕnÒo£u-èg_ëZ×Úþª4½k\rU.)À™^\Zü–ª™ÎIaT½†æøq{\\WÇv•¢¾=ÀÍõ±ÇK“Î÷\n²lcÎýv€°ÑrÄ¤·E˜TÓÍ¥Á¶ÇÝqêø¢}iÙÞð†h>À\']P¢´iu¿xÑ‚¾¤>w]ïÁ.¿¶G¾ô¥/}é”ŠÊiÍ\Zjó—¿üe~s˜ÏÖXs—aÈšxÐUÊà˜ö€Î”ªpZÍZ(ÒÐÇK³Ê¨`Í~÷f]Œ‚ÑnþZ)Õ³øNØ!Ü‹Æx€¤á@÷›\08Vvàw\"ø±€#í_ø‚è\\­ †hûá\\™GúÒúÜc½»&ìR€/Uµ2ölLÄp\0‰pùN ßþö·ïöÜsÏaJ³Ã;ø\rtÁÙ\Z û-²fº_ùÊWØm¸R8ƒ\ZÃÍ§>õ©îï|§Ñ-Ïb\"!¥’J1É|x&?(¯ú–^ÿøÇWg1€ëúIk?½·¹ú\Z kâ–Üà\0Gâˆ¡€Ži2>è»¬w`“¶;ðÀ—(ˆ˜t€YÀ¦Ýˆvóç´¸\"l>Tz$p:A`’ª…ü––ÑP×lÊ®„»¨ñòÎ9íg\r¸±Kp®€ÉTb}Ðƒd¶‹h=€‹àNè<‹ L$Î_ÛgÂ¿ï~r\r°ã*¢íX©éRJ¬†R]s(\0i‡zèÐ5õ[\"ø˜÷òéÛ­7`—ælYt_*ˆ13y´\rÅ¤Ó0 î¼óÎ,ÚËo‹0·»Ýíà‹ ´ ;.\0\'À³ß=X˜iQóðÞb¦_ê…Ù›nº)¦aÞŒä ÐF… #ßkÅtnˆ\0±\"Ì<l\0ÀÛ!UÖÇ€ë:7\Z.Ðéeîí™sž–°}¶sêsËõA³7~Ìcs0iÐ;ÞñŽ\0Mó`€3GháÊd\"W`Û\'\0#IÕúi\Z&\"ûgä\nØ‚(U4\Z„Ak•ø]B“/þd%X>?ÀÇ¬ú˜nÇ[UŽ¥\0>\"8\"¿¹çôš\\¿}–“®ô<½¤õ@?ò‘ä{Œ1y\rRhaÒiÜN;íÄ”2¿ü<JsÌ¤l<©œ€Œf\ZMÈ€ëŒjJ“Y.ùÍ(Ó¦8ÿ.ƒ0L:´\ZUV>»ø&Ú°Å„¸ßr-n¨-Î\\i`×ìÏ=I~Îsž#À‚[Ó’h^8³n¶Š@‰&óã>£ñ>KRRóÉ2˜áâ\Z´Üv99s}y@ãÇùëË­ù@Ëo;<HÅ-y·x\"Ú«ŽO³	°Å,ÕMJi&m{ï×9Ø•ZíQ\\ÎŒ–IçGv\0ïƒ®cÌ/áË™µøtA•\0.Ú¨Þ6ŸR/Õ5Qý¤€ŒóÛ|(p\0NÐ&<w<ÑÖvÂKg”.-+§¯Žç|\' 4¼#$mí}÷uvIå¦Eg™Ì\'¡q\0eÎ\r& ™@—«mTøÞšzL‹Sju±‰Šf±»Ýín“0¿?á@\0Öþæz€ß¦$ËZ­íŸƒ¦¾·È_?ZÀ+‹É=øô¡ðOÑäòíýÎ,Út\0~—»Üåˆ\\4àêÐÐ8ÐÛ¨]Õ\rÅW“~¿¥W*RªsR;Å–hÏ8J¬Ößç\\VÄw¾0`·$`t.¿L\0&u\"¢‘LzšX¤Ü íb—˜ýijïÒÖöG\\á`—/Ù§RKM˜r~Ø´vè¨z€OaÆ§ Ì‚‚w½ë]rh@LÂX1€‰\r3	3kZÒ¸c„Ú4hœ+È3MEŠF¢wM‡ÕR±Ó¦p´þü’¢}®0°+àÚx—]v9#ÿ˜b šžð@IïƒhŸÆÈY¹)NÛêˆb²=ÍŒ:Gep2©\n)ýcl³à@Zç:£®A3Y“<mü ¬Ö¯Ïu”’µ×>ã\n›#W‘õBÒE£^üâU0…€=è}\r\'™§F«ta–Ï–0:&µ;&?‘ËpU<¹ïÒ7Ù„áÍ\\ÃwÛ’Ú	äâr\r³eäÜ¾ËÃ¹²ôTúÏÛ7ÿ,Œœ°)ÐÌtüj¯½p­ƒ]Œ¼yåÏ›ÿ=ÓlS”|¢q ÷#÷c|—w+[*2«:fõ	S;\0¶\\Þ¶–¹r|f·vö;6ûDJÁ>ÛcDÏúé»Y`Úì—V4¿	€gò}õA§é}Ðýžt®Á}sÝ‹Šn¾¶}÷Q¤ŸÙTP\0¨œ{èhlÔl\"4LL¦ÎD\0–`àûÂŸNˆøuû„€£hbâ¡ŠœO”BŠO•ëè«a[&¼÷\\	™xQÿ$ÏÝ7ï©»_M—µ×=j­]>{û¢ü¨øh9¤O&}è#£v¦œWhQR¬k\'¢n	£ÚßÑÐŒh¥FÞ?Oí›Ï°c€h{4ÔÀÛõb2µôö92ÑvVÉsDH’$ûúœp4Ê§û>¾¼¹´8±¢híLš(ŸuœóßÊ¡@vˆYzø‘¾ØÇ¼ˆ×@‰Á…0«%LVÖkG-[í·Ï¸ºÏ\0`\Zë8çÑB96ðù<SœdÜQH.,`Tö5‚ÇÒ\0»_Fõ<ñÛÊÃ,ˆ{»\'Wd€Ëq|Û§>èIÙhûÈIù>Cóüí5[`ß¦|åÅQoQ Ý@V†# £5\0¥<jH”)†A>Ce³K“tŠ/\0ˆ¶&€Â\\ûÆË©\rÂ`†ùpS4Ú&sPÕSÎ½:°\rh¸gÜ‰åÃ1åR£gK¿ÆîøâÍY=€Oº}	(³¤é6—7;,åD€+-à!Û£é£|9í¶Ïö€®É¯`ÚØ\'û0XpX`çØìÏüp\0Ðl\n\0¶6\Z†+‹R!ÁÄX•\'ÐM[–eÜâ·ˆe\ZGíÀ‹ÈpsÕtË˜Û¬â°9ƒ]U©Í«³K™NÑª1`um€ûô8Ðvˆv3›:èz€ŠéG)s\nr²NÐÙO£åÐL+íÿîw¿Û]\r\0ŠA´Ü3$¸‹à@y>ßœÙ-ö£t³x¢å}Ðx¾£QË—ñµ!³ùœ\0¯”æ€Lëµ®\n¨L:À£å£@G­¦ÓnsÈX	¦O¤¯Ã‡úQr4™i¦Ý¾‹–§cdÀ¦IÀ6D*M¹‚›²¯g¢½y^àÏÆÐtÑ;Ð#œã¨-ã]@¹ûh†_SðyÀ\\¦oPo<:Uj£ü!-Ê:\rG“švšY¤fõgšù>MsåûÌ~¢`•0ŒÅÔkâ…¼v5}ä:aüºg\"´+nF&1	èIÙKfÁ}Và{Íü\0E¤ÄD§Ö57˜\nðz§\n¬V‰pUÕv(f=ÔÚ:í6ULA£Í{[²\r€2Ç—ö¶ÇÒ¾_ŽŒé˜l¬x·Ì@E,–™vÈ@”F`õcµ ›D-ðQ4nva\\UŸ;MxÕžÉ1åÝÒ šDH»Cã@oÍºßêØ)ÚÀQ”©@ü K-jIÀD’]K\\p%5óà17ZÞöÈ´] É¯OºkI	p£´ÍfÞ¥™x–©S‹&»üæFUÉ:oÌìR`#Ú=è\\*F»ùnD+Ã”ïa i?©$JK0$Ç:_^Í¤Ë©¯Ìf‚¡¤åÖô…%â¾Lv”¢	R™÷¶/}ÁÏÌsä´ÞèQ ç³#Sp×·}£I\'&îPU¦UònÃŠ€¡ôqšÎŸ÷ósù°Àp€ÑX“ Œùèâ˜g^Û4\ri€é¿LŸzºJ™Iæ´Ï¡³Ï€\n‹“TQ_-4Ð¿	°g&¨Ø9>[r-)k^8Ðg[ìàw;^ž¢Õªë]ïz“½[¦†\'Vò,àuŠV›ô±š°CÌ¹éÇyã!íÕ)~Ü6y6ùlIõÊ~æèÃøvÛLqž¦I×R\rkóøäÐÀ2!aÚfu‹_Ž<£‰Š‰UôG¿ž Ôýr_’öá³6ôqÀ§òeÂÇƒ\']Ar‚Ü—ÿ±Ž;\ZŽ0¯}¬–Üp\' åÈÑâh2&yƒ\" c{(çX=RBH@øÅi¢rÏù©Æe©/\00Ý6ŒÚ\\Ü„Ñ¶ÔáÕÞÅ;7Zœ>$þ°”È¼¼ì¥ï®Å\0ØS®™¿ÅðÆÈYÁ®JØ¦eîÎ¶å6Ì`´»zØ¡øq9«tEÍÜd´\n3¢É4š—5ÛétŽ!¬Œ‘1#`\0\"“6A!	Èü€Ý\'«J”n})ä4Í’& y¶âš€çõìîŒÒZC¶âÏÔðð…°à\"\'5ï)µzýôLç×Û\"gž÷V¾g·\Z¸à†A\n¼@†‡ønfWt.‡wã‘`3õÑ0•\"ÚkŸc0ˆÆ`mJšÎqíIs.@rª|Ñz‘1€Rù3T;M˜r–¦{fÏ®[,#ÅåF¸0Bž÷Ã4€{F×3‰3Z>•¦W3kˆ@é÷e%Ð»ÍxÕ¢püL•I„€øL Ç´p\Z®ŒªØ’ê“@ña*x˜œ}:ÐuXA…À¤ p`éô$Í0¬ë3ÝÈ=¾g²ÏD	ÁUÞÑâÙX÷˜´œq}\ZÝŽ¹t}¡<À¶]tù{_2™²”.+Wû€Ï˜§k–5ë§øÈ[ g¼À>ZôZÀ“2 {$è¼ºíÎgâ0PG¢Q|¶¡Ê¼öÊv”ý\n/îah{´ÛöI›R0€‰©îç9\\Ó\'­æ+ó6&ÁWf¯L1Â&ov.Àõ\'\\îŒO,û}Íô*©[Î‹€|ëÛÇ½dˆ¹©é™ÿ.SÀ7¯þžðòÛ§™Áié®¥¹¨:PÑ\Z Ø¬»f!ø,R`uJÉ‘0ØîwÌkì‚Ö1µ˜þ;¯ï˜¤a¶ëb^®ï9D\0ÄŠ±2˜žµêîç»¥Ès\0<`Æ’ð113Ë¡²_ŸbM¼Ú›6ÆzÎh¦Ò4¥ ³žU«©2‚„Nv™áM\nèsMía’l¡>èÑðP|¹è\\ŠCr™´hW(’­FaØYHH¸üP4K“‚@Î;c¤ æ´ëx?]RqŸ­@	Òäáñéb3nl·Í}]o*“0€GK]GZ«|¶>%žØîI°Û›rE3	ª%JÞZ_øQ ‡€rÿ,ã:wì?;ØÛ”D® ÝÓº:íµffóõþ‡Ù6ÉÖ\0]§’{Ó\0cH¦-Å·3ÿ]iñ»ÎÁ(¤S‚?÷ôrQ¶grýTî¤…˜Ì]Ð¨Ð>‚DÓ]‹¿¥ùÓ4Ó¶2O.}á³Ý¬ˆ¥jI\0­uáM\nà€¢&‹¨±âqAÜ¨—\0[ÑƒWÆ/Vø;‘€—ÉÜ»HÞk¸\rÀÞ2Ô‚“³ÎgËuéJ±ÜS¹ÁQ\0O\0ÊÄéÑ\0´Ç8s0¿ev“5iÀö?íxß˜2°]0è\Z\\‰í€E	žëcÐg*ÀÅ9Ü ~g>]úœÀx¬\'°õ#–\'ÅÐ§¼˜\0?õ—‹r¯LsžAÓSäÇ¤®µ÷HÀK;÷³òƒß±ö+¯ÆDÑðh9ó\rh`=”ä—h·’¬í:\ZÔ\'\Z ]q,èpöEú]S\0¦¶ž(6 x¬ó\rPD;\0é\Z¶»†ª_*Z\0f¦|Kð¦lú*s]BIQ<³gÕÏé“0a¾YC#}Ež ³H@C„ÛvŠ@~$èÕÄQ€Îÿ¸ì7ðòåÞYà××n`3ß|\'+€ÉB=ÍJåˆn`ú¤Ã‚\ZfC²¢4Ç“tÃ©†fm\'<e{jê®ÁL2—À¥Å	ð€LÏÀLÛïÙÜ¸){º_âk¿¦i)ÎLgd1³YÝ73r\\ãõEßôÃ³¤øÁÈ§gÕ_~ž°âk(ÖMPlè9Å–t\rN,wv§;Ýiá¸×cÏwóÃ*H@èüµù],€|ÐÌMû0~+(6\0îw´9Çï\0%\"Stùo‘¤g\0Œ©ãÏÝ;Q­,\0Ø2u.¦ç\Z4ŠbPJ¹™âLËcFí£Ó¶¯~õ«ÀÖÀ$ør÷uÿ ÀÖÿÜ“+èÑj}/°ZÀñ\"D°ìÃëáä­Ñp¾{ä>Ç¼ÌÍbõd•\Z­†›G–·0õIGãµ|:iÔ‰PŽ¡¢qE™oÙïxéaXÀÎ1ƒöÀlû1‰0“À°<ŽIm^°&È£‰Ü	ù\\š¾&‹†ÆÍxv–EàÊgã›cÒ—Ìèaº‘çlûA°Q&„ >ðúÊš9Î¨‹“V+{ð\n\\{µõ2óÎ½Å4šÕ\rÒ+ Ç|#€©Žñc\0w¼\naDÞ‚ŒIÙŒÎ{Ÿ	ìFø¤¹&`¦ï¶…Ñ1ãÎ5é†9/#S(>;1„ôsÍüö[ëLÏ”VŠ¾~ç<–•\"Ù§„’ðë`GñÛv”`–‹ÌX€·gpq@\'ËF^ùóJ©øjáòD~4š3˜æÊÃ”åÿ¾ÒnšÅ·`F@Bf_L¡HW§\"ñ‰jy”åFk\"PîA hŒ´ :/6Ïä¾¢æ¹‚í},ß§„˜°¶‚3®/,\r!Ì`MžUT¯×…ll€9åœ‰ïö#çã\rxÊ\'Ø¬®	xÍ¶X\rèÝA1³.6yPRÆ(ŒøÂè<Tòc ÇTcŒ\n¿„1€â™0Çlf<]FÚ&K‹â\n<;ÁqÏCîçZ‚Ó94RFÃ’;@…ŽÄ\Zq\'úkáÍ\0/‘8°¥i±bŽi„»}†¤ ³Q^£‚x“—âÁê‘€×ä¼•À¢¡†€=\rà)é\Z?î| ù}ÂxÅeákb{:ž ÌñÀf=¢%(Ì¹7Bã²?¢crOÛôoŽÍsÆoÇ•tc„.`§/”+ºô\rØÂ>¥àB¸E÷-àOqh¸ëÔìãÑ\Z^@-­ŠÐ1	Ó€6!é `J°ÇÇÌ=žt3[ü­Ä˜V(bþd\r4Èõ“†9Ï,\n¼0ÕusßŠ$x´ŸOkã¤sñÙñÛîÅE\0’Â¸g¯/1·öË¥™\"}L9àéÃ¤ä\ZŸ%r öá5Yq±ò¨€¹àn¢Ç5ÐØä=r¬<“6ð3¹__ Xn‚`ðÏñ[1]®!€ã¯(®À#í®#p1d:W3N; |¢…¬ŒhœRÀ‰u	\0~3ã\0Ð_®*OK)`QBÎ¤Ïp.’À¸YŸÅãþßóxù6- 5‘Þ	(€;Ï¬9*éL§ZsX¦¿eŠ1#ÿL sS‡1c1ˆO0†ä\ZI½Ÿ$Úä~ÎejZsÁÚLPÏìJc\\^Fã³	\Zð	½òrøç“öó©Î§ÒµRPxä»ý&Yº!™Ÿ”ðï=wÞ%?:/ \Zzü¥›MBéàò·‘´.š†<¦lRØš`€H´D¾îša³Ï\'aÉäaNîŸ9o¶¥ã\nE*Psh%z~Ìw‚A÷f6‘\Z}«±±t@ÓÙ°	¢¾:Æyú’”K¢øÄZÕkû‡&ÁÂ}Xç\Zš>ºÒVC›û	çi¨áÍ¼`vÊt”Æ\ZPa>ÝÈö6}`^”^v?­Ki–Àyð5\"PÛ˜E\Ze[,‚sÛ*Í`eæÚÄ2@Ìpn²À§¼+ýñ¬Í»ß£©ŽÕWkÀ\r|¤/-/bº	ƒ{EäàJ×™Ñt6<Ü›‹ÌßjxG×Òk¨oo€#y4À\'å·8@â/j¶-E¦—/ÁH§C˜ÂgË£i{ü1JJÇ¤‹”13Zlíw}Ç` WÍ¡™ÄŒM»fá£Œâ¥\0ä{ž£Í‰i>ZýŠ/ý¾ê£ý2‘hº>ã´Öù³ió8À	™g#4\0=ZVogØ¦fz®ð†%Õ®TÆÐÈ›‘~#m&÷ûÄ¬”™±´ðÇ	ÂìƒX…¸ÁM¬€Î1uIß’cÚï”v-40‘ ËhRþôL6áØžƒ…IÉ§³Lm­vI‹\\CŸ˜×ð\"•4“A\'ýHÜá\\ÕNÚŸâÔLÔÇÅ9‚µüyþŠ}ôxxÕ_7)ÍXâ=§\"õœ¬ó#h¤`ˆj™™ Flh²¥cùR‚0×¶h<¦ÈÌé·?E×k‡bO+ø,S²æÐ8¶toK•þðµ´Tê•\nZR=€zV}ö~[ˆß.¡möë—¾àA |¡†r)-ÒÍF}\\Ü‹…õ¬KÊbn2×ê]§§xMµàm†áÍ±‚€a´„IEL8©M§BÝ±D0± ð[=@‚fóû)*\0sü\ZÚœC3ÄœÔºL\"ÌÓ\'ý`~vöE)€NëÍ%  m_âôƒÒ—>ØÈošo¶°5y2¤~“P+€À6Ìí^\'fj5Gìh)-g21bR°Q:oà˜»O™_æÁ2Ã£í|Ì¸×{a\"Áq­è#Ož²1û\n>®™)½®Mû3$¸Ù§äÌL·ÕÀDÎ®!öÈ‹€	Mú’·DrSþî‚›pÛ³¹\'á0\ZlG\'%®‡{a%d(„tÆY«üø‚üÝ„Ô@gu¦Gcµ>~ÎKqL0Ì¿Ä×†tF)O¤´Ljâ{„q$•f\'ÇNÎÜñ¹sifëÐØQ€7BÃÜ#©`‚ &Ý6¥àŒÂÅ\"¸Ž4L°¨o\"là:>}56a»ßîÁrÛo–*\ZoxÚD–\"C¿S•ãåmÕÁ™ç¥×œæÝÊ_tó¢2Ï¬G@KÎa¢¥frrÔ9‹–£ŠÈuRg1è4\ZƒøC`c¾ý\"a\Zí;Ój*î´MŸJ1ÃMn¸loýb4,ýÎÐjŸúŸí™KH]\'`ëc´™ðê«þ°>oîØ/2Mj\Z¢l5–Ï_^VƒD3¯<©¥<›V¤~~5˜DÞ:Ú\'IIgÔÔëZÒÚìü£QÀ¶M‡}”t*vj¢i4aÈœf¦d5†\0Ð5	bABùÍ†h@i)ÏB\0‘mž=ƒ:€ÓwÇðÕúJp#¬Ž\råo¼þˆªËÂšYä\Z™\r3\rN.PÀÊž_1ÃìïT¯@ææ¸¢vL&iƒ¢åüˆ Èl ê†U‡lCm§™B~Ð}ã\n2AÑdmŽ%Òüåäå\"¾¿%³LL#Î{gÒ/\ZÏíXàO(<úi?Èï/˜™uÁ”`\'eäFÍId¡OLÒÊ7,ÂöÂz~<kµ{ø±pÁIæ¬‹PuZg( $åé,Íç×˜>Ú&8¢a-à»6o–“ž¦¬”¾èƒB\no…à!}¬yÀ¨™æ<\r™Á#&Ra4¥™‚L¶>¼^¤·CÑ*µd£^¤w\Z°ÛåCâ\0s½å¹ñÕ€6²E0“h¶@%÷X‚Ìl	ð†×Ç¦H T¿DúÀf²õ™Ö	êô#V\0pý7‡@£ ™H1%àpò,\\©­*~Möˆ\n6*_~:ÿ+èR³ ùžß‚ìËþ¬¸0\\j.;É¯öipÅè\Z°bÀÎþ¤_4]d¼>îÙ1[°)@•Š4šìØ@÷]ÇŠéŸþ²~4›‹P“ÈÈŽ¢ð{ÔBLnAìe*ùém4˜´Ð‹ø_ KŸ2ð?e²=-—\n	\"d&ÚóçÒ`ÇÇcR@ODKSÖWÀó¢]š<;–LÓ×€O|¶³¢©L&­â4™T•’Šö\r¦i%%;Õ¨Ù*Þ=P¬OAyiÓÍÌÈ/¥k€L”ÌÌßÂ0	1u,\0­ÁÄõÕ‡g£D_ŒôP”B‘(œ;Îv| $@Rè’¹d&ìÔÁ1-ë¼\n~S^ýØ $îT«Aó¯ÀLtOƒûßûði1³.ÀaÎt8) :æØl–EÀÃ²ë«†Jš™U®\07àÐÍÌ‚›~³j”	à¬¾»¥éí1­ë7ä_œ-B8~ƒi[t@EØ. ü9³Ž˜;‘¶È‘©Iõ¬ÍoÐó&$‘+-  \0_5ÜÊ %kƒRÚj@Øòq`Ç—#ýµ?«{òŽ\0NK¬‚ ØºvålZ>õ»V3‹uó})\r(”$xk5Ÿ}êK\')4‚cú”€B B³×\0]:c€!`ÛÎìc¢ÉÿëY“Ûg}yŠ)\0¹–‡ªlÁvŒ\0–vãk0.õí¯ïïÏRjelÿm*ø[ZmNoSNÄ~X^ŠOë<Ø©BM¸ …ðè¬Ô,oXF¾ËÏ1J0—?q§A¶>štåÚÔËj\07²¦Ê¨ˆ¤L¸}²E)<0?_æ’†!V×ú2C¼Š@‚¿Ã—§U)ó6ÐÅ\"ÈÒx$Ú;\rØ!ÖA‡U‚”:ÕzSÂC€Kcò_cÑpû™ûLî3˜2ÿEhÉ,ó\Z\"L!ãïL-ò½ýmüšf†øe”ï!)ß\"¼™gŸwÜda`úH˜`ÂŸW›ÌlJÄê2áR:o‹¸¸*Š·\\ÞVŽ“Û™˜höe–ÙNJý\nœQ%ÂS×5(Œj™b[@§éù\'ßÅcfòÜvñ;ÊTc26úó2ß}9t(,YöÔ¸ ÇgekÀÎÿ§ØŽòüyšyô¬YR×9k«Àãå?ÀfÒ¬V¾sûò½+¤UfÂ`éšìN\Z7Â‡IW\0Æ„h¾@Ž–\Z\"-E\0òÊ–À9¡Ä„\0„Ç÷dmŽ,ÏäøV ò	H\Z-¸Ê²/fÚ«A÷r}Ïò¼¬ƒà—9Ã„‡s¡¬Ÿçjùm®eE•f·¬­VõÙ£²NÜcÆÉ\' ±Bà:@¯k3sy‘OË(\0`4ó8J(0™ÇhÀ»F+	Í0ÍJŽ¾àÄßª˜.1…}I!	ž‰…¬“ßýg¼þxŽÄ¡Œ\rØÏ¢yŽð\"ü™”í4á‘Š‰Ì{¬µÿ-‹/¿ys‘¡;Z©“™\r3õ>ë¾1èy}f¹vË0ÌÄ>ûÍ4¸Ãg;†–æÀ(ßb²ßòåÔç#0®AØ”ó\\ƒà\0;ûí\Z~‹#hg@Í}\0í\ZÎË«D‘cDä,”\\<sí3‰d.„ïSe.óyQùñ›Öv+s¾P°EÓMêMyÊç8êÏ˜É4h ûgšNj1\n#}†¢%\0Ál\Z(\0s<s*ú§Ù|¤w»@çÅœ\\C.L`b¤k×`¦]ˆž!Ïá\Z®™éÊB×t~žÙw÷ã·¹Â–EÓÌSëÿF‚>£Š6§®ˆVoÜ¸‚­3[Š$ZÄ>ÅÃ÷µ>³21M|Àz&`ah:†òƒî+weÊKÀX¦—Vìã;]3ãÀ\0v{\ràº€\"0öõ…Ïu<·#`‡\\Ã=h4a 8í,à¹ß›¥¸Ø3jdsãÁÕ*Þ§´ñÀ(„`Öˆ™•#§ÎöÁ·¾IaF5Ž@ÉQi3æF[û±ßBŽ€ÌúåçüìÏœ4üTüF\\CÀL“éÛö–­eÜÃ=[0¡cþ]w6 Ã¯	ŸX”lÊ½^R)ó=Wt«çn¨jF›)½³QL»´	 \0\"\01ñ´J‡\\›‰§¥Lt4ÅTò­­0ä»Ñ(çÐ[XÖ¤Ñl\ZÈbd¿k³¶s-Î˜keˆÉ8·:û<Ð¹…ðæòPRBÏ¥\0%î‘Ú1X­¦ÏlZÚ}¦@0§	×¡ypy/°™dDC2y0ÿDhºx\0(°Ô0-³í˜ Œgni©ó2Ï,÷_ÌìÆe\0[åÊ˜u®a›ÏÜÇwÔ~ÏùÉÇ]»}…˜Ï¬LIÎŸ}%áÊ¼¬?\nƒ3á\0uúîe~—\'ÂæÓ€5Û:¨¬\nØ4,‹£	yå‡È–‰—ÿÞKJ€ÃC€FùM(\0G›}W¤H¾Þ\0Å\"ðÙ\"p®‚f÷¯m[îÑRRÁŒógR#@Ûµw¶µéZ„ì›(ƒû›ë†æ,¯·Xì>X×­Þq ŠÐÅæjcÀÎ«,RerMTŒvc˜ß¾#>PJ°ùg%C¬&9\0˜3:Ž€Â5˜B…Ñ}áðÜ_g\rvŽ#4>ûä¼Dê™‚LPtH6@°ZJè¼?Š(Ëä-J±¢sÀ®Ð‘RÝâA eÎwCyñ°™ç\0ŽŽÇ4 Ò¶¼9*Òí;ß.ˆ•ª.Y’,h\"ëôh=0TÀ¢!|n_{L€ö{Ôq®A r÷Ìdæ;ýlûLpææ¥¾\"ä£ÞòiÏÜr,7g^Á1W\ZØIÕ\nô“’G›Æ”eDM-\ZƒvÈoQ.&«>åM‚áü–qys2Ë¤ÑvÿZìmÆòQcå´@\n”w>ûÚ:Žìo¬gáÃEÜˆÙ÷;Ñ;@ÒÇ>`¶ÑÎÔZ¿Ýj=ÓÞ5Š>+asA*|*…ÜÍIõ]\nv¥6µö-Ë§Ÿ#ºÙ-6RKú™L3X€œáB`Ù¶|ŠŠrË¸|&ÃtR¯ò§Úd¦G-„Tj¤ý‚\Z²ÞK°Ã\n8G~ÈÐðwþ_•»aI•sš‘7ÖHdžHí\0K°ü¦Õ	è²-kíZÀû¯EÁ3÷æŠ,2Gàœªæm9X_ZEŽÛ•_Â§y÷XVR\nÖ08Á&4ÚNËÇš·¾æ$¯çBhµÉƒ‚;+7¬‹óþQ“C^l2ë`Ý•ç£9*¯ÏvÅXePÂBH=Mž~´ÚìYd»Ï>	âbÒvÝsðœ°“Å(êx&nkIÑvƒõ­Uä¸K¾4 LÄš·94/°¡U4Ñ\"æ1œ•ZðS½Ê,X>ÏR^þDÖølo\0¿žW¤H#ºR+Å,@8æ^-0´”i\'p\"Ã«-9€#€´åÕãx“eÕî-Õ%tú²´h—ÁúÚj’â@—NCuª÷®šÞNfø$O„€·Ñ‚àwÀ6šÄgð…if®iPR±P€Kèœ7Ê/ÛÞÞÇy0º\0×mÕ7àöIÆÐ¾÷%sÔUÍ.R>^ZŸ{Öó&Ûµ¹˜wÅP:bÊ1;û&!@\0M\Z8 Æ‚´ÔNdè¯E¾· ¶~4`!\ZÉÏ»gü1`ÒDƒ8\"ü˜cÔŸ`‘<<‹óR£ÜKÇåÉTX /)eØupi\n3Ûègäfäé4\nƒóò\Z@Þ¶†A³QÞN¤j„)Ìž‰h&OúÀ9¦òÜ´°4oÏoß.\n\\fÕôÉöbßJÁ¢Xk¬]âöƒ«Z+0¶* Oº´\rð°t#˜ƒfz\'»â‹\0LFÏvžTËè—\0ÏúiŸ~Ûžçõ,´Ñp¾6ÛÐéÛ4Äbp+´;séN¬ën5¸Š¶äéÇðé@W{—«3{#úLÀÅdš0ˆi´o,åŸ\Z_Ï\"ecq|&r·?Ö£OYäÏÔº7&tb¥õYp ˜²tØ=®„\'cìÇ”—g_õ[1ö *Ž,§åETÉ00Ç0&LdöG“Tœh¨#Ó¡GyË°\r<(QšŒé|¿DÇÉ=˜mõ}Ç\n¨âÃGÚÿ³áÈ[\'	Ž·//:hð?Öøõ=èÅÈe<—¶ˆÂliLKyÑ=hó	lÚêw¢Û™ÈuaL°ë‰â³€ ÈÉ³~Ý9>ûdþYÐ!ÀgÆ¬Ï™H&‰\Z©è-.à÷ü¯¶k³ŠÞ/¿~)óž9rr`þ,m”Û·öªÜó›#•/æ6‚2Š²jSõàêý˜íºrh×¸ˆßufÌ14`:™Fî3Ž²à™siiùáµo6¸\Z4&~ßýtsäCEøxA]1ŒHSH\0m¬ƒ*\0úS‡C£\0ÏK†˜Óp‚Öž×‘Gî!ªÎêÒÔöÏêÑ\\/ï…9½ú¸ïàjÖ2GîÐ2ñËšÿE¼éG&„Y\"Zû€Mã˜\r>9OÕ/€3ËŽ\'(-àm¦ë |§­Ì|ürŽ³½Í¹lÀ—• Zš-0»ú¶}ëýÈ\næ.Î_\\f#­ÁfuÞmÆ,+Ü(£€1„áb\0‘¹rj4R„Ì‡Ûn4*\ZØ§€Û§lÏg&2f>5€~q=Ã‘öÖƒùöÿ–4íP~ýØòëËóäµ¸ø@.3œ÷¢ Ú5ÈÞ\0„‰€DÖ¤Ó|ÑºŠhgêÝÎk?[Êq™P‘Q7×ŠÀEâËë^ÇÖçƒù6#ðÛZµZÚ~AmÞµù×Æ¾M~0ö­`\"oñ&²NÜ\'Kt[?\Z[¿Û?oØ´˜Ö\nø¸Ï€2V.§¶ï‚Š;+Ð·Ì·©¦DoQ~}AÍr9%¯ñvGï3îí5ß,³,Ê70ˆfÚQë#cÉþß™_à)²Øp¬Hõ=Õ1 ¯*ÀO©ã”Fo1˜o—¯°;è‹ÊÄŸVÚ¾Ò¸w½&Ô˜÷p1]-‚\',€4O@\'÷õg¬=“ ˜^&e:±íö«›Ó(ž‚Jjå>UÔl“1Ø/B_Yƒ7§U)tQ½ó`¾]!Å›\rËÄïX RÚ~B­©:¯Þ\'·\Zà&=\\pÁf¿X3Í\rd\\I×ÊU5‘¹ÔN-]ÕN`È·‹Ô`Ôç3!BFÀ¿€¯.ÀÏ«BË	%D‡Ø;– l8˜oë®Õûg6+`÷(xõwMs:¹`‰v\08Ê÷TÎoòƒ€¢%ŸÐx€ËïW˜iR³_N.°®ÒìcÓ¥Ý›\ræÛúÕü³ƒ¿ó(¿W½…jÿÖT€_Ñÿb9iº`\\]š¾²4}YiúâJÛŽ/°Ö°ëþ¥é{ÐÛð›æÛ|›oóí*Õþ‰;÷\'¥5\0\0\0\0IEND®B`‚',0,'superadmin','2013-06-05 15:15:03','admin','2015-05-06 15:27:35','','For Test','sadf',0,NULL,NULL,NULL,'0',NULL,7,0,0,0,'',NULL,NULL),('c1cabfaeb9a448028ffab2148da9f65c','QQ Login','QQ.exe','COMMUNICATION','7a1e2f19c8f21ca9405690d2fedf8c4d0d9f57e9a797732a074689ab39238a2d1e698f87c64032dc548d6ec7dc3c4863','Desktop','‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0B\0\0\0B\0\0\0ãT\0è\0\0\0	pHYs\0\0Ä\0\0Ä•+\0\0\0tIMEÞ\0,!C\0\0\0tEXtAuthor\0©®ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0Àæ‡\0\0\0tEXtSource\0õÿƒë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨îÒ\'\0\0+IDATxœí›yUÕÇ?çÞûöÞW\ZºÙ›E`PPÅHR#1q’(5Ñà’Qãdc‹¨Q‰Ñ8Æ]£ÅHDÜP¥¥¡Þ»_w¿íî÷žùã½f1N¤éÖÌTõ·êT¿WõúÞïïóÎùß9ç>áº®dH(ÿhÿW4\"§!9\rÈiDNC r\Z‘Óˆœ†@ä4\"\'í‹¼Ygg\'¯¿¾ž\ron¤¡añx7Ží ¥ïûÄbQ†WgæÌøÚ¼yL›6íó&¾ˆµFkk+÷ÿá^}u=é¤c;8¶ï{ø¾R\"Iîµ øœtò,.¾è\"N™=ûó3—»÷çâ‰\'Ÿbõê„±M—d¢SOãy6 B\0’¬¬€€‡Ãùÿô-~rÍµ\r®9ÙwWñùð}Ÿ_Þò+^~õm\n\n‡ÑÛÝžIà9&Žmâ:&¾ÏÐÁ÷}\"‹cÂ¤ñÜuçŒ7nJ¤ñ9&ËŸ_#ë7ì ¨dÉD\ZÏsQ%÷Mÿ}ƒ‡›•(R¡¾n.]JCCÃàì»MÎÎçbåÊß°aãvBá’‰¤ô‰/Á0Lt=ƒ”Û¥”¤ÓR©T6wä\\J$A{s?¸ôR:;;Ýó ƒxçwyðÁ?’ìã¹6¾gã:ét/S§ŽcæÌ/á86žçø?Ïóp]‡gEß<E¸®û‰«öïiâg?¿þSADƒ\nÂqn»í‚ÁÒ©ŽcàÚ®c‘Lvsù²yúé‡xêéG¹så­ø¾‡”>RJlËäæ[nä¾ûÇ=«Wq÷ªHéáû‡àµW^ãégž˜Ù¾š»ü ‚xé¥Wh¨ß‡iš8¶Žk8ŽI2ÑËôi¸òÊËP€Eß<—ÅK¾M*™Æ0fÏ9‘.øîkÍŸ&g/<‹L&ó7îU¡q÷Ý«Èdô¸žd?þž+±­®mâXŽm’I\'˜7ï4\0lÛâº_ÅÕW,cîésˆD#Ø¶Íü³Î¤nÛV–.YÌÛ7ðÕ¯ž‘Ë/}Öªµ¥Ö¾80Ãâ ŒA±wï>>Ø´ÇÎN¶màäšçšVÀö­[yïÝw˜<e*õí ¤´EQ(--aÕ]+8~æ	Üÿ»{ð<áÕÕƒ\ZÈÃ!Hé£)\ZÏ?ÿÂÀbð@lþ`–™­=×Æµ-ÛÂµ-<×Á4M\0\n\n±î]M,Ë–RRÒ3ë¤ÙüèÊ3lØ0\\×C×M¤ïbÆÍ~Þ÷<vîl ½½}Pü\Zˆí;ê8˜È%àÒàÃ·0z\\-—_}-‹¿{cÆO «#;îü¨ï,ý>ø×Ÿ^O(äýM›pÃû!ð<ÓrhØµkPü\Zˆ¦ýM‡¼;Üx$åùg_ q+BÀù‹—péWòøcOâ:.±XŒçž[Cã¾&P£=žà©\'ž$sx‘••”UÕØßÔ2(þ\rDú@—|Òx  ÒÑÞÉU?ºœÍ[¶Q¿§…ëq=ü<¾ÈÃ°5\Z÷õ°dÉ¥¬ye#ë6¼Çe—üÝ7…¹Ò¡%¹@*\Z®iŠÿAYk$ZZøî¢óÙO¡RöIJ‰åª$R:Å\Zc‡k„qÆŠRT*\\Ÿ®Ž]=\nMqÆNKäƒô‰}@H‰\Z¡•²ddÿöÈ£(ª: ¼!¥äõe—QòÑ»¤FSã2åel…€\'ŒNsæt™SÆÖ†(Y…aP6¤tHˆ§°;-šº4ÞmJ±vGŒõ\r1R– ’YÄŠ€`³£§þm¶=öÓg@q¸G˜É$N?†cÊ¢\\º§—î¼JbªÈõ…Ùc2,›ÛÉœ	:Ô”ÀÈqP\\	0h*H	¦é$ôöBW7tt‚aB\0ða{sˆ»Ö•°fGE¢ÃôX‹Ü–Œ(£)¯„ó_úB9ú‘>à±kíZ\\Û¦²¸˜›J\\Hv`øÝVùÖq½<vi#sjsÕ¡È¢à+àyà8`[àØàºàzÙæIppaJ¥Å=ÿÒÊ3è^vÃàé½Ì÷SæE¡¹‘t¿bÙi¸¯\rxh´¿µ‘šŠººãÌBgÅðb®i“ÒŠù¸+LCkˆÚ\n‹—·Ç8‰òd\nÂ‰@ ×#lt’Hê`YÛ¼\\—Çé3ìi²q_”3Í%y:—äy4¦<ò|“H,Ï¶.€ì¾ÐÀAä——Q^ZÀþúzò=—3*Â<:ªŠë¶ìaM]”ºßŒâÂ“’X6Ü±.ÂO¿gÎô°U@Ë–®Ž–\rÂÞÙáö—ËYâòöþ(«þ\ZDd2Ü^˜â¢Éµ¼½Óc˜ÖƒÞÓKtâ\n««Ç€AôT§ÂÈPêfèv 0abª‡«Ur5þÐnqý³aJb\Z*–¬\ZÁ©“œ`pÜ›Š|MJ,Ó§5®±¥1ÆKuy¼¾+BÂ”¸ÒFXçUÀÕ¥&µ–Åþ^ââ4£‡D<ÎºÖ^Æ~¼‡qãÇö?€\\†P²Üº­Ž%K—qNóv+]ØØø©Ú\ZÐRJ«+é=…çö¶òbÂgGÚ¦ËôIÛ* (\n{ç¦iCwZ’´4áQò¨\rIæEÅ<&\Zƒµg\'miÒ…å”TUÐ¾u;u‚Ü]4‰IÇNá‘Gî%tXÝñ÷	HÉÀ‡F2™âßò|ËäI­”òT‚…ÕQ‚šO&e $]I~GK{YZPDÛ¤±ì´|\Zâ½4;Ð!£–\r¶…\ZT(«1ÂKS+,&—1.?ŒÒXºƒ™)\'¥Åpý4E^\n”Ô)Q~¬ÀV5>nØÅ½«ïçG—ÿð¨â9jwþú7tö$Àq1´\0¿ŽÄµÎjk\'‚\0”H^v%jXŒvªbùŒ²Ò|-‡µÇBg$z!„šZØ½2:dlÛÁöÁÑMÔHŒ‚tu›| »¸#XC‡T	™:J¤ˆÿø8ÎgÔ¨‘GÈ@6f¶lÞÊÚ×ÞÈ®*\rƒ\0’Œ\ZàÑÂQlœp\nuºŠíC \ZE˜:B€ÔÀ	1mÛrð3¤ÒèÉýí…L&›4=™RU\rT$œZ0Àî8l|\"O?‘FË#Œ2ÉJ À}÷>Ð¿`²y{ïï$ìîAÉÖ¾8†Î©sOãŒU«ÉÜ¸’u”³³Ë ™Ê€\0UUPU\\\'»¬V(JÖ‚’3$(Zöµëd?£i86ìÝÛÅÆL€†¥WQ½òAæ_°” Ì	‰c[Ø–É_Öo ½½ã³ƒèK’¹·ý\Zõ;wñþæ­˜†ïùE ²gEL?n\Z£+JÈ?gÏ/ÿï×7T*UÁÈ<…® lš¨>(HP•\0 ¯,WÒÓtÈ´w³¿Ëg_l$öYg1lÑùœ4m:a-„çz‘J&PU\r!éDUSyí•uüó’ó„ÃõÄÚ_Åq]ôt&WÒf+³P8Ä°ªªìE[[™¬I¤=Ÿíi—Ý½.U›wD(–*	“|jo\náäŠMµ‹TÒ#é’®¬ÆtáïÂè“O¡¦v<Ñ`Œn‘6lòòò)*)¦·\'ž]p	°-Ç²xã7?Ä ò(Alzo3Žiá{B¤Ì^,‰ÅðDýNÂJ€Ú±#ÐÂ!bmITaÇ aæ×0æ³yÍí­`èÙQ‰¢¨¨$6¢†á5Õ——S˜#”ë¿ºåâ¸®ë£¨\ZáHôÀÙißwl™\r\rfâMýÑÜÜ‚sh9›»ž”Çqpts3Z[\'„óT˜òK\n)(¬®¤pÁ|¢ÏÃ—}éA ª‚€ŸjÊó%®ëáy>žŸm¾ŒCö@lË¤§;Þß°ú\"Jã:ÎaEÁ2Mº»{q\0_7ÈY‰^\0ªG©®#MZ{ð“=¨B\n„´#ßCp=Çóq=O‚eš¤RÙœÐ—_$àØÎ§}¶ŽjÖ8ô”©¢²¸ŽËžwã¶¡cïnÆß·¿³•0‘âb¨¬ÂDÛ¢?ù¾Äv<l7Û\n]ôtÇ	…Cœ6÷´ƒ½BJþ—GýQXXpàõÄ‰µ¬¸ëWD\"!Uaó¦0mPŽŸ…6¡†`õ”Â°Mhß»v!¦ùm%`9–ãa;Žë£i>Ú¶•L:ÅÈQ#¹ý×Ë™:å˜ì’\ZÉðUý\r«ÿ ú6ùÜ¼üf?9§Î&\n±yÓ&>ÞQOÑ¼3i9÷;´¥L,ÃAÆŠð+jH¯ANÁ‘®\0ÇÃt\\,ÇÅr²G€®çòÆºWÐ4s. ¬¼Œ[~u3ùù1@rÒÉ\'ö7,Ôn¸áÆþüC8¢·7ÁÍ·\\ÏôéÇ0zÌ(žùÓstÇ»‰æåqêÜÓÌ˜EüË³‰W¥§f<=SO$½èûTž>è,Œ¤”8®a¹¶‹a¹X¶K8eËû›øãïïaDõp–ßúK\"‘•Ã*™qü—I&“\\|ÉE”””ôÄ =(rÛí«XyçQ˜aåý÷1iòU †‚hš ¬B˜#ë‚žïã8†íbÚ†í ›.Šª •ë–ý\rÞäÞÕw³páYƒað¶ó¯½æ2Î:m&-m	VÞzÉ´Ž©[ØÉ~ZÇÕ-,ÛÅq}|)«ì$àK‰ëù˜¶KÆtH›º•k¦ƒP ??ŸÇÿøß¬[·ž«.<o@Ý¦“Röh|š<Û mÍr\\áó4´¸¼üæ>,=ÉÌ9§âùÇñ²u€\'ñ|ÏËÝ×l×Çv<,\';¬=!;$šJaQ!Ï=ógþãú›X4%Ã5³öÒ»¿‰Põ—ÑBÑ~{¹3Ï¾ºjÀCÃµtêWŸÚ	OžÛ+Xv[9Ï¾k²ä‚ó¸âº«‰FÂø®C@UÐrM(^%Ù)Òó³`×Çr=¤”äEÃC}äqnúÙ\r,žÖÎ­ßÎ?œT€ÞšÅØ‹Ÿ \\T~”d7hbßÆµ¸ÏœKí¹“`ìéò0ë7±|Å~V®Q˜:s6Wÿô*&OŠç8HßAEÊ\\)%¾®Ÿ$ÉÏË£¥­ƒ+~ËëOÝËµs{ùÁ‚|S+!¿ö¶Óøjôñ¿fò9ÿcAlúíO˜|‚³¿Šæûàl‚x;^Ž²üÉvç+gŸË¹ç-dÜøÑ4\rÏs³…U®8BASUA\r!Tš[;yá…Wxñ±û˜à¿Çu_w˜ö%¿¶\n¥êT \nÛH¬ÝÈÝ˜sÃC1àÍ[+ÞLA­	]¿¼6ðMPUfÅäÏc-^{+ÅÃïÝÁò¿>AÙ„YL™1‹	“j1¬œüXEQ0m‹öÎvïÞKýæwè¬{qlçÎ\'OP- ,€âw@ë«œ¢ˆÂ²<Ìû\ZÆÀAtUŸBÇæg©ì‡2‚PÁ\\Q&8ã‡3&š´ÄSlÙ]GÃú‡Ùød1ÛÍ\nÔÒ2TUÃw15ØÊqq–”˜L™\r•EQ¦A±\n	ºÌô€ûôHïÐUsê?„”’	\'žÎÞ]ÀÜ5/ñ¥É¡\n\"T>áj¸iÁp_R9LPeú´fbL9f*‘\\ác¦ªˆî6™îbFE\0MHì„ŠTH\nHÐ$H–ŠÓ¥P·^òç0ïŠo#¥ü›¥u4àÑÖÖÆöíÛyïõXué4Rì¥4â\0tSÒ•”ì7Ø£Â®9–Êqã‰D\"Ùí;²OêZ–EÛž=ˆÆã4R£õP–ç‹ª¨ªÀt%=¦J³]Ä^µ†À¤S8aîsÌ1TVVeƒ”,¥”tvvÒÔÔDKK­­­t·w`gRX†Žô}´P„XQ1•Õ#¨®®¦¤¤„H$B,##„À²,2™†aÐÛÛKSSíMÍ$ã]Ø¦Ž¢(ÃBù——3bDöZUUU”——¨7À –ØRJ2™™L]×ì	!ƒD\"¢Ñ(ápø@/øßäû>¦ibº®cÛvîIÜÜNX$B^^ÑhtÀ\0úô…üLáÿƒ†~Á“Óˆœ†@ä4\"§!9\rÈé\0¿ùANØ†\rM\0\0\0\0IEND®B`‚',1,'superadmin','2013-05-25 09:37:51','admin','2016-09-27 23:03:17','QQç™»å½•','è…¾è®¯',' http://im.qq.com/pcqq/',3,'','','uid','0',NULL,11,0,1,1,'org.maxkey.authz.desktop.endpoint.adapter.DesktopQQAdapter',NULL,NULL),('c3d44bb1-e2c4-45dd-91ce-43e821f1321c','Liferay Portal','http://liferay.demo.connsec.com:8080/','OA','d9457a9a9017d2f92ce3d0b58e4328ea637dcc5a434d3bc900bd5f07cd1eda86','OAuth_v2.0','‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\03\0\0\03\0\0\0:¡0*\0\0\0	pHYs\0\0\0\0\0šœ\0\0\nOiCCPPhotoshop ICC profile\0\0xÚSgTSé=÷ÞôBKˆ€”KoR RB‹€‘&*!	Jˆ!¡ÙQÁEEÈ ˆŽŽ€ŒQ,Š\nØä!¢Žƒ£ˆŠÊûá{£kÖ¼÷æÍþµ×>ç¬ó³ÏÀ–H3Q5€©BàƒÇÄÆáä.@\n$p\0³d!sý#\0ø~<<+\"À¾\0xÓ\0ÀM›À0‡ÿêB™\\€„Àt‘8K€\0@zŽB¦\0@F€˜&S\0 \0`Ëcbã\0P-\0`\'æÓ\0€ø™{\0[”! ‘\0 eˆD\0h;\0¬ÏVŠE\0X0\0fKÄ9\0Ø-\00IWfH\0°·\0ÀÎ²\0\00Qˆ…)\0{\0`È##x\0„™\0FòW<ñ+®ç*\0\0x™²<¹$9E[-qWW.(ÎI+6aaš@.Ây™24àóÌ\0\0 ‘àƒóýxÎ®ÎÎ6Ž¶_-ê¿ÿ\"bbãþåÏ«p@\0\0át~Ñþ,/³\Z€;€mþ¢%îh^ u÷‹f²@µ\0 éÚWópø~<<E¡¹ÙÙåääØJÄB[aÊW}þgÂ_ÀWýlù~<ü÷õà¾â$2]GøàÂÌôL¥Ï’	„bÜæGü·ÿüÓ\"ÄIb¹X*ãQqŽDšŒó2¥\"‰B’)Å%Òÿdâß,û>ß5\0°j>{‘-¨]cöK\'XtÀâ÷\0\0ò»oÁÔ(€hƒáÏwÿï?ýG %\0€fI’q\0\0^D$.TÊ³?Ç\0\0D *°AôÁ,ÀÁÜÁü`6„B$ÄÂBB\nd€r`)¬‚B(†Í°*`/Ô@4ÀQh†“p.ÂU¸=púažÁ(¼	AÈa!ÚˆbŠX#Ž™…ø!ÁH‹$ ÉˆQ\"K‘5H1RŠT UHò=r9‡\\Fº‘;È\02‚ü†¼G1”²Q=ÔµC¹¨7\Z„F¢Ðdt1š ›Ðr´\Z=Œ6¡çÐ«hÚ>CÇ0Àè3Äl0.ÆÃB±8,	“cË±\"¬«Æ\Z°V¬»‰õcÏ±wEÀ	6wB aAHXLXNØH¨ $4Ú	7	„QÂ\'\"“¨K´&ºùÄb21‡XH,#Ö/{ˆCÄ7$‰C2\'¹I±¤TÒÒFÒnR#é,©›4H\Z#“ÉÚdk²9”, +È…ääÃä3ää!ò[\nb@q¤øSâ(RÊjJåå4åe˜2AU£šRÝ¨¡T5ZB­¡¶R¯Q‡¨4uš9ÍƒIK¥­¢•Ó\Zhh÷i¯ètºÝ•N—ÐWÒËéGè—èôw\r†ƒÇˆg(›gw¯˜L¦Ó‹ÇT071ë˜ç™™oUX*¶*|‘Ê\n•J•&•*/T©ª¦ªÞªUóUËT©^S}®FU3Sã©	Ô–«UªPëSSg©;¨‡ªg¨oT?¤~Yý‰YÃLÃOC¤Q ±_ã¼Æ c³x,!k\r«†u5Ä&±ÍÙ|v*»˜ý»‹=ª©¡9C3J3W³Ró”f?ã˜qøœtN	ç(§—ó~ŠÞï)â)¦4L¹1e\\kª–—–X«H«Q«Gë½6®í§¦½E»YûAÇJ\'\\\'GgÎçSÙSÝ§\n§M=:õ®.ªk¥¡»Dw¿n§î˜ž¾^€žLo§Þy½çú}/ýTýmú§õGX³$ÛÎ<Å5qo</ÇÛñQC]Ã@C¥a•a—á„‘¹Ñ<£ÕFFŒiÆ\\ã$ãmÆmÆ£&&!&KMêMîšRM¹¦)¦;L;LÇÍÌÍ¢ÍÖ™5›=1×2ç›ç›×›ß·`ZxZ,¶¨¶¸eI²äZ¦Yî¶¼n…Z9Y¥XUZ]³F­­%Ö»­»§§¹N“N«žÖgÃ°ñ¶É¶©·°åØÛ®¶m¶}agbg·Å®Ãî“½“}º}ý=\r‡Ù«Z~s´r:V:ÞšÎœî?}Åô–é/gXÏÏØ3ã¶Ë)ÄiS›ÓGgg¹sƒóˆ‹‰K‚Ë.—>.›ÆÝÈ½äJtõq]ázÒõ›³›Âí¨Û¯î6îiî‡ÜŸÌ4Ÿ)žY3sÐÃÈCàQåÑ?Ÿ•0kß¬~OCOgµç#/c/‘W­×°·¥wª÷aï>ö>rŸã>ã<7Þ2ÞY_Ì7À·È·ËOÃož_…ßC#ÿdÿzÿÑ\0§€%g‰A[ûøz|!¿Ž?:Ûeö²ÙíAŒ ¹AA‚­‚åÁ­!hÈì­!÷ç˜Î‘Îi…P~èÖÐaæa‹Ã~\'…‡…W†?ŽpˆX\ZÑ1—5wÑÜCsßDúD–DÞ›g1O9¯-J5*>ª.j<Ú7º4º?Æ.fYÌÕXXIlK9.*®6nl¾ßüíó‡ââã{˜/È]py¡ÎÂô…§©.,:–@LˆN8”ðA*¨Œ%òw%Ž\nyÂÂg\"/Ñ6ÑˆØC\\*NòH*Mz’ì‘¼5y$Å3¥,å¹„\'©¼L\rLÝ›:žšv m2=:½1ƒ’‘qBª!M“¶gêgæfvË¬e…²þÅn‹·/•Ék³¬Y-\n¶B¦èTZ(×*²geWf¿Í‰Ê9–«ž+ÍíÌ³ÊÛ7œïŸÿíÂá’¶¥†KW-Xæ½¬j9²<qyÛ\nã+†V¬<¸Š¶*mÕO«íW—®~½&zMk^ÁÊ‚ÁµkëU\nå…}ëÜ×í]OX/Yßµaú†>‰Š®Û—Ø(Üxå‡oÊ¿™Ü”´©«Ä¹dÏfÒféæÞ-ž[–ª—æ—n\rÙÚ´\rßV´íõöEÛ/—Í(Û»ƒ¶C¹£¿<¸¼e§ÉÎÍ;?T¤TôTúT6îÒÝµa×ønÑî{¼ö4ìÕÛ[¼÷ý>É¾ÛUUMÕfÕeûIû³÷?®‰ªéø–ûm]­NmqíÇÒý#¶×¹ÔÕÒ=TRÖ+ëGÇ¾þïw-\r6\rUœÆâ#pDyäé÷	ß÷\r:ÚvŒ{¬áÓvg/jBšòšF›Sšû[b[ºOÌ>ÑÖêÞzüGÛœ4<YyJóTÉiÚé‚Ó“gòÏŒ•}~.ùÜ`Û¢¶{çcÎßjoïºtáÒEÿ‹ç;¼;Î\\ò¸tò²ÛåW¸Wš¯:_mêtê<þ“ÓOÇ»œ»š®¹\\k¹îz½µ{f÷éž7ÎÝô½yñÿÖÕž9=Ý½ózo÷Å÷õßÝ~r\'ýÎË»Ùw\'î­¼O¼_ô@íAÙCÝ‡Õ?[þÜØïÜjÀw óÑÜG÷…ƒÏþ‘õC™Ë†\r†ëž8>99â?rýéü§CÏdÏ&žþ¢þË®/~øÕë×ÎÑ˜Ñ¡—ò—“¿m|¥ýêÀë¯ÛÆÂÆ¾Éx31^ôVûíÁwÜwï£ßOä| (ÿhù±õSÐ§û“““ÿ˜óüc3-Û\0\0\0 cHRM\0\0z%\0\0€ƒ\0\0ùÿ\0\0€é\0\0u0\0\0ê`\0\0:˜\0\0o’_ÅF\0\0ÿIDATxÚìÚ½JÃPÆñç$i¡ÑÒY­[ïApèî^È\\ìÔÁµàÜ]Å¡à=ÔÉjç~M!Í‡C\ZÁ&mÎyÓ{¦BÃo’?„Ã|ßGV–þè\\]×%vÚ—&„F³!›¢×»}\0P0øšŒa4Çµ£Ù0†Ñl-AP¡µÃì0”¯æ¿V¾vºò{ØÇÁÉùÊkF]´ï^W^Ó9;ÜMf­Éˆ^Œû9E~c@1§ ¬krcŠy•‚†ŠÌe	)r¨ê\ZJº*\'&€¨(4T÷4”\n*T…É‡	!=¸µJº\n•1nûGbìa?r“Ñc7ò\ZIŒ¡âýÓ\0Àõ}Ì,ËÁÄr`Ú.<?zŸÔ;ó{¹žÙÜÅøÃÁtþ\rIý™Y7ˆ®Ldl9˜Î0m/6D(f“ N—·ÕdîÀ´=¬ûyBÙ ˆcËÁÔr`.Ö‡Á$	âÄrð¾!„;&iMÛÛŽhŠ\"e“î‰¡bœ@oM4“Qè3CDa˜4‚(“V¹cÒ\"WLÚAä†Ù† rÁP1N …w†Wã|\n&‰¦è ’M†\"ˆdŠ ’a(‚H†¡\"†\"ˆ±zˆ-.“áÄ8Ÿ‚ÿÍdv˜­Ç„çO2óx¾´düÏeá¼ËÒIÀÏ\0MydŒv÷\0\0\0\0IEND®B`‚',0,'admin','2014-12-14 03:46:00','admin','2015-05-06 15:32:11','','Liferay ','http://www.Liferay.com',0,NULL,NULL,NULL,'0',NULL,9,0,0,0,'',NULL,NULL),('c8038bd4-12a4-4b45-9d43-61b3ecdc2eb4','æœ‰é“äº‘ç¬”è®°','http://note.youdao.com/signIn/','SAAS','995c91d60c0c29f7015a1bd0538010c159ec2b5e4130f848a2b9ae2bb2de98ec','Form_Based','‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0|\0\0\0\0\0\0-Š¦\0\0\0	pHYs\0\0Ä\0\0Ä•+\0\0\0tIMEß“´Íö\0\0\0tEXtAuthor\0©®ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0Àæ‡\0\0\0tEXtSource\0õÿƒë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨îÒ\'\0\0IDATxœíù“åyÇ?o÷Ì®´ºVÒJHBBZÐ1º–C’\'•³ˆ`dÌá8•¤\\Iå?IR®ò/q€b§b\n‚m(‚m!#K$F`!KBB·´ÇÌìÕýæ‡wz¶g¦ûí»{$ô”VµÛß÷}ŸwúÛÏñ>ývRJ®ËFŒ¢\'p]ò•ë„Áä:á_0)=€	ÎTàóølŽ_Cp¾\n—Gad,w¦á—uè²‘”1Ô/®Î¸ósïwÕ¯…>iÃé8tŸ‡ãƒpr®Œj:å|’´‡Ó¾¸²¸x<°Ü	¿Tƒß^„×NÀÁsp±£“ík’‚ORÓáN²êXn„Ÿ«À›§àWÇáð…:Éš‰5¤NRÇwLr |pöœ€Ÿ…#—`Ü\n1±,0®é§­Räìa´XÈ>™>:	û?‡ŸQ®»At„É…:žÑ˜V}•b™þÙ<÷1üß1•eÇ™XQX\"¢ux‡Œ™*áã¼}\Zžz_¹oÛQš·‹Öay–`ž©~yžÿž;â²êŒÔÝw\'‘©Ã“Ì³.©~jþã ì>‘0)ËÑÂ¯§ýð¿x.¥ZSÿà]xçLtå¡Žg€]KË¬¨:~èüÛ~¯¯†d¦¢;iLbnKµäúþ8z%¦ò«i™SgÑîÛKbþÞYø÷pìrLÅä¾¯Ï”hÌ,2á]„ïï×÷•ÛIDÇÕ™c²\Z‰ðß_Qd¹Qù}™•sœN%†_…‚wÏFP#Ñ‰—Y×Eø˜?þPÝ	=øõ8ß˜ô.¥\"úÅOÔ¦…¨\n²Ä\nYfuRì¡/ðcƒðÌa¸2OA £ob÷í‡wZìÛ7n¯NÀ>€ß]Ž7x ~-¸ï«„hG´„¿y\nö© íÌ;‰¾˜:ñðvQ1_ÂOÃsÁÈDŠŠux\'ÅéN\Z3‰NÌ“ðI[m2<|.¥Ié°¬¬:&&¥Â…hžƒùÏE‹ÇÓ“ðsøé\'!÷‚a~x‡\r mè2ay/ÜvÌè‚Óƒ’Ãgáb…©« ‡¹d5náRÂ+ŸªmJ™M,ÈÂâèŠ;—:&™Ý°óvøÚm0¯`IÁ‡g%Oüæ¤D\"ñFfˆ„\r<?=¯ŸËöjžÍ¤/³º=)•5?¼v®…i®³b–æ÷À¿¾nó¦“ÄzY{Üyá)ž—&Âm	ûNÁ§9ÝKL´@öÌnxt\0¾q;tû¤±ËzßÛf ¥Å[\'H£™ôÎöÝM›>Þð¼uªeKqRå”}Ëö?{ºà‘p¿†lGVÌüã6Û¶xû¤À@9þóÔa)¯fMOž‚ƒ­7Gdxaú5qÜ¨ó”fv)Ënuã:Y1Oðøv“»—J•åù%IÎ›®OÈó\"[±º4·%ì;\rÃã))÷;‡Ì ùDœ§D‘ýØ\0<°6Ø²[¥žàŸw˜l^æCzœó–ÒyijêÑ¯Aø¸Õ²1®…ùYuÜ¤7Î<¥„ž’JÐþ*„÷“óßÛnr§ÛÒsðL~}ƒˆv¤Aø‰A85S¹Î«eeÑqò	=eøÖFØ¹.¼÷ÇÒ7Ý¤qï1<a¨~^î;è¼à\"üÈE\Zóiñê$šXÊ\'IJ˜^‚‡6À)íHÿ|ÓïZ*‘nÒ“z4?i^¶×õ:áRªíKGxƒ”ëNlåiŸˆ€“ä¸ñ‡6Àƒë••§)+æ	þi‡Ë½Ûš““’û5¦_‡3#!•_%îÛÁ\Z1{\0vm€é)“íHÿ|Áã;Lnl±ôóóùÃÆi>Ô~µU× QÝ·FN¹nÜØ‰œwÜø7ën<+²YÙ§,}ã°‘¶LÅkµ5‹{ñP\'|h.×¢\rÐDtšn8-·\'ëåÒŒ-»UV.üË½&;ú%19Ùüg¨~m½Å\0µ³¥2Þ‚„‰ÓY¸o]ŸcJÁyY¶[&lè›)øöf“·\0­¤Gø‘ÜwH.J ’µêDK#¿ù]EYA_Ã¯W	Zžd[PT7žÌ<ºÉ@`óÚQÛ6!o¸HŸãAýÂ`%P&lÿF…cL)Õrk×ÉƒDþdOLí!°%,tH6»?±‘7\\\"ÅiÅp]vj=7†ÇÓ§¯”0Í„¿^\'ÙµA0£KÓ?e™°ë–Ý2/[Bß,ÁÃwl¿6Ò½Ngê×Pî;.­.Ý¯o¢ä)³¯ãÆ\\/ùæÆœÉ¶ 2ÑN¶{nŽ¥KiñúÑ)KÏÃ¢[±¶w­JBZuT,Ì•#£—ºMx`dWÞdÛz²±%,œ-xd“Éæé¶tHeUâyÜ+¹Û$VµOB}Rª¤ìu’o\r`ÙnÜO¤„Ås³ÕD‹7ŽEKäBãý¦\\z.%Ã Q	Úƒë°ì\07î\'vôoo1X¼þ©MÛÎÈÆ°êüúì$.%JŸ }®~\r7^Ù3óvã,»Ul	‹æÛb²¥_¶\'r1ÂZŽü	ÏJy\\}®_»Ku7^„elð!Ž{ÿÎV“{n–º.êJ§ð¼—Y	”\r¸­ŠÙ3»5:S–°	ZXq,ýÑ-&›VHÚî§ëÙÔÕ0\ræqÜqã;×J½C0+g²«)’íˆcéo6Y¹ÀÆ¶];g<;,×Â!<aL¬<â•+¥z\"dçZÉ#‘=™Ðû‰-¡¿O°}¥ÀÀRwØ¼$%\Z?†§ <L‡ìûo—<|‡`ö4î”%k²‘V-Ì›n·ßKOÙƒ¶WÚ²XJép]®â¸ñuŠìkÉ²[eZYR2,lÛÀ0ìw§•ð¸6FÝ²ë	ZÞdOæL¶!àø‹ËÃ‚’:7^|Ç5*·.mã,²XÃ¯•<zgþ–]É‘l!`dTòÎ±1*c¶ÿs,qÃhK_ÿ½›y[µ3üéškß`Ò’<¿¿Ê¯Œc\Z]ÑªnAxa1<&%,ëU´Þéš~)KÞn\\˜°$Ïï«òßoW™´K%³ùyµÎwqµt_\\2p#,šÐ7E™ÌÙƒ\"û\'oWøÑ›Uj“&F©F	!ê„§OÕ%x;~J…“ðcJ–Î’¥¦$y“-„*Í¾x Êdw!Œ2Â(¡}\Z5.âÅp?<x#ëo_ÈïÜÉFÅìÿ=På™½UªMd—•;Oëµ\">Ç½	OÛª#\\eS2³;ûïÀ-Â[’Ù#ãF3Ù\"âmRžxsð¨cJ)™Ù\rÓK~Ñt¤7>iI^|ÇE¶©!;«œ	‡ð$eÔ1çáéåk‡l˜Zzý×UFÆê–m–1Œ2D!;£Ëw€;óNËh{qQ	ÚOßi\'[´’“aE‹á)Ú0©^°“öÓP\\‚öÂþ*Ïê,;çºGòZzå­¸D¹ô´-¼ÈíYw‚æ¶lÝZ²á‚ ÂKND7I™z/Ä[’—Þ«y’Ý(¬øIÆF—~ÛWªÓ0³ÊfÀø!¥Ë¶àgïÔxzo…a/7îGva1<o¢LBÉ€ÙÓÒ±î¢´—Vyzo…ÁQ—e‹2èÞé–ã\n)\\-=#åÍñ[ÒeÂœn˜‘ Y–ägïÖxúõ\nWF\rLÇ²…ÇÒË‘Œ‹X^’¬–„Gü@eC2»;™…A¶-á¥÷ÙƒÙFÝ²£VÐÒÆB×ÒsPÞŠ•\r˜“`ÏZ·8í©_»Üxœ\nZÚVy-=!&QÉZ\\—î=‘g‚fÓpãƒµ)²#UÐrô F-=ò&L*Âã$mVÎd;nü•ƒÕf²Íú-Î0´œ‰v$¸–ž…r¬Ë$ò6ä¼-[½4_òÊÁ\ZOî©p¥æJÐ²® éðÄëðƒ¤…Íè‚îE—¼-”e¿z¨ÆS{*\\®N‘ª‚V@¨l•Ž‰áBJæNS´(Ë~õP\'^«p±…lm-o‹ÖàácxÊ›0½=jv‘ ©˜­,ÛM¶¶‚VpmÃK’mbLé9¿öNÞÚTT‚öêûŠìKnË%|+hà¾½$ÿîÂÝÍ0¯Gh	·¤*ªäºôª\'hm–vQE‡\'4,7ýæIJlùC é.|c¸%ÕÃøy’-ñ°l¿\nZÞË¬ú¼š„¿y’Ò•ÛJ´#˜ÛãÝ=o²7þ«j<¹§:eÙyUÐR´èV	Žá)Çi¿%C2gZûnÕ\",Û–ðËj<±»Â…aÑ »­‚Vd¡ÊjR•Âã5#¹ï¶æR2£[ÐÕâoŠ²ì×>TdŸwÈn­ ]MDK0]vTõvw	jS’(÷l\Z0fÉh^’Eö/×xòµ\nç\\dI·%é°<¨g×Á®2áeµq°Öú\níÊÛš…è#€áQÉÐ¨Djd;žWÔÿk|A°)Á¶TfdTòÊ¡\Z/ì¯qqDxWÐ>Gž˜ö:i{]¹Q	Ô¶¢ÙÝõoÐªÜ…‡&Ú¯Ã/>²XµÐ`R†#[ˆ)2\r¦¼­m×ß=!›ÔÆ%ÕqÉ¤¥~jã’¡šÍÅ›³ƒ¿?oqqØâüdÜ21Jål+h	¬:Î—ñ.t=˜YuÃbn»bQ­ºUl	/žä–>ØºªŒa4·ww³mõôemÜfxT2Xµ¹0ls¹b3iI&,Éè¸d¸fs¹\"¹0lqvÈ¦6æ¸¡^–$Õnm)@†‰Y6¡þ`Ÿö«&;ÜªÝÇ—ôNýYUáê›RqžÄª§D „Á•šÁvñîñq¶¬,3kš 6nsiÄæÜÅg—,.Wll©6Ž×‰­Œ)—<2&‘rÊìª#„P¯Òõu¾¨%t½MIPOÈ„0#—ûÂŽ©KÈÂŒ¹¼oê÷¨Ô.™]qiÇéÃäBÕâçïOðËÇH¤-±¤rÑ“¶²n‡(õO½ VYv\n7S„ªõßÖê5.’ºkÉáÁ¾¤Ùw˜ã=]°lþÔß…Pÿ|•¸µ}÷‰Ç IÝ·&“2ƒQk¤Ýh&˜%éí\"©a¹m¤RÇÜäµZ­ø:¼`¢Y2Í™ú»AøêÊµW4_6›¶U7I=ã¢¦0L@Ö_<ë&—õ‰¶ÿiûÿãi†ÆLê¾[eù|ÂÍ‚åóàÔ`û mãe¶rÓj)¤ÎÀMš*­x„éð„úÒ²jGLkÃ,×>ÁF\r¦lÂ]Ë@H¼É–®?åq±Vqâ/F½níüˆ¦„k*GÜóóÂãŽYÇ}›$Ñ\'Õú{Ý²f¸A¸0°æÏð3èêÌã\"‹gDJªýê˜ï{tã|}ý`õâæcMw+Ï†\rK=¬:Ê¤|”\'îw>Yê‹ÙO÷Âä´ô¶ßÚþ$ná3ºa[ýùì;I©\'PYy&Î@¢SôLKæÂÝ7·G»¶û‘KUÆUA\'°cÜw˜8³gÚ±÷¶Cm„÷Í„¯®V{Ä+ï$¢	epñDŽÓhŽ‡œÏ¢^Øñ%uS¬U<ßuï*X³0ò¤\'>ª¾¤ýâŽ©ÁbÇé„žÉ°}\r|ùFïfž„ÏíûnW_íIy\\Ë,Cƒ%Ô—jœ&æÂõÂ}PòáÎ“pCÀ=·ÀÖ›#(Ï‚è }æ¾ýšdá™<ú–LøúpóBïæ ù\nŒÞépÿ¸Á¹—šg‚W_Üù$œgì8óÀ–Ã¯kÞÒÔ*Úw\\®[__‹º}è§ÜOR¼rC÷É\"Nkð\\ãt\0Ö7vm……s|ÚÔEK¸iÀ}ë”{Oeb„ÀüÈŽÚ\'¨o\nViÜ¤çEƒ•MøÆ&¸Ó+·Hà[lçõÀ#›`e™¸!mß¢â´/b™4Ï¯|	¾v‡¢æ–P¯-^³þn›r¾ó“$6n¿¸cj°¢–YAÇ7®€ïÜ½3<[·I(Â…€ÍýðØæ–Wrd™”t¦%º@ÏÔ¿\0þþ`…_eÔCB¿˜¼dÂŸºfèjíÅÔÈc†ÐçÛ¤÷Ý\"ËæÃwÿÖÝ¤Ñç!Á¯írIwv¨GvŸù\rŒŒyLÎOòÄt}BŒÙu\'Át¸Ïñ¥óàñ?m«ƒ¯n•H„ƒzBeç€*Î<»kþ\r–”,0í”ÒÎbŽ¹¢OYv²„”Ú}ª¾2aÁËÀ¾g†|\ZuÈI\n3f¬„,`ÌÈXÀ˜ëo‚øCX¿<Ù€pP®ý­cðÃ½ðÛ3. Sˆ¡³#ˆÀJì¸þö«Ð¯)›†‘D„;rì<ñ&ìùÆ&=\Z|ÑÝw‚}nÜ¿	þò.˜ç·,Ž ©0<\n/†çÞ…îG–:%‘sá©ZuFžÉ*ßµ\r¶¬\nWT	#©êù°ÎÀÿ€½¿S§ä§e@“NYAÔ±æÀ}wÀŸm€Ås5ícHª„;R‡ýÇá…÷àÐg-Ë·k}™•\0[8¶®†¿¸V-JÏªÝ’	áŽÕàíc°ûì;æ±nwäZ Z‡kú	à†ÙpÏ­ð•ÛT&^Ž¼X/™îÈP\rŽžWIÝ¾cpnØE~§¸ïœ—Ys{à¦>µžÞ²ZUÎ¼ö ¥-¹îˆeÃ¥Šró‡OÁÑsðÙ%u4Þ/“wB–‚Î0ÇºKjûÑò>XµX%d·Ý¨¶†çñ=«ŽäJ¸[¤TDŸ„ÓWàøE8y>„K#04\n£îºÁ:g™e\0=Ý0{:,˜­Æ¿©OÝà¸aŽÚ:öÎVRá×¥Éþk|¯KGÉÿ`ÂÖ .)\0\0\0\0IEND®B`‚',1,'admin','2015-01-16 15:33:02','admin','2015-05-06 15:33:35','','ç½‘æ˜“','http://note.youdao.com/',3,'','','username','1','[{\"attr\":\"tt\",\"value\":\"tt\"},{\"attr\":\"jj\",\"value\":\"dd\"}]',5,0,1,1,'org.maxkey.authz.formbased.endpoint.adapter.FormBasedNeteaseNoteYoudaoAdapter',NULL,NULL),('f1e33b71-f553-42ab-ae91-2fd913854cda','Token_Based_Simple','http://tokenbased.demo.connsec.com:8080/sampletoken.jsp','E-COMMERCE','1729a1ee16e532d61e097c01054dcfe7','Token_Based','‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0D\0\0\0B\0\0\0îJp¯\0\0\0	pHYs\0\0Ä\0\0Ä•+\0\0\0tIMEÞ,%6¤œã\0\0\0tEXtAuthor\0©®ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0Àæ‡\0\0\0tEXtSource\0õÿƒë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨îÒ\'\0\0kIDATxœÕœy°%W}ß?çœî¾ë»óöE³i´Ìh—X……\0£If©@ $(ŠÄ	IX‚¸¨r¥‚+NÊØ‚ÄE06c,,‰MÂ9‡hŒ4Ìh4o™÷ÞÜwß»·owŸsòÇ9Ý·ï›E#UþIWõÜûúörÎ÷üÖïï×#tf¬µ %dÚ†Šv»M»ÝQµÂèèÕ¨þÜ2m‰ã˜õõuúIJ%dtt”0‘B¡µF)€p€X¤kY^^¡×‹™¢R©\')OíŠ\'Ÿ|‚Ng)%ÖZ\0,€pŸ\"ÿÛoÖ‚H…ƒcFk@ ¥;ßZë`pc!ü=EqÌºS‹k”R!þ!ùw!XK­Reff–ÙÙY¦g&	¤`eu…åÕeFšM&Æ&QJaŒq÷2ÚX\0m‡/P­DÌÌNrèàa¾ø¥?å¯¿qóGŽ°Þíb´AŠ\rqr‰±Ö\"…@*‰~¢T ~²ù$Ÿ‚(\nBúIAHéž#¥$ÃbòRÊ!@„T*¤”þP©Ôh4jŒŒŒ°mÛV^öò—ðšW¿š X\\˜§Q©2:6€1ÑOR+€gÏ#¥`çŽ­|éÏîäSŸú}<D5Ù‚5!Æ˜Á¬Å@¢ôƒÅ‰ŽNòIYìÐßä«ï¿X›nn\0Þà’a€¥t¿K©ˆ¢\nR\næfgi5©×«lÛ¶•½7ßÌ…{Î¡½Ö&K3Z­A ­íÑ£+\0ÌMOò;Ÿø$Ÿýìçh6š(bŒÄµG^ ¥Äƒô\"«Ki¼œ\n²é·\\ÝŠS7K™µÄâ®\ZQÀvüõ›7kÝÕR¬…TgXkQRF»wŸËöí[imiqËÞ·ñŠ—½˜P¯×™˜˜@¬vÖì±v›3·nãsòEþÍ>ÈÔÔ4X‰1„\Zè§_]·r 7Í¥˜t‚üÍ°@zË°€äù]m€Œ1£éõºÌÎMså•W2º¥Å¿úïezj’……fff‡—l£Ñ`a~‰×¿þF:R„*t7€dH¬`H+ÅäýšÚa‰0b“ôœhÛl‘K2\"­$„†Ü} †µÒswÈŸï€ÑT+½.­‘®¹úµ¼øâÝÜþîw³ººŠR\n™¦	[j\r¾ð…/°´´D%ªz«k	Ã\nR9Ýt»-S¬½5`\rÖï`Â\"¬Á\n{ŠÝýN~OQÚ+L	\Z÷l‹q{>É2¢äa¬ð#¡B!…D¢ÈRM%¬°¸¸Ä/ñKž9t˜\'žx‚ÑÑQºÝ.²ÖX^ms××ï¦^m`ŒEH‰Pm5ÊJ«H¤‘(Â\n”‘HÖ\ZŒ5XcJúmK\"a7}ž@@ì@P|e<XiÑRc°hiÐÒ`0þ˜ÙtW¯bb\0¢°a\r‹´Nâ”U4+\rß÷$+«žxòWA@T©#ÇFGÙ÷èc,,,R­V.Rx£h…saV ðŸÖÛ+°Ö_Þ±Þé`ý¢®qßÝòÂCçKŽ»ÏÀïHœ’\nRBQ’[á,ˆpcÖ\rFXŠ¹aA©~?aiq‰……%º½„ 	Â@qð™g0Æ$	B„`¬uA¹—ôÖÏÂIÄñ^ÃÙ\Z‹± …ÄZ	Â€)»K‰AX‹¢d!„÷6‰Ä\Z\0ã>‹“¼ý*ÇÀs‹jXc@ÊBŠ…Ed:??Ïêê*ËËG%°ÖÒY[\'Œ1(Ü¦»¹_µÒ±ü¸›Yé˜H‹\0„wÏBH\0‚,MI²”4Í0&ÃÚÄùƒ2 ¤tq„ \"\"Â((‚0pdá\'¿ya\nÙÊï]Š£„€(é¬w0Æ°¶¶Æøø8‚v{µ]¥”npöTZ¿Y\"<D>FI’„ Š0\\g}Ž.­R©D4\ZM¶ž1ÇÔÔA ¨WR\r$+Ë4Æh²L“¦«íÇV±´´D·×C`Ñ™¦92â¢Rz7?Oµ)¥‹&Y–¡µ¦Ûí!¥$€A°c½žå9„…†üJŽî`Êºîü½”)%ëëëhÝgzzŠë¯W¼ò\nÎ9ûÆ\'&˜žž¢V«R\r\\®#%9£!ËR6â>íµ‹,¯¬ððÃ²ÿW¿äñÇŸàé§íCtIYg\nu±¶§-I‘ñª3˜÷\0 `x‚Þ \n°^M¬\0éÔÿ¤±„1îfZëb¥———yÑ‹.ão3W\\ñLLN>¶ÁbtÌúZ—XYä*yŽ„ŠÑJƒñÑ&»vÌpýuWe{o~þàiµÆü³Ë5oþžï·î~wR²ÊVXg™ó›{€ÄIWÀ_K‘	¯­µy×»ÞÉí·¿›ÑÑˆ^¼ÁÂÂ³T¢\n•j€TŠ@)WY¬Rš”Tã2aQJ\"¥©NQJQªDUHuY[6KÈÉ¶\\Š¼ÊZ(¤ÈCF‰ñ\"]|œhÔDª!,&KÉ²k2þí~›·¾åMt:Žt5Q†!ZCÜK\nÂÐlõ kué·ÀSü-…ô.Z ¤ ‘ÏøàÔ/«F™&(£âl§*âÅ5Ÿªõñ…¶0°þŸˆ_­ã=‚õ{ßú®½æ*æç`Œ¡R«’¦ÂŸ«	…4\nÆ\ZK ¥çFü3¥ôÙª{F–eÞX›8·®.×Ÿ –%¤H±œ¥«¬>âO”Rxt“µˆBe†®E‰Å&)µZ…õõ_r·ßñNöÿj?õz©Ú\ZÂ0$MS‚  C”2di†RŠZE%}V-$Ö¬îÓ{;k,\Z–ŽÌRJ¢3[ä)/dËÁ\0ZÕü Ï[Š„\"iÛŒpNøDQÚíUîxÏmìØ¹G~˜¨¢PHD&°VcmT\\kŒ¥ÙlE™Öô66\n\Z/FGaÒh4Ã(R(9<åôIc2®¤Ä‘P…\rñAPqÆé!l­EÛ¡Û·oãòË_Æøø(»víàéûiµZ˜B|zc©VêüŸŸ>Ê·¿óö?µŸn¯K§Ó!MSÂÐQ©DAH5Šh6l¥Ùh061ÁÜÜ»ÎÜÁòê±\"Þ90\'ahü€5Æ§,Nð1€ÇùóS@H¬5A@wÙuÞfggÁÂo\\ñ\n:ëmŽÐÚ2VdlFÃÔd“ûîûŸùÌgIÓÔu””ÄqJÒ×@\\rÏºˆòôBz#X¯Õ¨Õê£OèbO\nÄ&Nx`CÈ%DÜÖi‰‰;G)‰TÐïÇÌÍÍÐjTHÒŒ(\n¹þú×óàƒßçÉ_þŠfsk Riðëƒ‡¸ë®»iÔ[Ôuâ$v¼˜ÑDÕZ1‘œ&%JIz½^É` ž®ÛõF¢ô½\0rb÷¹ÈãxÎ{8w¨‰Õ¥W_}-3³süøB¯Ûg|,dqa‰¸×GIw£‡ŒD!yÂåT›\"Zk´ÖÅ9RJ´Ö„aÆO¼4äÓ•’ó°ÒEZ…Q¢{šöC\0Æ™w¬÷ãÆhÿ4\n–\\Ë%_ÊÖ3Îà¡‡~Ìü‘£(ô²>µZƒ^ÖC.r†Z•M:Fg,JJÏ“Z0%Vk„ðõ”çaû†Àä6ƒÌÚ<ƒ¤Óqa…˜\"+Ð:Z)]Xœ¦=&&&ùç7ÞÀÕW¿–í;¶177ÇêÊ\nÆf>\nu j9š@x\"ÎšÂë•óž\",©‡xa®×’_ïî/ó	lÖ×SßÄ»O¯ÇA°¸¸D§›l¢\0J…$Icg½“›nºÏ}þxã›Þ@÷X>ºˆÑ)Öh¢0pô5H¡˜9+ygKùˆ}>ôñ“±9‰äé§ádç4ƒœ¼•¦	Qñì³GxöÈ³ùØÝ&ri	@J´vœëî=gñç_ùÜu÷¼åÍo$é÷h·—éõÖAh‚PFn,–€‚a3ðÁØ4w@ªÕêpêT7a ªJ)ÈaúÑCäC’¤ù”P*(&§”ç<´æòË_Âÿççù‡‡þŽßýÄÇ¸è‚óX9ºÈÊÑEÂ\0ÂÀ]›oùø^hdz:[HîæN÷aù¹rQ†Üù•;É2R>ç(‘ÎB*-J9#¦µ¡Ÿô9sçv>øï>À=ó×Üùå?ãõ×]Ëz§Í³ÏæØêja“Ìf\";÷œÄå¹öá­°I¥8¤\0$ÿ|>6ÄZKÜÉ²Œf³Éÿúñù‹¯} HÓÄÓ‡ùÃˆœwŸRZ¢0 MûÄqLµ\ZqÃÿŒ¯Üù%î¿ÿÛ|ìw>ÂÎ;Y\\\\\"ŽûDQ4”wä*éî+Ù©öãuóÌäæŒïtxÃ²ÊDQä3TE³9ÂøÝO²ïgOP«ÕIS=ÄšÛ|!O°ja¨HÓ„4MÉ2Í…ÏÇ?ñî¿ÿ^>õ©ßctËKKXkPRcpãÈolü~ú2¼{£ªTP :îKäÙ¯§„ujµÒ¤ÓésËÛïà±Ÿí§Z­¢µ¦\'Eè=X©A¬†\n¥(A ‘Ò`LJšÆŒŽVø×¿ý~øƒoó±~LJÜÝ@W»ƒµºF¾çÇN¾¹R§@k‹ÑÎ£I…Î[k}ÕëÔ[‡£išÑh49tðoyË-üå_ÝCTk:3%“2ËsîÅ¥y•Pxî#@g)iÚgrr‚}ìÃ|õ+_fjjŠvûR	0ƒ\nŸõ;{œ=ÉCëÍ+È©OÇë¤ÁIHÁFŸ†Î806©\ZŽùÊ²Œ±±Q:5Þóž÷sÛmïãç?’0\n×˜¢Æ°Öœ¦ŠbBøïÎ‹EA€Ö½nŸ+®|sÏ_qÑEÐn¯„¡S~ŸRv=°\'cóâYš¦¤i\n¸jdA×. 9(\'ÚUh¨×ëŒnÙÂ]w}“nxýè\'yô±ŸœGîeœ]‘\0¹Ääßñd•…0\n‰{	;vœÁŸù‹lÝ:G÷Ü¹\'uÃã;É`…“¬<WÊ\r=Y–}aß0Å8Ä…`jf–$Ó|ú3ÿ×¼ö:nyû»¹ïÛß#É¬£½TeiZ$uÖZ²,¤“\"§B®Ç#ŽSvìœã?}ê÷èv»X ßï»‘!ýsŒØ?+Ë2â8öQ ­`Àjsb’å¹¶rž‘ÿ›$)ÕZƒ;Îdltœ{ïû.ï¸õnºéÍ|þOþ”ù…EÏˆE¾Hå«q·@Çe®– pÁßu×]Ã«_ý*Úí6­Vë”“ÏÁ*{¦|Üý~¿ˆq¤ËVƒ¢”ùB¢ÀrhOf`Ò$Z[zqBœ¤LNM36>ÁOþé§|èCÿžW]ùj>üáðè£ûÃ|nùçà9Ö“ác2„€w¾ódiZTàJgvÔa9„åj	ÐëuÑ:ÃZãlˆGYŠžß–/^9cv­xw \"Àèõú4›#LMMÇ	ÿýó_à\r7½‰Ûn{/?üÁEx›fÊ«*üÀ­ó0À¥—]ÊìÜý~¿¦7¦EPèž\\—‚k÷{&sËìØp…xiô0@…\"”çïÝÃtª]Ü¢I_#ƒ*“3[1¢Â=÷~Ÿßð6n»ý_òÔÓ1€TŽêõú¾LªP*DÉlÛ>Ë®]ÛY__GÊ\0%#¿úœ0ÈÜJ–eôz=â8&#k…\"?ÓštnÉsŸ_Îƒno£@û<ÃÔd™ëhmEË×¾ö— \r7ßüVÎÙu&çœ}–÷^¹‘u\\‰µP\rcÎöxž×-HÎþ•Uº\\ÓHH¿{F¤1‚<MwºRPNŽŽ?^ÎÔÂ‰\nG9pZk’~Ÿ-[¶ð³}ûX\\Xäûà›ß¼‡••c>40¥I»ëêõfÑËb}qÚµkrI‹qw»½Âõz£êlˆCì¹ÁÈ]–«±P/ƒ”‡êeI*7lñ åî?Ó\Z©“““,//s÷ÝwóðÃEñ2¨Y–zû!pé€¥¼H\06“¹—É\r²tâ”×VKìû)¶ò9Žôq.2I’â¸1¹$+s>!g¯\\WO^ÍY8!0y×r½^G)Å~ô#|ð‡dYF¬Y!X__ÇX;XTN.e@ò~ÛN§Sô´È|Ï‹Bô¹Œ1†JÅUä’$q“”îAQ™py5rý5F»ÌÕw¹>RK «««ìÞ½›¹¹3R•¼f³É¾}ûøÞ÷ð5_HÒŒcÇÚ?X‚\\}¬—˜SIH\\”ÁË|Qn¨²Lû	\Z´Ö,..ú* ðÞ«â=ƒþ‚ ïMÏ‹Ü€$ý”$Ù ßïsé¥sóÞ½¤IBµR!ÍÒbÀ­V‹ÇœÙÙY.»ììßÏ¡C=ãgÍ}VúÎQÐ($ ×sFÕWîòšÌéIHy3Fý~Ì^À{î¸ƒÃ‡3??ÏÚÚ\Zíc6º]Ö××‰{1kkk¤iJ’&X«ÉÒ„bfvŠ[·òÒ—¿œ×½î\Z¬ÈèntÃ\nÖ€ôa«Õâ‘GáÒK/á±}?çÙ#Ï²un½8A*WK&—\nëÛ9†ò›Aµ@Aß“\\ÆØÛ­Õj¸ÆÛÓ\\W±±$¨HÒ[]gnf‚·ßò†ãÎ×ÚU÷’$ac£K–¥$I‚ÖšÔ‡ëaR­Ö±V³¶¶FkêÕ\Z½^B\n!Iše¤YŸckmîüêWJÒËz¡±\"u¶	ë:Ÿ£Š¾Å\\\\“q¯×Å	† È­oY×Ÿk¬ñ®•üUA–¥h‘¦`ÀZ´ÄJA«UÇ‹ÖnEÒÌ©Z’$$I~¿îµ,ËB‰Àb¬‹\\£z½€;¶ñàßþ=ûý‡˜žÜ†Î2 (z_‡y(çŠË‘«”ÎðçI¡³cÖu!\'P§©æ%ÄRÓ¬p%‡0”IµOéMáuŒ÷,¹!Í]o^¢9‰±±!BâØÐ\Z§·‘ñ_ÿà‰Â:&X£À‚®`/ŒðRaÈ›\\Ê3ËI©<Û®TB”ò2\0#?ñÔ›«Éà\'çæl^Þ=[ô€(\nK\0äÙlÎ,z(žÉcŒ<¿R¾iFiç\"\'Æ§X˜?Êïÿç?à©§ž¦Ñh $ˆœñBÙ\n×m”÷ÄçÒ’ÛcI\nÖvŸ»›n7&0†buAÔsÉÆ\0mí¹HDIw4É\\¿¼ðL®WM\n·jÆ\Z¨0(å\\dDN\\¤YêÅ;`t´…Ö‚Ÿí{‚Oÿág8üÌ<###¬v–èõºTü5®ïÞ¯²c‡4(=\\2gHÒ˜Ûß}—\\z	‹ÎËäMqÅ ¤%ïaÏ¥\'wUC’‚%BŒv-Vi–!¥¤Z«l>°h=œXc|R:PÙ 1FEÕª3ôYfxä‘GùîwïçÛßú.XI*Ž./rþeç26>F¨ÕJÍ¿Òæž)„[1B¸vsX«9÷Üó¹ãŽ;ÐÚµjJ	ªÕ\Z•J¥H¹s3à6Srm!Ñ²LÓl6ùéOæ­o½•óÏÛÍ¥—^Â¶m[™œœ ÑhÒjµ¨Õ*TkÁq÷É´ñ|†¡ßé÷æ™Ÿ_ä±Gçþûàá‡ÁK£QCÉèèŸÿŸáª×ý¦ëQÃ)lÎëŸLñ7ÿžYXko$	QT!pe€\\B”×e6!72Èdƒ $Ib¤TdiÆ<È·îûJ¹ÇÇ\'hŽ4¥ÑhP­V©V«4\Z\r*Õ*Q%ð‰UF–e,--Ñn¯±°0ÏÑ£ËtÖ¬Œ¡”$Ëçùôþn¸þ*{¶O7éclÌ$î…áÞÂR¶BU­Q\rØ\r¬ÈÀ¸f>£0Ö2Þ<é©cAk¶liÒjmadd„ù#‹Ôk®!¶ßï£TXL~à¬÷dk4Ê#SÃØä´·«c,ë½”öú\n-z½õß]õï¥\\¿›£kŒŽ\0–4ËÐŽµW¹ì%/æªk¯¦$Ü»ï(_h… ubd=AZM%ˆt†šÌxí÷qfý/°\"\"Ö‘IŸ¤?NÁYæ#¼æ¥oD›˜TgÎË´Z£4\Zufff8øëgü›\nfÈ•cÿœv#ä’”¥º°9ÂÈ5éúÔ âDt@™.pî8ïÊ«q®öóþ÷¿Ÿ0IÒž[çž_la=lÒÀD½ˆ«÷ëülùZ¦§¢fcÌ9T[h&+ëã(:»\"¥#½ã8fd¤Éìì»ví¤Û]\'ŠB‚@Q©DC’1øîWxH­­MÖ:‘Öb)ò9µÈ÷ÍÇuæÂoï\"`îe‚‹/¾˜k¯½†$Ó$¼h²Âî]šX´©kËø:4bÖ€ÑT„àÀÆ6÷_ŽÁs˜,A%Ìá³yÉÅW`m†0®R »½\r‚0`||Œ;wÒhÖÙèvPJù„ÇPÜ†xÜ¢”~/zÇs‰8Å~²ßÏrQ.ÞÃ%IÊ-·ìejb„8Ž‘T˜u^~æ:=ó4„Íö*õx$ÐDl0)Ž ³˜ù#/ÁÆ¡Â.:ˆé­U8{üjš•Q°®Kºh˜‘RpÆÖ9¦¦&¸úš«ˆãÚ¤¤YâuZŒÖ@mþßlyÚ°9uÈBkƒ‚~¿Ï®]»ø­ßz#íµ¾«„\n¡c^³mŠ]­qq„_ð^sù2†5Fä^¹ç1ÎoFt\\FûÈmŒÊÙ(ýþV.8÷U*Äd†,MÆ\"ÃPÑëm077ÇôÌW¾ò\n.¼ðŽ[)JÎó˜¡çÀ¼½ê€MÜoÐ¸ãŠYZk²,eïÞ½lß6‰±`´¡wèš[Ãˆß<sñ¢dl[Æì¶o°­zˆFÖbÏ6ÉE3ÿÀÆJF/|%•z„^©T/frz†¸»WW×Ö¨ÕjÈf³ÁÚÚ\ZZ§ìÙsÜzëÛ9çœsX]Yvzå_ÿposË¢52‹àÄ»|ÎÝÆ¹dS99«íe—½ˆ›oÞKg=õÄ’$Š*h%åÚsëì¬ÍòwOÖé¬=ÍåS‡i³<üx—ÙñŸP™Yç{‡°Ô=„Ym0wÆ+°Ò 0$ýc,­‘:A¥R!Š\"Úí5¦§§ÙsÞn„|èCä‡?ü{îÿÞƒ,//±HêCü²ÈŸH\r6ú;éKÆ:WËÍm]££[Xï¬qûïbûöiVVº„a^ÞTh±–¤ìÙ¢xéÎ\rîÝÐÝÍÙ2gíHXlj½”g‡<õdÌêÁ—2ÙŸå‚³_ŠÉ2*aÀüâ\"­‘&Q\"Rc¬Ö†…ÅejÕˆF³ÉÂÂ\"û÷ïgc£ËZ{~Í3Ï<ãÞ{ëvû2¡ìøÎ\0ù\n—ý\\\róãåÏüš\\Š’$áÖ[oåãÿÎàØ7k,‹–Ì\n”tú†»ï»Ÿlc7ßt#&âŸ~ú¿¹xÏvf¶ïæ÷>Àêá\'¹þšW±gÏ…è~Ÿ¤çæ311îèLmÜY`´e~aLNNÒívYX\\deµC·Û+€p­R~bÖ½2°3Ž_H’„8Ž}‹ã[»Ý®ç<’¡¿1Äq<ô»{)°K³Ùä¶ÛÞÅûÞ÷^â8-x!=X¼ÆŽ%‹ÕV(ªµ:\r2@kHâ.tüK£Q#í÷iwÚT¤àŒ3f‹„öÿ°>4Ï®ºL\0\0\0\0IEND®B`‚',1,'admin','2014-12-14 16:14:35','admin','2015-05-06 15:31:11','','For Test','For Test',0,NULL,NULL,NULL,'0',NULL,2,0,1,1,'org.maxkey.authz.token.endpoint.adapter.TokenBasedSimpleAdapter',NULL,NULL),('fe86db85-5475-4494-b5aa-dbd3b886ff64','è…¾è®¯ä¼ä¸šé‚®ç®±','https://exmail.qq.com/cgi-bin/login','OA','815fe27ae9ab72a746ddc1b2b33298241a5a4e5fafa030e2336ec5109626b452','Extend_API','‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0C\0\0\07\0\0\0Wy÷¤\0\0\0	pHYs\0\0Ä\0\0Ä•+\0\0\0tIMEß+2x\\ö\0\0\0tEXtAuthor\0©®ÌH\0\0\0tEXtDescription\0	!#\0\0\0\ntEXtCopyright\0¬Ì:\0\0\0tEXtCreation time\05÷	\0\0\0	tEXtSoftware\0]pÿ:\0\0\0tEXtDisclaimer\0·À´\0\0\0tEXtWarning\0Àæ‡\0\0\0tEXtSource\0õÿƒë\0\0\0tEXtComment\0öÌ–¿\0\0\0tEXtTitle\0¨îÒ\'\0\0\0IDAThí›kŒ%ÇUÇUÝ}û>ffgv×û°½ñjmÅ²ÁØ$VÇ6ÈQ@‚`aH „øÄ7„„øàQ¤ R„	‘€ >ldL?ØuÖk¯÷åñ¾w=;³;Ç}twU>T÷}Mß™õ¬³kE>RïÜ{»ºªÎ¿ÎùŸS§k•sNøX\0Ð·{%	GÝœ@¿Ù(¥P[H\0è-vp³ö+\"ˆ€R~J­Ÿˆ*s\'‚.iüaˆ>@×ˆ¶”é¸Î2$o´ØÊxñÌ§ZtËîÛøäÞIÆâˆVjF¢[&Ö4Û>Q‡vVqù\nmö´¿ŠÆÁô²eÁ„Î±‘¡ý\Zëˆãç„™Ùeæ®,ð‰íUžú‰Ýl«…ˆÈ€¥n²ÔÎøæáþæ‡W9·\"TÙ¿]ñÄOMñÐ½ÐC2‹7;¼b¥“ß&Ò0×¬1½xÉZÈÞF˜+;ÚDÛÇ—ÿ·¨qS^UqA)Û)üxÅóa .]R>²ÂééYöŒ9–;–/}z/ÕAõKÝä‡¯ðç/^`¦é¨`@kda™F#æ3à³÷ßEjœuMˆ@aÑl\'ì\n~÷q&*zSwL­ðú\\ÊÁEÈ‚*.K6¼eƒ·Z­çÎ]ãÐ§¹6wúxÄì›ø£/ìç7>sWþLÎ%e¾zq™S%Âbœ`ŒÃlgy¥ÉÁWóÂÛi&QŠŽRëÈ¬Œ¼+´“”°Ráª«òÏ§Û,´\rZ)ÌÐZ˜œè\0¾{¾ÃËs–¶ª$	écdÖÏ#µ‚q`Q;u…—_>ÊÌÅ«Äµ:Ö¶Wáû§Öé]\nÆbÛBT\'T NÂG·M°œ\ZŽ¿þ/~…vF\\Ñ$™!sŽÌ9ŒH÷²à/2\'dÆ Âˆ³í€ï]J™m\ZB­°¹ò‰B¥H­ã[\'W9|Ýa*uÒ,Ã8‹íë·#BæIfA+¬‚·Žžçõï¿ÅüµëÄF7ŒJPQÅfÖ³&0‚3TnfôsµHÂä6šó‹œ8t\nI2ýÔ}Ü1^£ÓÉ@+Tnr]ûËû1í:8¶¦Ð³Ÿß«ØS°q ˜YIøÏómŽ¯ijuH¬s€êö+ÎõÉÇ°Ö×b:©áÔÑs9tŠµÕc“Û¨TcLf<i!Rªg‘*·‰\róÏº7c \"»vâ®ÌrìSdiÆ£ÞÏÔxqg]È¾Þº€`Ê:‚0âÈ2ˆd|qŸf2V\\^Iù)o6kLV!í$¸. ‚Ó›à£‹@…¬5Þ=vŽ·^?A§Õfbç¢j\r“¥è>=œµ]WôÝ\nU†øÑW¡Xë­H÷î¡:3Ëô¡“´Z)?þc1JÀ9Wv@|æ`lJX‰9´˜ tÊÓû*üýÑë\\¬îbG5¡•˜ÞÊuŸ[¿`~±„´qüð4GžÀ:ÇøÎQ„ÉRÿ|Ÿâ‚”ö7Ò2FJA½\n:»wÍÍ3óö{¼ÔJxìó°}¢3Þw‡Qf-( K”R¼:—ñƒË-2»«&_9•+ë&ß\ZÎa3Ë‘×Ž1}d\Z¥ÉItz×,ÜVúg1ðÃ\0£ë¨2øY*1æŽˆæ§/ðZ–ððÏü4{÷lÇuDû{ ô’¯¤OTtXê8:k“™G/_PJ\rpDq„•ˆæj‡£¯åÜ‰sˆÀøöIÂØ[¨ˆCåÄ\'¹òÞý‘õ!ýÆ-C©. *ÿ.Î¡ë5Â»vQ¹2ÇòÙN)…|î!öîÛÊ2Œu©L¿b…é+¥PiJ`2¬ln	ªÏMûÝD+E Q¥ÆòÂ2ï¾q’K\'Ï*EcÇvt%î*îÁúÖQ!(Öyñ£é³+\n;6EzwÚµ.™§åNÑ|¬Âî½;µÁei¾ÉõJ:¿zbsw(¸¥´â3 Z£u•¹ÙUN>Ãùc—ˆ«ã4¶M C…X2EáûÍÁíáãÝ´,eþàœÑ·sPÁÒÐ	ºÁö˜íAìý‹\\y­ÃØÐ˜\ZG´Æ:A+oº…’Œ ²~®ñ 9´PÖ`Vç¹rø8K\'Î³w[¨V!ª*:Ö’Z†\\qHrã¦ÜdH­0•1ž¾7æ¹/ìg×Xìu‚µŽ@+²0äÛRþw6Á…0)YA|Ý¹•€10w!ÀAP!3OEüöÃÛÙó³O’¤EZ)æ×¾òwyþÔ\Z:kb7Úó¸µ%0|]BAóà\rî¿£1²í³û5õ\0¾;“ÑŠj„i‹ÔySõ46ýw×õé!‰ªH–ñä®\n¿¹?æÀDäo6*Ýç&>¹gœÞKQ¦ÕÕxXo¡àÿ#´äd\Zj°NH¬#ÌM_¡Òì¬jžÞW%Äñ_W\r×*cÐYËS|Î1Àfùw%>uîÄ\r\"›ñÔžgî©q`<@ë<¤q¤‰•NíÊ¶€*bJ­ß‰lÙM\n@œx—‰Ñ%»Wã„UÍ¯hP	ZüÛå6WÃ²åŒ¯`\rYâÓï,ŒÇòÔ.Å³êÜ•§íJ„jØS&PB 6›Î¹Ò•÷S–îõ¡‘Ï¼7Èˆ&ÅF¬(žÙß`,jñõ÷Z,ê\nØl0-ÎC­(P: bR¾xgÌ³\ZLÅ\Z+B Ãq±(\\®äe‹1FqK\nÂE™/Òðów×øãÇØ¥SlÔð™h¾7p‚b0p_•ßº·ÎT¬ó~6©Ýd}ðæ,c)Å»«h„¿;pBM´—±ÎƒÕ©N0åZüácüâ¾\ZcQ¾Á’uÑ\'>wW¹ËÄß÷m†¿õ`¸¼ üäž\Z\ZÅ?œK9”U‘´CVipO”ò;ûªüê=u¢@õê¥®zQëËªîUÞv8ÿ(¸´Œ^ü ·Ò6–f×‹Îk$Î	ï©Òˆ_=±Æ[Ç^Õæ÷ï©ñ¥ý>Tw9bS)ríã…à¤¯&â< å`UÞ–¤{ïƒ;j‘§8GvÄüÅÃš¯\\åé»küÜ]µnmôÆ€èéwƒÑn\"âéÑe1Ì-w“þ	ªÞ;ñåOO‘óäìMw`2cI3K%\nºéû-w“uÝäœPU·Û›Â‰CoZÃ95²[ko?àIµØº‡[xÿX¬lQ\Z•GÐwÖÏü¶pÆºn¸9×ð;áÀ—n€3ºWÎÝ>Î(²ÍÕÄr|n\rë„ŸÜ=VúÚo3)ªÛ¢ðf¶EûÚ† jÈªµ¾}Ghg–|ó\n¿ð­“üÒ?MóÍ7¯ÐJí–Ï9é•87»†ÀRJÝ02ëCç·ß¹Æ—_™eÙ,gŠ¯¼>Ç¿¾s\r­™½qHŠ¤)3qäŽþËoâüF.³½\\#¸Õ–QXo(^<½ÄWp‘÷ÛPµ-ª¶ÅLþò•K<?½è³Ï<”¡ð¯\'ÚI†r†¸c€Gò{8YçI7FÿÖûšœ{n±Ísÿsž£‹–Ø4Iò÷±q¶Æ;K†ç^ºÀôµZ«î³‰RŠNji\'×ÝÂ—]¾¿ò·FQ!ï½ØXŠU^j¥üÙg9øþ*8Œëí$Œ(gyãýUþôù3,¶|q×m†°¸Ú¡Ù18gGÑ_ëpCÇ’Ddë`ô£ëÄqF]‰q `¹cùúÁ¾sj!JÌ€¢N%–”€N/ñW¯]f¡™ÐI\rÆºÒ«“\ZÎÎ­pu¹…rfÝ\\oT¶\\E)p†¥¶?ÅÓ_yZ7HnîÏOÏóµƒ³4­FcüjÁ€uù2´¬æoß˜cÿdÌï=z\'AeôTÃ@³¸’°´š ÖŽ,ùsWâ]¢ßÞ”\Zñ®u3|ÆHÒä¿O\'üÉ÷Î0VÑŒf¡9^:{¹D¡ÅXò÷]ÝIi‚FÐJ˜Oà¯Îpúò2Û]‚ÓšõÄ—f–·Ï-0³ØÌ£Iq™{å,îDò·ûÅÏ²u0D€¬Ã‰¥*\'Þj‚\n6yÈA’šV líŠ©ûº¨#tmŽ\\99“1±²J”¦^G¥Qƒ˜1ÆG“Þ†‡ä.Ü/[wü¤´M	Ú×o€ò}áÛÈP%}®cQè,Á†šfì˜l® õÇªdÐø#Åñ(\ZÝ¨¢A-ÈM§ã\"þTÝJÚÏTbdb‚‰¥%tfpZûÝé@k5ð§TòÔóÕ‡`ÃåÃ›ícø÷)Þµ*E»V\'LSê««(ã®“{(Ç¤H¾<ÃÝ€e”uxóõ—áÓ}wª5kcãÆ7›ùêö¶ïIoßçO¹!ÎMP9ê·„á9øù[ÿÜ:\\Ñc‰;œö¡r³yöÈÙÝðÜ†ÑDJ,ã¶€Ów.¤‹i½Ž²–Ð´5(œ*^-?Þ;:E÷_\" Z	Ú•fJH£LgcdÄ9’FƒµÉm@ÿ•DîÉ‹H ”¦Z	˜‹ó[¾U©e<rgƒ÷2Mª¡t+Ù·]\n³ €‰q¬8êË×}ù_‡¥ÖyÂì„N&Tu‹OÝ·o]©ô ý/?°“3óm¾ñö<íh\n¢˜Áàv‹ÌJ§±›¬º@}um¢cD‘w‚Ø”X·øõÇöñÌçt+jÝ\ZêðÙñ¢Á…¥ÿrtŽ£³MZ¶`¨ÝJU^âÏê+«Y†ÓC`H/’T+!~b’_ùìî¾c|]y±ô }?®R¦c)þ¸å:~üÔúäc0úäÿYÈÈ¦óK:ý\0\0\0\0IEND®B`‚',1,'admin','2015-01-20 15:46:06','admin','2016-11-06 15:10:36','','è…¾è®¯','http://exmail.qq.com/',3,'','','username','0',NULL,11,0,1,1,'org.maxkey.authz.exapi.endpoint.adapter.ExtendApiQQExmailDefaultAdapter','connsec','2df203cdfa2bd2dfd29f48739f431869'),('maxkey_mgt','MaxKeyç®¡ç†ç³»ç»Ÿ','http://sso.maxkey.org:9521/maxkey-mgt/login',NULL,'4bbb82a8f4928756c4054abdba5956ba1e698f87c64032dc548d6ec7dc3c4863','Token_Based','ÿØÿà\0JFIF\0\0`\0`\0\0ÿá\0ÊExif\0\0MM\0*\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\01\0\0\0\0\0\0\0\02\0\0\0\0\0\0\0’;\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0\0Q\0\0\0\0\0\0\0Q\0\0\0\0\0\0ÄQ\0\0\0\0\0\0Ä‚˜\0\0\0\0\0\0\0\0‡i\0\0\0\0\0\0\0¦\0\0\0\02015:01:27 13:55:30\0\0’†\0\0\0\0\0\0\0¸\0\0\0\0UNICODE\0\0\0ÿÛ\0C\0		\n\n\r\n\n	\rÿÛ\0CÿÀ\0\"\0ÿÄ\0\0\0\0\0\0\0\0\0\0\0	\nÿÄ\0µ\0\0\0}\0!1AQa\"q2‘¡#B±ÁRÑð$3br‚	\n\Z%&\'()*456789:CDEFGHIJSTUVWXYZcdefghijstuvwxyzƒ„…†‡ˆ‰Š’“”•–—˜™š¢£¤¥¦§¨©ª²³´µ¶·¸¹ºÂÃÄÅÆÇÈÉÊÒÓÔÕÖ×ØÙÚáâãäåæçèéêñòóôõö÷øùúÿÄ\0\0\0\0\0\0\0\0	\nÿÄ\0µ\0\0w\0!1AQaq\"2B‘¡±Á	#3RðbrÑ\n$4á%ñ\Z&\'()*56789:CDEFGHIJSTUVWXYZcdefghijstuvwxyz‚ƒ„…†‡ˆ‰Š’“”•–—˜™š¢£¤¥¦§¨©ª²³´µ¶·¸¹ºÂÃÄÅÆÇÈÉÊÒÓÔÕÖ×ØÙÚâãäåæçèéêòóôõö÷øùúÿÚ\0\0\0?\0ýü¢Š(\0¢Š(\0¢Š(\0¢Š(\0¢Š(\0¢ªkÚý…´[­KT½´Ótëš{›«©–mãQ–wv!U@ä’@ùµûqÁÖŸ²ßì“%æ—á}cPøÍâ‹| µð˜VÓc~1æj	R3óAçx v\0ý0ªú®­k é³^_][ÙÙÛ!’içGJ:–fÀ\0zšþ[ÿ\0k¿ø</ö™øé%ÕŸÃ›\n|\ZÑfÊÆÖË«êÁOZæéL]:4vñ°êpGçÇïÛâ¿íUªµçÄ¯‰6ñÔÞa•·¬Ü^E	?óÎ7b‘ž\0`(ûøáÿ\0·ý’ÿ\0gg¸Å¾­Å¡\"{]+S\ZÕÔ,	Z!4†>é\\ûr+å¿Šðxìwà	Yt›Ï‰^8U$Ñ<5ä†ç¨ûl¶Çß+ùL¢€?¥ÿ\0Áîmn‚èÿ\0~)_A–ËÞ\\ØZ63òü«,£‘Ôg~µÆÄq~ÿ\0£u×ÿ\0ð°‹ÿ\0‘+ùâ¢€?¢ÍþƒðÃÉý¥ðÅÖŠ\0òÍ·‰-îùÝxíÓ?…zO‚¿àõ?Ù§Xe[øñ£E‘±óÅ§i×p¯ä‹Ån¼!Ïµ1TPõõð—þ„ýŠ~,O¿ü-§ðÍäÇˆuíþÍG8æo% _ÆAüñõçÁÚÿ\0á?í1Éðïâg€|u¹<Âº¿k¨Hƒ;–\'fR;†\0Žø¯á&¦±¿ŸK½†æÖi­î-ÜI±9G‡ ©‚=E\0~ÔWñ‰û,ÿ\0Á{¿k_Ùkxü5ñ£ÅZ¶•n@þÊñ4£^³(1ûµ[ ïñÿ\0,Yçd×ê/ìeÿ\0±ÛÝMg¥ü~øNÖ»°²ëþ	œºH\0µ…Ëî\n,ËpÇŽô ßj+Â?cø)·Àø(&†/>üJðïŠ®–?6}(Jmuk5èL¶s9Êl8áˆæ½Þ€\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€\n(¯œà£ßðUƒßðK…ð’|Nñ\0‹P½G:?‡l6Í¬k®½VI($n•ÊÆ¹\0°%A\0ú#QÔmô>{»¹áµµµ¦šiœ$p¢Œ³3€	$ð\0¯Éø)çü¿ðöRšûÂßím~4xÖÑ>¥\rÁÃZt˜à›…ù¯1ÁÛŒ8E~2ÿ\0ÁWÿ\0ààŸÿ\0ðT½NóE¼Ô$ðÂ¶“ýÁÚ5Ën†¾œ{·Èk•R±«eÂ4ô‡íéÿ\0iøùÿ\0$ñ·_¼{©j\Z?™æ[xvÄ›-Çœ¨KT;Y— ’Mò`¹¯›è¢€\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€4<+â½SÀ¾#²Ö4MKPÑõ}6e¸´¾±¸{{›Y•xäBX„E~¯ÿ\0Á5ÿ\0àî_ß²Ôºo‡>3Û·Æ¯ÂR¾¹”Aâ[¸–çîÝ`e¶Üîp<äù!E\0o_°üàŸü¿À?ÛŸ\n<cg«][Æ$Ôt+¢-µ#?Ò-IÜ«¸€$]Ñ±á]«è*þ	þ|`ñWÀOˆš_‹¼âcÂ¾&Ñfóìu=.éí®mŸ¡ÚêAÁ‚:0$A\"¿ ø#—üË¤üJ›JøwûS5‡uÉv¶^?¶ˆA¦ß1ÂíTµry3Æ?1Üª– º4T\Zf©m­é¶÷–w^YÞD³A<2	#š6•Õ‡¤Ajz\0(¢Š\0(¢Š\0(¢Š\0(¢Š\0(¢Š\0(¢¿%?àâ_ø8’ø\'•…×Áÿ\0ƒ÷Z~©ñ£TµÎ£¨·þ\n†E;Y“$5ë)‘¸*ŠUÝX2£€wÿ\0ð\\Ïø8—ÁðKëÀþ\n]3Æÿ\0o \r–Ò,<6®ÙïÊwôe·VW`C1E*[ùiý¤i~×¿5o|JñF©âïko¾êþõÁl»h $Q(ácUpªÉøŸÅ\Z—¼I¨k:Î¡{«júµÌ——··“´÷“ÈÅÞY$bYÝ˜’X’I$š£@Q@Q@Q@Q@Q@Q@Q@Q@Q@Q@¥ŸðD/ø8¿Ç¿ðL=rÃÁ>6mSÇŸæ“cé&@÷ÞÜrf°g?w$–·bŽJ˜Ø³7õ9û<~Ñ^	ý«þh~?øwâ-?Å^ñ}Ž£fÇd€¬¬¬G\"0*Ñ¸Œ¥X¯àÞ¾Òÿ\0‚0ÿ\0Áh<wÿ\0Œøè/¬M×ˆ¾ø†d_xY¦Â\\ ãíVÛ¾Xî‘z7ÀØümd\0þÊ¨¯=ý–joþÚôˆÿ\05ë_xSÄPy¶×1<L8xeCóG26UÑ°T‚+Ð¨\0¢Š(\0¢Š(\0¢Š(\0¢Šù×þ\n•ÿ\0ðüçö?ñÅyw·°øFˆå×µ9¼›e=Bü¬ò8¤qÈÀ1HËÿ\0ðqü6Ëþ	kða<à{«;ÏŽ>6³fÒâuY£ðÝ™%£2‚Ù°£Õ™XÊ·òwâj^6ñ&¡¬ë:…î­«ê×2^^ÞÞLÓÜ^O#’Y‰gvbIbI$’k®ý§?iO~Ø¼Qñ+ÇÚ´º×‹<]z×·×\rªNE\ZäìŠ4\Z áG¸:\0(¢Š\0(¢Š\0(¢Š\0(¢­hz÷‰µ‹m?M³ºÔ5É6öÖÑ4ÓNç€¨Š	f\' &€*Ñ_z~Éßðm?íûZCk{kðÊ_hwI½u?\ZÜ#¶`×|‚H\"¸yûïàgüýª\\Gkqñ/ãÝ…£ËÎŸáŸ=À<ÕÄ±ãžæÜçÚ€?(¯éûÀÿ\0ðe¯ìÃ Om6³ão:üÞÂu]>ÖÚs·*–~`äbAÐHÎ{/øƒçö=ÿ\0Ÿ‰¿øRþ3@Ê}ý>xãþ³ý˜õÙ®&Ñ|uñ£A’c˜áþÔÓî­ ã\nÖbB3Ï2ÏQ_5üsÿ\0ƒõËHo®>üzÒu	6“g§ø›ÃÒY¨;FK»yeÈ-Ÿ™mÆd€~Ñ_u~ÖŸðmßí}û\"[Ý_jo<m¡Ú€[TðdÃZŒŒàŸ³ÆÚ¨à–h@ç8êš]Ö‡©ÜY^ÛÜYÞYÊÐOñ˜å‚E%YN\n° ‚ Š\0¯EPEPEPEPÝŸðBø-‰?à’´B‹ç¼Ö>xÂâ8üY¡¡ÜÑcå]BØ»q<¨À•FÁ¼×—ÂïŠø×ðëEñw„õ‹Ã^#³PÓu9<È/ ‘C#©÷¡ä‚WðC_³_ðjü¾OÙ‹â½Ÿìåñ\'UoøW^8¾Ûá[ë™?wáÍVVÿ\0PIû¶÷Nqè“l,Œ\0?¦*(¢€\n(¢€\n(¢€\"¾¾‡L²šææh­ííÑ¥–Y\\\"DŠ2Y‰à\0$ž•ü}ÿ\0ÁÁßðV‹¯ø*gí©y6‡{3|\'ø~ói¶åRé7>¢ÊpwÜ²)\0¬I\n‘¸1?³¿ðv¯üüþÉ_±´?|+©}ŸÇŸ\Z!’ÞøÂäK§h#+rùƒpßèà†ŒÜãE-ô\0QE\0QE\0QE\0UßxoQñŽ¿g¤é\Z}î©ªj3-½¥œ\r=ÅÔ¬p¨ˆ ³1$\0\0$šöØþ	ïñCþ\nKñâÏÀô5Kù\nË¨ßÌ|½?C¶-†¹º—Dà»‘µ˜…?ÕGü#þ/ð‹þ	Iá+]JÊÒßÆŸî-öj^1Ô-€™ž+8ÉamQò’î>û°À\0‘ðLOø3çâÇ«?ÅŸ´>±wð·Ãs‘*xfÁc›Ä7qÿ\0ÓVmÑYä`á„²C\"\ZýÜýŠ?à—ÿ\0àž~[/„ÿ\0\rô]”Ùq«¼fóW¼ëŸ6òbÓ0É\'``‹’Tq^‰ñßö’ðwìÝáíOkØ¬€ýžÙ?yuxÃøbŒrÝFO\n3Éšøö‰ÿ\0‚¿ø»â“iþµÒ˜•Rm›P˜}yH³è °ìõô™\nf9«æÃÂÐþg¤Íü“>Sˆ¸Ó*É•±u/>Ž²û¶^­¯#ôgÇŸü;ð¿JûwˆõÍ+Cµþ/nRçÑw¸ûšðOˆðV?„~’Hìo5L‡oüK¬H?ïLc{®kò×Ä~.Ô¼i¬K¨k:…ö©9Ì—7s´Ò¿Õ˜’j¨<xWéÙ…¸:i<eIMöVŠý_âÇ3O±õŽŒ`»Êò¢_s?A|Aÿ\0¯°†BºWÃÛË¤ áîµu„ŽÕ‰óÎxÜ:{ñŠ?àµz–üÿ\0ÂiOíƒý_#íû¿•H’>_AÈâ­ì/ÿ\0oKÿ\0’>^§‰ÜI)]b-ä¡þDýðïü§M¹pº¯€/­WŒµ¦ª·ú­~Y¯QðüSá/8ï/õo\rÍ\'u+#³?ïÄdP=ØÂ¿*Ãâ¤Y}káÎMU~î2‡¤ŸþÝÌv`üZâ\n/÷²OñE/ý\'”ýÆðOÄ]âN’·ÞÖ´½jÍ¿å­•ÊL«ìv“ƒìy¯ý·¿à”ß\0à¢\Z,–ÿ\0~hzæ¥åùpkvèlµ‹OM—p•—\0àìbÈqÊ‘‘_—^ñ~­àmf=KDÔ¯´›ø~åÅ¤í‹ôe öõoìõÿ\0mñG‚ä†ÃÇV‹â5NÓy\n¬:„CôŽ\\z¬{±¯‡Í¼3ÅÑN¦j¢ìô—Ë£û×¡ú>Eã%K2¦è¿æ^ô~z]}ÏÍŸ–ðSïø4#âwìÕa©x·à­yñwÂ6ªÓÉ ÜÂ±xšÊ1ŽP¯N7å¬rÄÇšüzÖtkÏkZ~¡isa¨XLö÷6×4S[ÊŒUÑÑ€*ÊÀ‚A¯ïàÇÇÏ	üð×ö§…uk}FÀš/¹=«á’3ó)úŒpHæ¾8ÿ\0‚ÂÁ¿Ÿ	àªÞº×ü^·„‹X[ôÒ+ø†>Ñ\0È•06¶Ü£~mZJ3tªÅÆKtÕšù®áñ«ÓU¨IJ2Õ4îŸ£Gñ÷E{í½û|Oÿ\0‚x|sÔ>üTðÝÆƒ¬Ú’ö·™,u{|ánmfÆÙboQ†S•uGVQãõ‘°QE\0QE\0P¬U²8#Gj( ë[þ\r¢ÿ\0‚¹7ü›ö9ñ†¤.~.|*Š;[y¤&mnÈ‚¶º<³°CÇ$ù©¼í ¯Òjþ$ÿ\0à“¿ðP}gþ	“ûrx;â–šn§Òlçûˆôø:®“1Qs	\0°\0H™8E<\nþÖ<ãm\'â_‚´è7öú®‡âu-:öÝ·Cym4k$R¡î¬Œ¬¡©EPUµfÓÃº=Ö¡¨\\ÁeccÜ\\ÜNâ8à³;1áT($“À¬×æßüAûq·ìÿ\0°ñƒ¥Þ}ŸÅ®ƒì‚6$ŽÎDg¿“Ýû:´$Žt”üÞÁ]¿o;Ïø)üâÅšèèw×§OðÝ¼Ü=&ß1Ú®ßà, Êê?å¤ÒsšùªŠ(\0¢Š(\0¢Š(\0¯|ÿ\0‚oÿ\0Á9¾!ÁOi}3á¿Ãû5Y%jÕõ{…?bÐlƒ\0÷3ØdAó;£©#É¾|%ñÇ¯Š^ð_„´»­kÄÞ(¿‡LÓ,m×t—3ÊÁUG ÉÉ\'€$€	¯ìþÙÿ\0¦ð¯üoöK°ðvš¶z—µ¥ŽûÅúúDMZ÷«}ï³Ã¹’%=f 4ãþ	¿ÿ\0ßøqÿ\0¿ýœ4ÿ\0‡Ÿ4ü¶ãYÖn~ßâÍ¸k‰Ø~Jƒåp£¹<ŸíÝÿ\0)ÒfÁsá}ŸYñÃ&$\ró[iq÷¤ÁÈŒ8-€@l¿ø)Ÿü~ÏÚ,ž\nðÒ·µ(A¹¹Œƒý‰\0C×gåÂãü9üµ¸½’þæI§’I\'™‹¼ŽÅ™Øœ’Iä’yÉ¯Õ8\'V.+0ÌîþÌ›Íÿ\0w²ëé¿ã>!øŒð–Y•¿Þý©oÉä¿½Ýí·Û¢ø‰ñ/^ø¹âËsÄšµæ­«]œÉqpûŽ9Â¨èª2pªŽÀV0“æªË)_½Ï½H’dyk÷R„\"¡d¶KDç*•§Rn¥FÜž­½[}Û-$»}ÅMßÝüªšÿ\0³ù\ZrKŸcL‹—ÖPßìš~}:¦“c­MØr)XI\nQR#î/åUÕÃt8§nü*@²²m©îëU–\\pÔðÜqÍ¢:†ÿ\0|Að‡Å6ú×†õK­/Q·?,°¶7êÃ£©î¬>•úYûÿ\0ÁA´ŸÚVtp[èþ43äƒ¶\rHËÃžÜÆI8‚Fqù^’U‹\rBm:ö«Y¥¶¹·q$SDå\'SÊÃAt¯™â.ÂfÔ­QrÍm%ºò}×—ÝcëøO1ÙkÒ|ÔŸÅô~k´¼þûŸ©ßðSø&?ÃŸø*ìåyà?Yýžòß}Æ¯ÛÄ¦ûÃ×ep&ˆŸ¼‡\0I;dQƒ‚—øþÿ\0‚„ÿ\0Á?þ ÿ\0Á5im[á—ÄKáÔl€¹°¿·ËYëVLÌ\"º%i2²²°H¯ë»þ	çûs\'í	áåð¿‰gXüi¥ÃŸ4áWW„ËE¤Qê:ýáÆBòŸð[_ø$†ÿ\0à­_²…Ï‡dK=7â7…Ö[ÿ\0k’.\r•ÑQºÚV\0·Ù§Ø‹ ÁXä\0˜À?Îy¦YˆËñ2ÂâU¥¹®y?ëSúË\'Î0Ù¦Ü$¯}éõO³]\rã6ŠÙø‰ðû[øKãíkÂþ$Ón´oøvúm7R°¹M³YÜÂæ9#qýåe ý+\Z¼óÔ\n(¢€\n(¢€\nþ¿àÏOø(l¿´/ìg­üñ\r÷Ú<Iðnu}$Êù’ãD¹fhÔdå¼‰Ä±“Ñc’ÝGJþbkïOø6¿âç¾\nÁ[>jž	ðß‰üQ§êÓŸøªÛF°–ïÉÒo\nÇ%Äâ0vC‚‚Í€>Î:ô ØEQ@.ðxÏívÿ\0\Zÿ\0à¤Z?Ã+;ƒ&ðsAŠÞXÃnQ©_ª]\\0íþ£ìHGPclžÃúˆ»»ŠÂÖIç’8`…’I#XÔ’Ià\09É¯áWöÕý¡nk/ÚóâgÄË¦•›Ç%¿ÖbY>ô0Í;´1}2ˆ`¢€<ÆŠ( Š( Š+Ó?coÙ[ý³ÿ\0jŸ\0ü+ðè#TñÎµo¥¤»w-¤nÙšá‡÷bˆI#c±œdÐî7üÕÿ\0¬‹IðÞ¥ûTxÇOI/5#>‰àH¦›xT˜¯5Ïw`Öèx!R~¡Å~Å~ÛŸµMì—ð>û^&}jï6š=£ÿ\0ËÍË‚G÷eÛØc °®Ûà_ÁþÎ_|-à	Ù.Ÿá¿évúF›n91Áaqþ& e˜òÌI<šü”ÿ\0‚˜~Ô2~Ò´úYÜyžð›>—¥ª¶RR­‰§õÑ×ƒÝ?Júîáÿ\0í\\ÁB¢ýÜ=éyö_7ø\\øž<âoìl²U)?ÞÏÝ‡“ë/ûukëeÔðÿ\0ø·Pñ¿‰oµZòkýOSîn®&l¼Ò1Ë1>äý*˜lÕ`ø§«â¿¥ã¢–ˆþG¨å99MÝ½ßrÊÈVž­“òðjºIšûþ	Ùÿ\0×›öƒh<eãhn¬ün´µÅ6¶ÀóÏUƒŒ-ÑHå‡m›á²ì3Åb¥h¯½¾ÉuðïCÒÉrfmŠŽÉîú%Õ·Ñ/ø\nïCÆÿ\0f¿ØçÇ¿µ6§åøoIeÓ\"p—\ZµÙ0ØÛžÿ\0>2ì?º˜dp5÷Á/ø#€|k\rÇŒ¯µê\0ñ+µ’Ÿ@¨|ÆÇ©|îŽ•õ–‡¡é~ðÝ½†Ÿke¤é:l;!‚X`¶{\00\nñßŠ_·‡áId´ðý¹×®Óå3îòíPû7Wü\0_Ì¼}ãu<¾“­Ä,-\'²Oß—¥½æÿ\0Â’]{ŸÓ\\\'á]†IÔ§õŠ½\\—º½\"ô·ø®ÿ\0#»ðoìÓð÷áí²Ç¢ø\'Âú~Ññé±[êåK7âMuðiûqö<zy+þñ‡‰l_xŠVòõHtØ‰ÈŠÎÝøüz¹áûAxÜI»þk9Ïü|¶?.•üÃúNdŽ«å¥Z§÷Ÿ*¿¥æßßcö?Îœ9aÉÙ/òGØ~1ý™~ü@€Ç¬x\'Â÷Ç6Ê½>ìŠ/AÐŽ•ó×ÆŸø#Ï¼]m5Çƒu\rCÂwØÊA+µí“Bù‹Ÿ]çÝ=+Ã¶O<;2ùº”\Z¤+ÿ\0,¯-Õ³ÿ\0]­ú×µü*ý·tMž»	Ð/…³ù–®ßÀ)ÿ\0Þ¯¼à¿¤†QŠ­\Z8|\\ðó{F®‘oÖò‡¥Úo¡óÙï‡X\\TÖðÐ¨»¥ï/š´¾ãó+ö†ý“<qû0ëßÄÚS-ŒÏ²ÛR¶&[+£ÉÂÉµ¸?+…lã×›+`ñÖ¿v¼Iá­+â†n4ÝRÎÏVÒu(¶MÈ%†t<ôè{~„Wæoíýÿ\0ñ¸ýg“Å>ŽêûÁ3?ï£bd›FbxW=ZpÏ#…bN¿°¸O©f2X\\bQ¨öká—ù7ÛgÓ±üÑÆÞÕË!,n\\Üè­ZW}7Šïºë}YòÚË»¯õ lê*¾xõ§$„:WèÖ?%7<ãMKÀ)Óõ­ò[KLn-®#?4n§#ê;x F\r~ÁþÉß´eí=ðrÇÄVÂ8/—ýR´VÏÙ.T\rËë´ä2“ü,;ƒ_Œ‹ cèkè¯ø&¿í#\'À¯ÚÏM¼—o‡ü\\é§^NØe\'Mí‡m¤žÈÇ°¯†ã®Žau©¯ÞÓM¯5Ö?ªóõgé\ZqT²¬Éa«?ÜÖi>Ê[F^]Ÿ“»Ù)ÿ\0ÁâßðJØ4©´¿Ú£ÁºjD·RÁ¡øö(\0È@ŽËQos…¶sëöl–5ø_Þ\'í-û>øsö®ýŸüaðßÅÖ¿kðç´©ô›ä}T*$Cü2#aÕº«*ž¢¿‡Ú‹öz×¿dßÚ3ÆßüM—®xY¹Ñî›aUœÃ!U•þ	+©î®§½;Õ‡EhxOÂ:·|Ie£hz^¡­k\Z”¢Kg¹¹ºôHã@Y˜ú\0M~ž~ÁŸðiOí\'ûU‹-_âzÀÿ\0\nÜl¾¼†çZ–6ç)§ÆÁ‘‡B·2@Àö4ùg_V~Â?ðE/ÚGþ\n+=­ÏÃ¿‡:”~¸aŸë`éš*)þ%žA™ñÝmÖV-Iß°_üCû-~Ã&ËTÿ\0ÂÐñ…¨u¿¬z‚Ç\']ÐÚmÑá¹V1´‹ûÂy? Ä¶ñ,qªÇ`*ªŒ €?ÿ\0`ßø33áOÂ¤³Ö>=ø·Rø¡¬(&…£¼šV‰wV•HºŸáƒ@9 ¡ë_®Ÿ?gŸ~ÌžƒÂÿ\0üá¿øzÜîK\rOŽÎÝÝ•\0Üç»6X÷&»*(\0¢Š(æŸø,‡ÆÓû<Á+þ>xª9Z«_êVr©ÁŠæî#i¤³¡ü+ø™¯ëþëøš|ÿ\0hñ•ælÿ\0„ÛÅ\Z>ŠG÷öNo±×þœ³ß§â?“Ú\0(¢Š\0(¢Š\0+ö³þ²ýaø‹ûZ|DøÍ©Yù¶ÿ\0\r´xô}\"G_•oõ\râIÿ\0y-¡• º¢¿ëúÀÿ\0ƒFþÇðƒþ÷¡x…­Úß‰ž\"ÔüA3:ì‘Íöºÿ\0Û=Ã±d}ì´?à¡Ûö{ý“üQ¬ÛÌ`Õ/¡þÊÓXcp¸Ÿ(}Ñ7È?ë~%zýÿ\0‚ð|Rf½ð‚¡“j*O­ÝÇýâO“~¸ð*üòW+_ÐÞeË”ªí{Õ[%¢_ƒ3ù—ÅlÑâ³Ÿ«\'îÑI|ß¼ßâ“ô,¬”àj?±§n+_¡—¸ŸCÿ\0Á:¿dý­¾6,:Œr	xt%æ³\"±_8|»`G ÈU²FUr8ÏìgüKüáÐÚéº^—nª¨ŽhQp\0…UQ€@+Åà›ß³ü³ïì¥áëY Xõ~1¬êlWæÌ¡‘§—–„tÊ±ïX?·WÆŽKØÍ´[D©ëžcˆÿ\0èd{¥øÝâe,º…|Î®´èû”ãŠoEÿ\0=[éä^x_Á«‚§JÖ«U)MõKt¿íÔíþ&ûœíûN_ü\\ÔfÓôù$³ðä.Dq©*÷˜èò{wÐwÉäy=WùkÄCÎñ³Ì3\ZŽu%÷%ÑEtK¢_Ïè|>\Z\nj%d‚Š(¯Ü(¢Š\0õïÙ»ö ½øW¨A¥êÒÉwá¹œ)\r–{ÿ\0\ZwÛÜ¯â9È?`^Yéþ4ðäO®¥¥ê–å7Q$70ºà‚:2²ŸÄ\Züá¯§¿aoŒR_Û\\xBþRÍj†çNf?ÁŸž/ÀÀzìRxâ~\"ž*7™MÊþ›Ö2_bÿ\0ÊþÏg¢Ñ«|—e0”&šÿ\0î»ÿ\0™ð7íÛû*Íû(üh—OµÉá­`5æ3’ÇËÈßí Ô©F?{âë cèÕúãÿ\0&ø	ÇOÙ{Yhaó5¯©Ö4öQó-O›îwE¼c»=…~B¤ßÞ¯õC‚séf™r•Wz÷eçÙüÖþiŸÂž\"pÌr|ÕÆ‚µ*‹š>]ãò{y4\\Ï¯çRG;FÀç§BJ«Ø¢¤FÏÝü«ëÏƒ?g?cãßìÝáŸM\'™¨5¿Ù/ý~Ñ	òÝûÛCãÑÅ|1ÿ\0\0ÿ\0ƒc~ÁFà¢w¿\Z¼cã\rkCðþ©¤ØÛêz…kzµõ¸hÌò]É¼\"Ú2«s°éÁ>ÿ\0Sø×\Z<#+¶³Ã«[.zùŠb—òòáÿ\0¾«îºþ\\âŒµ`sZØx¯u;¯Ij¾äìgpnló,›‹›¼œlÿ\0Åu¿›Wùž#ûÁ7¾þÀ>þÏøKðßÃ~y#\\j1Açê—ª;My)iäÉ\nÎTpkÛ¨¢¾|ú`¢Š(\0¢Š(\0¢Š(ñ‡þhñoØÿ\0`o„ú™í/ˆÿ\0ËÙ÷¼:ñ3»¶>ÓŒwÝí_Í\rG¿ð{Ïüš×Àïû\Z¯¿ôWó…@Q@Q@mðFÿ\0/Ã_ø%ìë¥,\rlÿ\0ð¯tkÙ¢dòÚ9n-#¸2àa·ÊÙÈÎsžkø—¯î¿ö)ÿ\0“6øKÿ\0bfÿ\0¤0Ðæ7ü;ÅgÄ?·³fK é¶6 Á¡~3šùl>+Ý?à§r4Ÿ·_Äc¸ý¢Ûœÿ\0Óœàêõý[Ã”•<«\rÿ\0$?¦ÏãÞ*›«œb§/ùù?ÂM/Ášîf‡ãâÇíà¿\rI–ßYÖmm®ýÉ•|Óø cøW\r{÷üúîÿ\0nÿ\0‡©\"«¨º¸pÏ+i;øá]9µyPÀÖ­\rã	?¹6pä¸X×Ìpô\'´§ý’?k’Ú¶ØãŒ}_ÿ\0<]\'<u«kÝ¨\\¼Êð!?*þøWßçko‡šôˆv¼zuÃ)ÇB\"lWçm“_IÌÂ¢X\n~ëç›ók–+îN_yþ€p­5ûÊtAEWòqöEPEP]Â?€~$hºÂ¶Å²»FúÆNÙâ…‡ã\\ýÕÆUÂbiâ¨»Nœ”“ìâÓ_Š&¤âá-ž‡éMÅ¼wpI¨²G\"”ta•`x C_…¼~|^ñO‡B¿—¡j×V)»9)¬ŠyõP{ƒšýÈð|íuá-.VÝºKH²0rPkñÏþ\nlšwí©ñ\n8Ú›R~ëîåâG?ŽXçß5þÔxGŒsÄU‚ÚpRûžŸúQüsãf?QÃ×{Æn?)E·ÿ\0¤£Èc—û¦¥I³íU\rþÉý)âB¿{ó¯ÜÏçŸYÁ ¼Q&û[0ÌXÑ®­˜ûLsý×ßÞ¿Skñÿ\0þ	säþÜÞ;^@Zùp£$fÂägè3“ì\r~ÀWà\'ÒQÍã%ö Ÿã%úÔÖsÈ¥öjIä±¨QEùÉú°QE\0QE\0QEøÿ\0»i³Ëû$|¼XÉ¶·ñ}Ô2>xW{&*=y¿ýó_ÍÝQ_ðyç€ÛÄßðJÿ\0kEºO\rüD°¸–OîC-•ü,:÷‘áçžƒÖ¿—Z\0(¢Š\0(¢Š\0+û˜ÿ\0‚vxÂßâü÷àf½j¾³ðÿ\0A½Ž7 ´bM:ÚØã#88î\rõýÿ\0Á¹¿ãøáÿ\0bø¨+îŸDÑåðìè[-X\\Íh òq˜â€ôqÀé@ÿ\0ÁVôGÐo_)ÝåÜ5•Ädÿ\0µ”É·Â¾x\r_iÁt¼Þý§t\rz5o#Ä\Z#Þh%ul}#xÉ¯ŠR@õýMÂ¸…[(ÃT_ÉóJÏñGòOa]ëüòøæ_ƒ&\rŠöø\'Ÿ‹£ðoí¯ðÞòV²kÙdŽ3p­n?YEx˜lUÿ\0x‚ëÂÞ ±Õ,dòotÛˆî­ßû’#Sø\rzØì:ÄáªaßÚ‹_z±âåøªâéb’Q—ÜÓ?¡i_ÛÞ¿±8Åå¼ú2•þµùÇqo%¥ÄÈ¥$‰Š:žªGWè/Áÿ\0‰v?>xÅZk)²ñ„W±€Û¼½ê!ÿ\0i[*}\nšùö´øpß¾0ßI{lu¢oíÈ.XþñÏ…kü«úMpõià°Ù’ðe(OËžÖoÉJ6õ’?¾8K	9B.êI5çý\'sÌh¢Šþ7>à(¢Š\0(¢Š\0*m6Â][P·µwMs\"Å\Zÿ\0y˜àÌÔ5êß±ßÃ‡ñÏÅû[É#Ýc âöf#0ª_®ï›è†½ÎÉ*ç®,¢µ«5Eyú%vü‘ÏŠÄ*4eV]öv—`º^™oj§rÛÄ±)õ\n\0Ê¿?mŸCâÿ\0Úãâ%äXòÿ\0·nmÑ€á„NbÏS×fsïÛ¥~Èürø£kðSà÷‰<YxWÉÐtùnÂ±Ç› _ÝÇõw*£Ý…~j\Z”Ú¾¡=ÕÌ5ÅÔ,²7YŽI?Rs_í„¸VÄ¥h¤ ¿7÷Y}çñ¯y„}ŽÕ·7òV_}ßÜ=d+þÐ©#—#ÈÕUr´åpßìµ~Ô=Øúcþ	E£6«ûoø^xÙ•tÛ{ë‡ÎA´–.½¹z×ë¥~fÿ\0Á|Ú·Æÿ\0x×t:6Ž–`ÿ\0vK‰•”ÿ\0ß6òÆ¿L«ùçÄÌB©œò/±¯Î_ûqýMáÒáõQ¯âNRü£ÿ\0¶…Q_žŸ¨Q@Q@Q@	ÁË	_âÿ\0üOãe­¼>mæ‡ge¯BB–1‹Kûyælú`³ô\0çµýã~ÒŸíh¿Ù×ÇßïYRÏÇ>Ô<?37DK«i \'¿A&x¯á_Ðo<-®Þéz¼–z†›q%­Ì<£taê~”NŠ( Š( ¿£ÿ\0ø2söÅ³\'Å„WWL×Þ×áñ%ŒR6ÑoaÈ©þÊMi¸ŽÆàõüàWÞðmçí¹ì?ÿ\0\\ð¡©Ý_øø·‚õ§fÚ‘Çzñˆ$cÐ*]¥³3<u èóþðe¼wû0iþ*·Œ=×‚u’C·$ZÜí†LÛO³Ÿ¢ŸJü“¯èƒâgÃý?â·ÃÍoÃ:´m&›¯YKcrïl‘\n’¾Œ3{\r~\0ü`ø_ª|ø¥¯xKZËÔ´Ç´›&ÓòÈ¿ìº•e=Ã\nýÓÂüÕUÁÏ\'ïSw_á—ù;ßÕ‚ø­“ºxÈf0^íEÊÿ\0Å¾øíþ`,¥zÔŠÛ…@iAÁâ¿Q?$q?Kÿ\0àˆŸµ„wº-ÿ\0Â]bëmÕ£I©hFÿ\0Yù§·êÙ”¤<‡¢×Ú_´\'Á˜~4øK c‹R´&{›¢IŽTŸî°àúpyÅ~\nøÇZ§Ã¯iºî‹y6Ÿ«i7	uisù¢‘NAô>àðA ‚\r~×~Â·‡ûf|5K„k{iqªë\ZXncnžtYå¡cÐòT§œþzñk€èbéU©VŸ>ºµEÙ¾¾Wz§ÒZèìAø_Æô¡—U•ªÓøûQ]=cµ¿—ÑŸ)ëšç†u{?P·’ÖòÕÌrÅ Ã#ó×¡ÕZû—ãÇìÝ¤ük²ó‹gëP&Ø/Qs¸vIñ/ê;d‘¾%üñ\'Â{ÆWÓäK}ÛRî!æ[Ëé‡3èØ>ÕþYø…á>mÃUåV1up¿f¢W²í4¾»ü/£è¿¦òÜâŽ*)^Óíþ]ÎNŠ(¯ÊÏ\\(¢ºÿ\0†<IñníWIÓäû&í²^ÌvñúüßÄG¢äûWv[•âóDp¸\Zr©R[F)·øtîö]LêU…8óÔv^g;áÏßx·[¶ÓtÛi.ïnÜ$Q åô©\'€\'Šû¯àGÁûƒ‡MFI¯¦>uìëÿ\0-e#œ²½°ÏRjÀ¯ÙßHø%§3Ãþ¬\\&Û‹ç\\:ìAü+ŸÄ÷\'\0ööý¹4ØûáÜ‹[ßøÓVˆ\'M-½¾Ñ(ˆ—ž8.Ãhþ&_î|¯”TXœTTñµU”Vªœ^êû]ý©l’²v»›ñWaéáåV¤¹hÃVß_—ä·oÌù¿þSûTÇ\"iÿ\0\nt{€Ì®š–¼Q¾î`·?ŸšÀúD}kóÕ¬xŸÅz‡¼I¬j×s_êz¤ïuuq)ËÍ#’ÌÇêMS\ršÿ\0E¸%§•àa„†­jßy=ßè¼’?‡¸§=«œf3ÇODôŠí²ý_›e„“5 9ª¡ë¯øð—RøññoAðŽ”?Ó5ËµƒÌÆE¼}d”ÿ\0²ˆ²×­Z´iAÔªí¦Ûì–çGR½XÑ¤¯)4’îÛ²Géïüçáü>ý•¿¶îbhï<a%ð,ï³§î¢¸ÊÈÃ=¤¯ƒàîOø)ÇÄØÂßà„>øãÄ>	ñn¥{yâ]FçH½kvkXQmàŠelÑI$³6ÇÊfÜ¤í#ö7Áž±ð„4½L‡ìúvi•´ÜŠ4 ú s_ÇWüûoÅûzÁSþ#xŸM»žðÌËá?H­¹ÎÈ²4ˆ{¤·\rq2ûL+ù?8Ì%ŽÆÕÅËíÉ¿EÑ|•‘ý¯‘å‘Ë²ú8ýˆ¥êú¿›»>”ý—àñÚwàëÚÚü@Ó|ñoL‰¥½±þÈÔä‚Ík¶úµ»\\“ú5û/Áå¿³Åqkkñ#Ã~:øO¨K>áí†¹¥AÓ¤¶À\\79ÿ\0—QÇä?—Ú+Í=Sûšýšà¡?l[xÛáÅoøÎâDó>Ã§êÑ6¡úÉjÄOü\r{\0°O%¬é$nÑÉGS†R9ÄW×?³ü‹ö´ý‘Í¼>ø×âëí.ßj®™â)—]³ùf‰v$1/´E¡ý Ñ_Î¿ì½ÿ\0¶øÏD[{?ŒŸ<?âøI5?	ßÉ¦Ìª1ók9$cßÆ¹<\08¯Ñ¯Ù{þýŽÿ\0i³[Ü|@¼øk«\\4æÓÂvù®£2Ú(ÿ\0za×ë€Ðê+áÏÅ/üað¼\Zç„|E ø§Eº\0Ã¤_Å}k(#?,‘3)àƒÁ¢€7«øÛÿ\0ƒˆeÆý”?à¯¿´˜­Ì:OŠµ_øKôÒâÔGÚdØ;*\\=Ä`vò¸ãý’Wáü½û¶¿ðëágÇÝ.Õ¤›Ã÷àÝyÒ2Ì-¦/sdìGÝD˜]!\'‚×QŽ	äùá¢Š(\0¢Š(\0¢Š(û\"ÿ\0‚ÿ\0ÁJ­ÿ\0à¦?ðO/øƒP¾[ˆ\rTðß‹âc‰ö×eÑen\").@Û¼Êƒ˜Î8¿ø-×ìw&»£Û|]Ð-ZK­5ËÄ1Æ¹/qÎ?Ø\'cî²_ÏOüwþ\n·{ÿ\0¤ýµì<A©Ms\'Ã?ô\ZYD#]ÄÅy\Z²Û;²4È0dÈþÃ-/4?Šþ†âÞ];_ðçˆ¬D±K-Å®¡k4yÊ¼nG5{qW+ÆÃK¦ëº{¯òó³<|û\'¥š`§ƒ«ö¶}šÙÿ\0[«£ùÕIsR+ú×ÐŸðQïØOPýŒþ*4ÖÍqà]~V}ì¶ónzµ¬‡®ô	ûëƒ’CóšMŠþ¤Ëñô1¸xâpîñ’ºÿ\0\'æ¶k¹ü§™eõð8™a11´¢ìÿ\0ÍwOtûÈ­ï†µïƒž6±ñ†uK#YÓ_|0™{Aá”‚AR AÎ¤™éOŸjÞ¥8Î.WOFž©¯3ŽœçNJ¥6ÓZ¦´iù¬¿±¿üOÂ_-¬ô?ˆÍkàï6#ÄíÒ¯[×y\'ÈcÏvz>HQö„ÚkúZIÛÞÙ]Æ]H’)Ž<†~¿œºôO‚¿µ—ÄoÙÞOø£ü]¬höû÷›EKhí×&FO¹\\×å™ç†zòu2ùò7öe¬~Ot¼¬þGëYŠ•èÅRÌáÎ—Ú”¾kDß×Ìý¶ñ7ì³àOÈÒM [[LßÇhÍoø\n¿˜®|~Ã^>fÝcoüóûXÛÿ\0 çõ¯€<ÿ\0Ðø£¡Á:Î‡áyW¬¦Þ[[‡ú”“gä‚»ÿ\0ó×~Ç·þ¾“ö¿û^M™õÛågÛ¿\Züg0ú=á«Õç«–P›î£]›ùŸ£aü\\Êù?Þg&¥ú&¿ï~ËÞð¤‹$µ¸•NwÝ³\\~ŽJþB»{ËËML’{‰mì¬íc,òHÂ8¡AÜ“€ ÀWå?¿à¹?<Aèú/„taòÊ-¥¹¸Cì^MŸš\Zù·ãíMñöŸwŒ<[¬kq¹m¤—ËµCê° XÔû…ö;à¬°qötiÒÃCª„Uþè¤ŸÍžmâþ^“tëK¥ô_{»ÿ\0ÉOÑÛþá†–×zÃV¶ñ_ˆ0c:§ÞÓ,ªŸùnÃ¶ß“¡ÜØ+_™><øƒ­|Pñ…ö¿â\rJëVÖ5)<Û›«†ÜòƒØ\0\0@\0\0\0\0\0+6iÁ±_¶d<3‚Êiòá£ï=äõ“ÿ\0%ä´?âN*Ì3ªœØ©Z+h­\"¿Íù¿•–„Ë(n¼SòV 4ä¥}\rÏ•q,,™¯ÓOø#Oì‘\'‚¼#sñC\\¶hõBm´XäR\Z<üó`÷•”?Ü\\‚D•ò×ü‹ö\Zºý­~#®©«A4>ðüêÚŒÙÛöùZ¡ëóp\\ºÃ2×ëŸ‹ü]áÿ\0‚ÿ\05-sZ¾Óü;áéò^ÞÝÜ8†×M´‚2Îìz,hŠO ¿$ñ#Š#\no)Ã?z_]Hú½ß–OÚ<+àùNªÎ±q÷cü4ú½œ½ËÏ^ˆøŸþ-ÿ\0‚•Eÿ\0äÿ\0‚uøŠ}#P[_ˆŸ’O\rxY±42J˜¸¼^>Ï3<y?z¿zûþƒÿ\0KÔ¿à«_¶æ­ã\Zê×À>\r£ø7N›åk{l™Ý{Mpù•Ç%ADËÁ¯Žkñ3÷à¢Š(\0¢Š(\0¢Šì¿g_ºßí7ñëÁ¿ü7¯xÛYµÑlARU$žUŒ;c¢.íÌ{*“Ú€?¨ÿ\0ø4£öX“ö{ÿ\0‚Jé>$¾¶ò5o‹\ZÝß‰ŸpýâÚ©[Ke?ì”·2¯µÇ¾_¢>hÿ\0³ÿ\0ÁŸ	øÃÐýŸAðfi¢iÑž©om\nC}NÔ=Îh ¢¼oþ\nû\"i·ŸìWñ#áªÐÅ4im-.%Mëez¸–ÒàŽþUÄpÉŽ3³¯d¢€?ŸxVø]ã½kÃ:õŒÚn»áÛùôÍFÎ\\y–·0HÑËcŒ««Žâ²kö;þÿ\0‚nÃ>~ÖÚOÇ¯Xy>ø½þ­yIˆìõØ#ù‰À~Ó‰\0ä´Ü±ë_Ž4\0QE\0QE\0WîGü\ZÛÿ\0ë·ø\'w¦þÍ?uÏ\'Âº…È‡ÀÚõì€G¢Ï!9Ó§‘Ëo#œÄÇý[¹Bv2ù†ôP÷­ñƒá‡þ;ü=Ô|/â:SGÔãÙ,2pTÿ\0£uWS‚¬9WâßíÛÿ\0ûñGì[ã’DŸWð^¡1]3YDàg‘øâ9€üW£óø7Óþ{‡áþ“¢|ý¥µÆ]ÕRËÃ^<½·Øac´Ô\\óåÂ\\Ÿ¸\0|£Ì_èÄÐ¾,x*kBÛN×¼?­[ñÈâÚòî¬¤`‚=ˆìkë8_‹19=]=êRø£ú®ÏóÙôkä¸«„pÙÕ{Ý«†_£î¿-×[ÿ\09Ë&\rJ“z×èí¯ÿ\0NÕ|1syâ/„Eõm,æY|=<¿évÝÈ·‘Ž%QÙ‡\0	¯€õÝPð¦³s§j–Wšn¡fæ+‹[¨Z qÕ]¤zšþ„ÉóÜgKÚá\'~ëªõ_ÒìÏçLë ÇeU}–2ìÖ±~ôÝu@¯R+f©¤¸©’]ÕëØñI…H“c­B²SÈ¤K‰`6áÅ85VŠzMýê^„8–§+Ô Õÿ\0\røwPñ†¹k¦i67šž¥zâ;{[HZi§oîª(%ÐQ)$¯-ˆTÜŸ,UÛ ¾ˆý„¿àŸÞ\"ý°¼Qäëq£øÆ`·ú©L4øë\r¾FNÄò©œœœ+{×ìaÿ\0`¾Õ¥´ñÅÆ66,°øvÚ_ßMÎÒd_¸¸þ%Žyd ƒú)a§è|\Z°ÛÅ¦xÃú%±!T%­¥Œ	\'²¢(“Àšü³Š¼B£B2Âåoš{9î£éÝùì¼ÏÖ¸?Ã*µå^n¹aº†Ò—ø¿•ynüŠÿ\0\r¾è_|§øwÃº}¾•¢éQyPAáGRÄõf\'$±É$’I&¿œßø:þÖ¿´§‰5Ù×àî¹æ|=Ñn<¿ëvRƒ‰®ã ‹HdRCZDÃæ=%‘F>DMÿ\0ø83þu_šn½ð?öqÕ¦‹Âsï±ñ\'í¤häÖ—æYm,N[cÑ§2‚U1Ý\'á¥~R¤§\'9»·«ovÏß©ÓŒ\"¡d´Il’è‚Š(©((¢Š\0(¢Š\0+öcþÚý›ã/íƒâOŽÚÕ‘}á5™Óôg‘Éõ‹ÈÙ)ÆÖòmL¥‡Uk˜Wã¿…|-©xçÅ\Zn‰£Ù\\jZ¾±u¥ºšêy\\$q¢ŽK30\0¤ŠþÔ¿à?ðOû?ø&‡ìà_…Ê-ä×ímÎ§âk¨Nå¼Õ®0÷,È‡l(ØÇyÍ\0}5EPEP‡ÿ\0ÁFÿ\0aßÁF?co|%ñ&Èañ%‘:uñMÍ¤êüö·KŒÝÊ²‚7¡t\'kø¡ø÷ð7Äß³7ÆŸ|?ñ–›&“âêSiZ•«r#š&*J·FFÀeqÃ++‚\ryÕø›ÿ\0lÁ›ã¿Ã6ý¦~i^o‹üd!ñ¥´Y“VÒ£%îe¥µ9<˜9$\0 Í­Q@Q@Q@~Á?àä_‹ðLCcàÿ\0¥ÇÄßƒÑ•tË­—šnùžÂrÐ\'È|Æq…ò‹3×ç\rý¼þÀßðT‚?ðR¿ÿ\0l|)ñ¥Ž­yoË¨hw$[k:NxÄö¬w…Î@‘wFÄ®ÕÛþÐ_±ïÃŸÚM0øËÃ\ZÈP±j¯‘}\0Î˜}£û¤•=Á¯áŸÀ_uï…^0ÓüEá}sXðßˆ4™|ë-OK¼’ÎòÎL¾9c*èØ$eH<šýeýƒÿ\0àñŽß\0!±Ñ~0èšOÆm‘¶ ÌºV»yÁ>tha˜ªôv#æ—$µm‡ÄÕ¡QU¡\'-švz1ÄaéW¦éWŠ”^é«§ògêoÇOø eìÜ]|9ñ•¼ðòÑéúôeG ¸…HcèJ=OqòçÄ¯ø&ŸÇ/…rÉöß‡ºÖ¥gm!WRWÞÎûÊÒ¾¿ý”?àéÙöœµµ‡Qñ½÷ÂÝrpwiþ2±k4Bú\\fK\\£tªÄ• }Ýð·ã‚þ9h1jž\nñw…üa¥Ï–;½TƒP‚D=<LÊAõÍ}æ_âVm‡J5¹j/5g÷«~)Ÿ˜øc“â•jOû®ëîwû“Góïâ/ëÞ•£Ö4][Itm¬·–r@Êy!€çƒù\ZÍI3_ÒHxoO[Ÿ8XY	ƒoy»vsœã9Ï9¯¡§âÓ·¿…×Êý©óµ<!ÿ\0wŠÓÎÿ\0Û‘üóxcÀZ÷%Xô}WÕ¸U²³’rzˆr?:ö†¿ðMÿ\0fì¾Ö4ÈY°ÓëtÕŒx¬Å\\÷TšýÆ#\"¹?Šÿ\0¼\rðC“TñÇŒ¼+àÝ62=Ö·«A§Âª:’Ò²ŒW+ÅlT—û=ÇÕ¹~\\§^Â<]ñå/D£ùóüÿ\0‚MçCuñÆQ¬|3éÚd–ö7¨Ç¸cÞ¾ßøû)|?ýš´¿³ø7Ã:~•+.Éo6ù·—®gË°Ï;s´v¾$ý«ÿ\0àê_Ùögµ¸‡Iñ†§ñS[‡…°ð…ƒ\\DIàw1ŽÛo©Iü\'€$¿o?ø<ãïí\ræ‹ð—KÒþ\nøv}Ñý®ÙÆ§¯Lœøù‘pä`þê êzHz×ÄfÜQ™æ:bª·åZGî[üîÏ»Ê8W+Ë=ì%$¥üÏY}ïo•‘û÷ÿ\0ÿ\0‚°|ÿ\0‚ex!µOŠ>0µ´ÕfˆÉ§ørÀ­Öµªÿ\0×+pAž<É\nFÁÀ¯æþÿ\0|\\ÿ\0‚£ÞÞx_Mi¾| ó\nÅá­>ä´ú²6¾¡8ÇœxÈ‰BÄ¼pì¾aøÆ¾7Ö¾$øªû^ñ±ªkúæ©)žóQÔ®¤ºº»õy%—v>¬I¬ºð \n(¢€\n(¢€\n(¢€\n(¯tÿ\0‚pþÀþ0ÿ\0‚•~×øSàØÚ)õ‰|ýORhËÁ¢iñn.åè0ŠpªHß#F€åÅ\0~™Á¡?ðJy>:||»ý¤|e¥‰<!ðÞv³ð¬w1å5-h¨Ýp ðÉkd®–2§t,ô­\\ì½û5xGö=ýŸü)ðÏÀºlzW…¼`–PŒo|ròÈp7K#–‘ÜòÎìOZï¨\0¢Š(\0¢Š(\0¦\\[Çyo$3F’Å*”tuÜ®§‚=Aô§Ñ@Ê_ü™ÿ\07ºÿ\0‚q|j›âwÃÍ/?üu|DÀ¤	ß¾\\Ù8íášè\01œS\'å½z_~øWö’øGâøãD³ñ„üQfö:žt—q{‚YH®¤2²«)?‘?ø-÷ü7Æ_ðHÿ\0ŽLñ­çˆ~ø¢êOøEüI³;:·Ø.ÈK¤PyáfU.€bDŒáš(¢€\n(¢€\n(¢€\n(¢€\nµ¢k·ÞÕa¾Óo.´ûësº+‹iZ)b8ÆU”‚8$p{ÕZ(Û¼9ÿ\02ý¤<f-ôÚãv•n¹ÄV~:Õ A““ÂÎROÔ× ¿üGöÀ’Ý¢?´OÅ\r¬»r5v\rŽŸ{®}óšùBŠ\0öÏÿ\0ÁJÿ\0hßZ}kã÷ÆÍ^Ý†w¾8Ôî÷èó‘^;­ë·Þ&Õf¾Ô¯.µëƒº[‹™ZYe8ÆY˜’x\0r{UZ(\0¢Š(\0¢Š(\0¢Š(\0¢Š(\0¢Šµ¡è—¾&Ö¬ôÝ6ÎëPÔu	ÒÚÖÖÚ&šk™]‚¤hŠ33€I$@\Z¾ë¿<{£ø_Ã\ZMö»â/^E§éº}œF[‹Û‰X$q¢ŽK3+úùÿ\0‚ÿ\0Áôø$ŸìÁö]Kìz·Å¤W~.Õ¡£‰”…»c>D;ˆÝÖG.ç\0¢\'ƒÁ¸_ðoõ¯üËÁ–¿>+é°Ý|pñ\r›-¥Œ»eÁV’bCÈ7’/ÊÈ¬bCƒ#IúÅ@Q@Q@Q@Q@pÿ\0´ìáà¯Úßà®¿ð÷â‡ì¼MáÛ›kë•á‡UtaóG\"0’)ŒªÊA\0×qE\0!?ð[Ÿø W¿à“þ;¸ñŽº—Œ¾	ê—t¿ˆ·M¥– -® Ž\\« 9¸+µ·F¿Ÿ5ýóxóÀz\'Å/êžñ&“§ëÚ¹k%–¡§_À³Û^Àà«Ç\"0*ÊA ‚+ùÑÿ\0‚×Á§þ ø%qªüLý˜¬ux;/u¨ø#q¸Õ´Uå‹Y–»„r<£™×;$ â=%Õ¬¶72C4rC4,RHÝJ²08 ƒÈ ñƒQÐEPEPEPEPEPEPEPEPEï°ükâïü¿âúx?áO†fÕ¤‘µMZà˜4­\n&$	n®0B!\0icG#ä?þkß<s¤øcÂú>¥âk·)g§éº}»\\]^Ìçq¨,ÌO`+ú€ÿ\0ƒÿ\0àÜ-þ	å¦é¿þ0Zé¾!øáu›cj®.,|Ž¸1Ä~ì·„a•NR\"Fé$÷ïø#·üá_üOÁ_o±Hüeñ[T·jþ0½·*)uµ”|ýšß=@%ä8.Ì>ä Š( Š( Š( Š( Š( Š( Š( Ï_ø+oü•ð_þ\n~·Þ(³~üZ•Ké6ªÐêÛûBÛ*·Ç˜¬’—.Ê¡ógÿ\0\Zÿ\0‚7|zÿ\0‚_ø–Hþ$xFi¼/$æi¯4Kþ~_ß\0.Ý£cà¤×ö­TüEáÝ?Åúæ—«XÙêšf¡[ÝYÝÀ³As2:0*ÊAÁE\0ôWõ%ÿ\0ÿ\0ƒE~þÔ·Þ!øGy7Á\\•­,mþ×áÛ§<óhYZß<ä:Æ£Ÿ)~#þÜðoGíSû	Myw®|9¾ñ—…í9ñƒƒë\"5É2Hˆ¢x’ÓD‹îhâZ(e*Ø#pAíE\0QE\0QE\0QE\0QE\0Q]ÇÀ_ÙŸâíMã4ðïÃx§Ç:ÓÝi¢i²ÞÉ\'äòÔˆÓƒ—r\0I @=hxOÂ:·¼Mc¢èZ^¡­k\Z¤Ëmgaal÷7Wr±ÂÇh;ÀUšý“ý‚àÍ‹.,õŸž*Ó~hlÁ¤Ðô‰#ÕuÙ—º´ŠM­¾xÃœðAAÅ~ä~À¿ðI_€¿ðM_­·ÂÏéú~±$>Mßˆïÿ\0Óu»ñ»}ÓÊ­€Lql<„ø…ÿ\0¦ÿ\0ƒA|sñ¦ãMñ—í-wuð÷Â¶â?	XJ¯j+Ô-ÄŸ4vhÜeFù°YJÂØaý	~Í¿²ÿ\0Ãÿ\0Øÿ\0á6ào†~Ñüá]-qŽŸÐÍÀ2HìKË+`n’FgcÉbk¼¢€\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€\n(¢€>qý®à‘_³oíÏ%ÅÏÄÏ„>×5{œ™5›ks¦êÎOv»¶1Ìø<€îW9ã“ŸÍ¿ÚOþøMã;«‹¿…¼gàI$-\"Øëv0ëÖˆyÄhÊÖòªôv‘‡_›¥~ÙQ@ËOÇø3wöªøp&›Âš§Ã?ˆ–ªØ†+\rbK×¬—QGýÍ_.|Pÿ\0ƒÿ\0l¿„>gö·ì÷ãË¿+ïbE¹ž½>Å$ÛºvÏoQ_ÙÍü/ø»þ	ÿ\0ñçáûºëßþ.h*8¿ð~£lU˜nPwÂ0Häâ¹sà<0ñ®¥àŸií0&1s£ÜB\\¸Üƒ8Èéë_Þuü\"éŸ²GÅmnx#³øcñ\nòK¢)‡/$iIé´ùÏlW¢xþ	\'ûQüIšÑÿ\0gÜ6Ôž_ßÛÛ“¿ë¤‰c=rÜsž•ý»Q@ÈoÂø5³öÖø±$-7ÂË_	YM‚.µÿ\0X[ÈæŠ9dœc=ãöê¯°¿gßø2/â³43|Rø×áÂi-|/¥ÜjÒH¼|¾mÇÙ‚7^v8t5ýQ@š²ßü\Zsû\"~ÎÓÃy®xwÄŸµHpÂoj…í‘ûâÚÙa‰—ý™VLz“Í~†|+ø=á/ž·ðÿ\0‚|/áïh6¿êtíN†ÂÖ.ß,Q*¨éé]\0QE\0QE\0QE\0QE\0QE\0QEÿÙ',1,NULL,'2019-12-02 04:05:53',NULL,NULL,'','maxkey','',0,NULL,NULL,NULL,'0',NULL,0,0,1,1,'org.maxkey.authz.token.endpoint.adapter.TokenBasedJWTAdapter',NULL,NULL);
/*!40000 ALTER TABLE `apps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apps_cas_details`
--

DROP TABLE IF EXISTS `apps_cas_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
INSERT INTO `apps_saml_v20_details` VALUES ('525d261fa3b04d19af0debabbd5a1e2d','VeriSign Class 3 International Server CA - G3','CN=proxy.salesforce.com, OU=Applications, O=\"Salesforce.com, Inc.\", L=San Francisco, ST=California, C=US','17 Oct 2017 23:59:59 GMT','þíþí\0\0\0\0\0\0\0\0\0\0 https://sso.connsec.com/sec/saml\0\0CDF­Ð\0X.509\0\0d0‚`0‚H C‚±7±7hÖºpBn g0\r	*†H†÷\r\00¼10	UUS10U\nVeriSign, Inc.10UVeriSign Trust Network1;09U2Terms of use at https://www.verisign.com/rpa (c)101604U-VeriSign Class 3 International Server CA - G30\r131018000000Z\r171017235959Z010	UUS10U\nCalifornia10U\rSan Francisco10U\nSalesforce.com, Inc.10UApplications10Uproxy.salesforce.com0‚\"0\r	*†H†÷\r\0‚\00‚\n‚\0²mKÿ-&cÙü\"ŽÏ÷IìÅëƒ==±`­Ó…uìjôØùâDG¾Ïë)ßY°$·\Z[UØ`Eæ1_;©µ\nþe@Âñ\\1ûVC]Ãç™æ¡BUpgT×€‰@:<íÆx\0…b¹%dQô°^1ò“2x2Ð\"ÉÒ\nu8Ù‘©¢\Z­dß¯l4HÑM>)ö´#zýèz!¾Ý²Dù\0Á¸˜RhÄB¬ˆ”!Ÿ)ªz…`,Ä›ÝDÒðŸQÕ‹é\"°-ý4ÕÁÌÕ~»íÇP·AZ¡¥gÞïë¤Ð	œpih‘5\rö—ßÚéð5iJO^ÊsF§RHÿ©ˆ¿q\0£‚‡0‚ƒ0U0‚proxy.salesforce.com0	U0\00Uÿ 0(U%!0++	`†H†øB0CU <0:08\n`†H†øE60*0(+https://www.verisign.com/cps0U#0€×›|Ø\" ÷Ý­_Î)›XÃ¼F\0µ0AU:0806 4 2†0http://SVRIntl-G3-crl.verisign.com/SVRIntlG3.crl0r+f0d0$+0†http://ocsp.verisign.com0<+0†0http://SVRIntl-G3-aia.verisign.com/SVRIntlG3.cer0\r	*†H†÷\r\0‚\02Âøy¹…·D~¦^éÑZ8£±Êp­W†cÎD…ÂôQ÷îsŠ6*–¸¢íR/…€X(Äé+=/T½6›t!Â¦!g‘²2J+9ÅkË1¾mÎ×V½¹Wà¾iðŸED¦äæ\Zõï!D€gš´	PàÍ±¬®5§eye€î¤¯èœH:1ÑZ¬;l	\n²ÙÐz–yEeií~ô{’:ô¶µü@q=×Åã\nïA	ªK»Á;Å:ö;LƒÿiY3H¥ø.9º]ÉøÔÐÞØ £Jf®ar…þÀ©qŒ6=g•b`­^Ë”åoØ)ÂëS\'RÿÚ@w§\0\0\0\0connsec.com\0\0>‰Ô\r”\0\0\00‚ü0\n+*\0‚èË¯aMéº¡-ÿ5³·ž¿Hfk<ëž“­nH	„Í%ÐŠ{„4º#’bº¨ùzõÄ;“W¿Â\rËŠ£2­?0šìQC#6¢ˆwP@\\ª09CJ.õ*†™pAÊa§ûáÄH/íùCdB\\úO @‚E¹zî=ó\0h%ìb:}ÈÅÃËN©àí«¼R\'˜C‡Á+Æš*ks«éw¿xë .îGÕ	±k[¡w—³n_+…Ù\0•s—†Â=©`wd¶@ÑñÚ‡Hœüte·``ý`â±‡(5\'1Z½Ù4LRhy_ú23y°Ñ¿™!¬ËMä,é&ýCÚzÒb†_£P¡û±B5Ï´Í•%¤ö ý¹ÐP}”³Å•cœš×qŸ‹Hb~Ä¿7M÷êy¥œ’Ð‘`žO•Ž=œY»`/«rïÑë¼:›Pƒ~ƒËž4Voü£·€´´Aˆ7^»ú/ÏKþlbûŽ‹rÀaS‚)™$¯T‹C?\'¤V#ù¯iÑÄ¤Ág”wÍ<e–\nlTÚ®ñºM¯¥›{\0\n¡cl–-‹§ƒ”É\0^:VÂ¦ø#\Z<_ì¾Ó‚Ü•¹O+l^}.¶¼k1S«øO\'=F6}Õ¾³e2¼C©ârƒÊÌØtó²i´÷\ndØ	è GJOö~ZH®Qe¿¦Ï4{Éí¥túmÀu·Ó«`¥FÂY°q­¶”¡t§|k(øé}ò’>‰J¶Rïí‹Åj‚·4æ–ª\n\\7oÏ·€¬=Rù]ÌëlzSÐ¨u¤è~ÚÅ¿€>¥Eó.O\0˜íØá¸EHù‡×ªqZlNt*xÒíút’Å6:?”åâLUº“8üåŒ#gìÑª†;Ö.Ý-R°§>“„2RãjË\"¬ÿ«ßˆÀ–P`×ÇQüzV±71U×»$‰ðRtD‹¬_4k¡ñâÎö,ÑÝoôUüðs·™h&p8k—1Ü¢˜4¹ª—ÑZ8‰5Y‹õÔ1Þ—¦ðÈá!°EßUðOó4/ 0ýÜ¡6$‹ùZ‰hN: È_y­\rç]Mg…ûUÃ`–Cs\rÖÛëÒù8C—œÕlºWðÇ ø“8¾X?uhu=R$ˆNÓA—z–‰Ø¯e¬›{˜ÎÕ\Z12ãiïâ:;`ÊZ-\0²)z\'Ôì6]¦`Ë	¾µƒaAàó,(LºëÆqbpÃø°Ú\"‰÷ÔEÿj%YÈ¸Ã8É­DZåÝš©´O6D|ú<MWô€¬Tþ\Z]5¹úØz>x°þÄ¿ÓìˆfÐiíÒvÖNÃÅmÒ¸âK8WîqIÖ$Î	ÉF\\ŸðLìý2,j²ÜÐ\"Ð{Að–0ˆy…öPa=kVß~9ÅW>´oÒâµ‹”®ÄU^üœke‹ET3|Ÿ·n`çåï8!Q™jªKO´s{\r=l¢ø§K/ÍJÂ.—åä,ìÇÏÜ ?Qã\"Z÷h¶9}B,€ØÕ ÚÜ:ÙÈsu‰Œu˜©»ýs	)$†fïï…?¤Ü¨¥-¼gløk¥Í^ŸÑ¼	Oå±°›ÔŽ¹lñÕIÐ$ïl°¼Kcø¾ï0›±ÔÖ?\"¬,Ï†8›²è.ÎÕŽIuxtš©?nm]®œ8™Jìº2$Ã\0\0\0\0X.509\0\00‚\Z0‚ Q‹¸û0\r	*†H†÷\r\00O10	UCN10	USH10	USH10U\nconnsec10Uconnsec.com0\r130509145555Z\r400924145555Z0O10	UCN10	USH10	USH10U\nconnsec10Uconnsec.com0‚\"0\r	*†H†÷\r\0‚\00‚\n‚\0›s~ìÿæg9hÇ}ò@³‡öç«¦¦^ŠÛÚØk²¯ä:êií&^éWüö+br‡Õš‘ÿ<KPßu®Úx¨7›åÏiÏû¢È×-.ˆBÈ1¢‹l´^DÍ~.×ûø„j¬ôó¨æÂóFðQ)÷#Y#\Z «â›X0`ŸØ·Úý¯©‚\ZCê÷óŽ~À<ÆÉ”#–PktJ²Žš$uÍ¯E¸çŠ›f x‚Ýöä„¿±üºã:ùöÄ\0Þt6L©Ú³/8ÆÔˆÃFQMá¼z|,‰óÜïJ9uuX?ê Ž¢ËD™q–\'ûŒnãµÐÄkÂa&í$sqÅ\00\r	*†H†÷\r\0‚\0š¤\rÅŽ²š8÷xU°ÇÐèÀ\"½üçN‡KT ÙFžØ€Ôw^Uó_:¶Œ!C|µŸEhë.þRtêu«ìdþÄñI¬wczÌŠ.è=îeËZ\0‘ãÅ\n×Ÿé½ÆOG-š²#qlBçU“}EB\nj^Ÿ›jÑ7âÑþú°x[3Ã%Ïsº2H‰…ÙˆE¯°ý¥Ú‘p´zŠ”oêZªâ:æî8pS¹–Sr€R«)‘øûôè+h^–ª«Y§RÁ^ØWD˜rO£u ö±s¨YZo“þ>¾#úî¢áNûzxœôé°)Yƒ|º¼wŒ-}ãnï—ÔmR}(áG%	ëœU+V×H’›ðâÁ	ØÖDÀ','https://shikey-dev-ed.my.salesforce.com?so=00D90000000r4kw','https://sso.connsec.com/sec/saml','https://sso.connsec.com/sec/saml',15,'persistent','https://sso.connsec.com/sec/saml',0,'Redirect-Post',0);
/*!40000 ALTER TABLE `apps_saml_v20_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apps_token_based_details`
--

DROP TABLE IF EXISTS `apps_token_based_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
INSERT INTO `apps_token_based_details` VALUES ('1327c121-cfad-49ba-bf61-afd3a1e09d5c','d6227a3d7756c255874ec7029678b8d1','DES',1,1,1,1,'http://tokenbased.demo.maxkey.org:8080/demo-ltpa/ltpa.jsp',0,1,0,0,'ltpa','LTPA'),('38c8a544eaa04aaeaa49d9c77ace40cd','c1f6adfcadd8ba23f73395f16a45dbe7','DES',1,1,1,1,'http://tokenbased.demo.maxkey.org:8080/demo-tokenbase/jsontoken.jsp',0,0,0,1,NULL,'POST'),('78917a82-1c86-4020-b86a-3b1b350357e3','985e805bd49770e7e797209db3cc2767','DES',0,1,1,1,'http://tokenbased.demo.maxkey.org:8080/demo-jwt/jwtcallback.jsp',0,0,0,0,'ttt','POST'),('f1e33b71-f553-42ab-ae91-2fd913854cda','1729a1ee16e532d61e097c01054dcfe7','DES',0,1,0,1,'http://tokenbased.demo.maxkey.org:8080/demo-tokenbase/sampletoken.jsp',0,0,0,0,NULL,'POST'),('maxkey_mgt','4bbb82a8f4928756c4054abdba5956ba1e698f87c64032dc548d6ec7dc3c4863','AES',1,1,0,1,'http://sso.maxkey.org:9521/maxkey-mgt/login',0,0,0,0,'','POST');
/*!40000 ALTER TABLE `apps_token_based_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forgot_password`
--

DROP TABLE IF EXISTS `forgot_password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
INSERT INTO `history_login` VALUES ('11a6dfc7-5d36-43fe-bf6c-9dccf6f1dd12','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 14:05:08','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','03e25597-33e1-43d1-80b7-37be15bdecf5','Chrome/78','Windows NT 10.0','Browser','2019-11-09 22:05:08','2019-11-09 14:23:38'),('12e7120a-c7e1-45fe-8d74-d12e95d5880d','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-04 14:45:03','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','f3691728-4580-4a4c-b204-4c96102a404e','Chrome/77','Windows NT 10.0','Browser','2019-11-04 22:45:03','0000-00-00 00:00:00'),('14db82b0-d2b2-4bf3-b4db-b62093f89d23','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-09 01:02:47','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','3fc6ee3b-5910-4e87-b504-5b5621fa48d4','Chrome/78','Windows NT 10.0','Browser','2019-11-09 09:02:47','0000-00-00 00:00:00'),('1937c1bd-ff1a-46e4-8307-7bcac1022139','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-09 10:16:48','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','c78f3ac8-2eae-4733-b7a8-01d74908e021','Chrome/78','Windows NT 10.0','Browser','2019-11-09 18:16:48','2019-11-09 10:28:45'),('1b43ea5f-7d68-4111-831f-f2b969ae25eb','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-06 14:58:26','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','98ef9cb6-ba1b-41ff-bf40-a2cb2d63b0c0','Chrome/77','Windows NT 10.0','Browser','2019-11-06 22:58:26','0000-00-00 00:00:00'),('2186d5f9-e0d1-4e75-845d-85e738aced91','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 14:48:25','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','d68ae4c5-b749-4ccd-bb18-e362c9db329a','Chrome/78','Windows NT 10.0','Browser','2019-11-09 22:48:25','0000-00-00 00:00:00'),('23ac5c2e-d2a0-4e4d-bbd8-d650b5463554','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-12-02 04:06:04','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','546f9be3-83a6-46d7-9e12-074af6ee9ef3','Chrome/78','Windows NT 10.0','Browser','2019-12-02 12:06:04','2019-12-02 04:10:03'),('2402a0d9-11ae-4fe8-81e0-bd58e331bb9f','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-30 15:00:28','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','06e55836-4fd5-4f66-b4c5-653b4f793119','Chrome/77','Windows NT 10.0','Browser','2019-10-30 23:00:28','0000-00-00 00:00:00'),('24ede113-b483-44a0-8a55-9b51ab364051','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-07 14:33:02','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','b668efa3-8423-4ed6-95d4-c38414a50491','Chrome/77','Windows NT 10.0','Browser','2019-11-07 22:33:02','0000-00-00 00:00:00'),('2b110937-a553-4edf-8f40-325b7bed1d7f','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-06 14:53:01','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','ca5d7ad6-1a9c-426a-bb85-9394a1940a0f','Chrome/77','Windows NT 10.0','Browser','2019-11-06 22:53:01','0000-00-00 00:00:00'),('3077aae7-6298-4234-99ec-0f8390d06ebc','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-08 14:46:30','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','8854f632-873d-4108-935e-2006cc23f7f0','Chrome/78','Windows NT 10.0','Browser','2019-11-08 22:46:30','0000-00-00 00:00:00'),('4086d31a-94a0-4c28-9dbe-ee345f730562','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-06 15:28:25','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','2e0c997f-6ce2-409b-b8a7-8d99fcd1ce66','Chrome/77','Windows NT 10.0','Browser','2019-11-06 23:28:25','0000-00-00 00:00:00'),('41a7c0a7-1203-4fca-91c7-ee434f31b12a','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-09 10:04:38','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','37cce053-b022-4331-b110-b808472f4ca3','Chrome/78','Windows NT 10.0','Browser','2019-11-09 18:04:38','0000-00-00 00:00:00'),('43a61c35-3e1d-48da-934a-a5f344ad58be','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-20 14:47:29','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','881e00e9-0047-4661-b7b2-f76dbd3a46c9','Chrome/77','Windows NT 10.0','Browser','2019-10-20 22:47:29','0000-00-00 00:00:00'),('4bbfcdce-efd2-4770-a501-3c7b87bbc518','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-21 15:46:44','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','19b2a9e4-94fc-4c8a-826b-ac20bab14e2d','Chrome/77','Windows NT 10.0','Browser','2019-10-21 23:46:44','0000-00-00 00:00:00'),('5179d964-fa8b-4a28-b286-8e6816771848','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-04 15:46:04','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','a285178a-2672-45a5-841c-bc20040ca16c','Chrome/77','Windows NT 10.0','Browser','2019-11-04 23:46:04','0000-00-00 00:00:00'),('54a63b5b-0000-4699-8cd5-30aed4d8fba5','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-07 13:30:38','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','afe35752-19ed-4140-9042-16123678d594','Chrome/77','Windows NT 10.0','Browser','2019-11-07 21:30:38','2019-11-07 14:15:55'),('5d126fbb-20ba-4035-9306-83ff24f711ab','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-30 14:54:46','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','3b4de92b-948a-4b42-9d5d-2803216c62dd','Chrome/77','Windows NT 10.0','Browser','2019-10-30 22:54:46','0000-00-00 00:00:00'),('64900db2-f2d9-49a7-9845-852ac8708c28','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-09 01:06:05','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','0fc5e1e5-4bf3-4a4e-9d52-77e2a84bfb71','Chrome/78','Windows NT 10.0','Browser','2019-11-09 09:06:05','0000-00-00 00:00:00'),('6bc27d76-afff-4721-98b5-cc86589b257a','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 10:58:59','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','b4b9d103-a850-499c-9ec5-7e4d5e1b677e','Chrome/78','Windows NT 10.0','Browser','2019-11-09 18:58:59','0000-00-00 00:00:00'),('6c4b7032-327a-4114-af59-903342143026','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-29 15:00:11','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','262ee673-8ac3-4e7e-8344-d216ff5994ab','Chrome/77','Windows NT 10.0','Browser','2019-10-29 23:00:11','0000-00-00 00:00:00'),('71c7652a-f241-4e3b-bac3-1b5855530ced','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-12-02 04:10:12','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','2d32a090-495e-4308-a40c-8a4eacd176cf','Chrome/78','Windows NT 10.0','Browser','2019-12-02 12:10:12','0000-00-00 00:00:00'),('77397d99-865b-4ee9-974e-013a0fc2a7dc','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 10:45:00','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','dfc2eb06-bbbc-4959-a8b1-aedb35686fdc','MSIE/ Touch','Windows NT 10.0','Browser','2019-11-09 18:45:00','0000-00-00 00:00:00'),('7e3d48cc-602c-464a-bd85-cf86d61a57ea','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-20 14:44:23','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','7db4e942-dfc4-4539-bf0b-317aea98222c','Chrome/77','Windows NT 10.0','Browser','2019-10-20 22:44:23','0000-00-00 00:00:00'),('7f438485-83b9-4174-802b-1bb33fcf44ae','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-09 02:28:45','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','1df9c283-01de-45a3-82a1-4dc1b4e7686d','Chrome/78','Windows NT 10.0','Browser','2019-11-09 10:28:45','2019-11-09 02:29:35'),('7f954cde-ce70-414b-8a5d-7c7404bfe791','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-09 03:28:22','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','d550de99-7ed4-4b39-b61f-186abe4ca271','Chrome/78','Windows NT 10.0','Browser','2019-11-09 11:28:22','0000-00-00 00:00:00'),('86304e15-610f-438a-8e36-0d934f7a6757','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-02 07:46:03','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','122e7048-6333-4b96-ac46-c989d85dfdc5','Chrome/77','Windows NT 10.0','Browser','2019-11-02 15:46:03','0000-00-00 00:00:00'),('90348d1e-1a60-4cf1-9dc2-d4bf35ad2edc','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 01:49:18','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','ba1efb04-f5ac-4e69-831b-d40952c134ac','Chrome/78','Windows NT 10.0','Browser','2019-11-09 09:49:18','0000-00-00 00:00:00'),('93c7501c-afcf-45be-94b4-caaeaf9ec74b','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 01:45:35','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','a24d4f30-5508-43d7-9e29-5dfd7b57320e','Chrome/78','Windows NT 10.0','Browser','2019-11-09 09:45:35','2019-11-09 01:49:00'),('93c7eafc-0a3c-4122-a04b-e59ee2afc861','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-04 14:02:09','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','b6dc6e15-bb91-4a36-899a-fab7e1b89709','Chrome/77','Windows NT 10.0','Browser','2019-11-04 22:02:09','0000-00-00 00:00:00'),('9672f460-5861-474d-b669-cfb7921d13eb','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-22 14:41:13','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','cbcd9c77-79e3-4b5d-a3ac-0a5dfcd0c854','Chrome/77','Windows NT 10.0','Browser','2019-10-22 22:41:13','0000-00-00 00:00:00'),('9dfa254a-767a-4c7e-ac1b-8e7fb0f01517','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-12-02 04:03:21','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','4b3719ed-fd1f-45e0-afb8-6da1acb03d72','Chrome/78','Windows NT 10.0','Browser','2019-12-02 12:03:21','0000-00-00 00:00:00'),('9f974c2f-f419-4f3e-ad48-52663b486061','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-20 15:19:59','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','9c277049-e9e4-4d0b-8b20-a8703458899c','Chrome/77','Windows NT 10.0','Browser','2019-10-20 23:19:59','0000-00-00 00:00:00'),('a0483e46-efc9-4660-a9e7-398df8a297f1','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-29 15:50:18','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','e837fd57-2efe-4627-bb85-fb3c087a9074','Chrome/77','Windows NT 10.0','Browser','2019-10-29 23:50:18','0000-00-00 00:00:00'),('a597d4a0-be6f-417f-bfad-1c781136e17e','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-04 15:52:32','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','bcdaf679-1e06-43da-b5c9-a30dd616c4e5','Chrome/77','Windows NT 10.0','Browser','2019-11-04 23:52:32','0000-00-00 00:00:00'),('a6b9651d-b0e5-4aed-82c7-416b8b5f92b1','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 10:43:25','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','fcedbcc7-3426-400e-bcf0-def47a14833d','Chrome/78','Windows NT 10.0','Browser','2019-11-09 18:43:25','0000-00-00 00:00:00'),('a7da5d68-1595-4525-8916-477a0cc63edf','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-02 07:28:11','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','f171642d-3a22-43d9-a378-71260a18e8e0','Chrome/77','Windows NT 10.0','Browser','2019-11-02 15:28:11','0000-00-00 00:00:00'),('a8d854f1-7dcb-4b0b-9d37-10f53f7e7637','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 03:18:13','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','c3d47b1c-f961-45b2-a909-c2a63ebfa28a','Chrome/78','Windows NT 10.0','Browser','2019-11-09 11:18:13','0000-00-00 00:00:00'),('aaf31480-ead5-40d6-9523-dbea71d50f7f','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-07 15:58:30','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','408da3a5-7544-4fb7-b970-f4a99868f047','Chrome/77','Windows NT 10.0','Browser','2019-11-07 23:58:30','0000-00-00 00:00:00'),('aec58000-a4ed-4546-b7a5-0959ac755834','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-02 11:04:05','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','c39868dc-7c0f-41e7-ac2c-b3ea93fc67c1','Chrome/77','Windows NT 10.0','Browser','2019-11-02 19:04:05','0000-00-00 00:00:00'),('b3b0fd79-de1f-48b2-a5e7-7f8f02b6f92e','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-02 08:40:09','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','227fa42e-74e6-4647-9ac7-1852008325f1','Chrome/77','Windows NT 10.0','Browser','2019-11-02 16:40:09','0000-00-00 00:00:00'),('b52a2c85-6241-4893-99a6-6a2a2f6e193d','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-05 15:13:02','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','8a9ae8de-16c1-46a3-9433-4693a4688651','Chrome/77','Windows NT 10.0','Browser','2019-11-05 23:13:02','0000-00-00 00:00:00'),('ba9ff748-03cf-4ba2-b6e8-aa62e43339d1','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-07 14:16:15','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','5dd5579a-7e72-4e4a-a525-cd18a99c77b9','Chrome/77','Windows NT 10.0','Browser','2019-11-07 22:16:15','2019-11-07 14:32:49'),('bb954985-9e87-4787-92f0-c239139a00d3','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-04 15:50:02','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','3a1e3bf5-2db8-40f6-9ad0-861c16790fc5','Chrome/77','Windows NT 10.0','Browser','2019-11-04 23:50:02','0000-00-00 00:00:00'),('bda7bd29-d60e-46a9-af25-2259767d7b52','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-06 14:26:42','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','93bac58b-091b-42f2-9747-daac6b33aaff','Chrome/77','Windows NT 10.0','Browser','2019-11-06 22:26:42','0000-00-00 00:00:00'),('c4f7de49-f335-4c85-9d98-9a7be8d32532','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-20 13:01:29','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','7db51f3a-108c-4cf4-b15b-97c32c63ab56','Chrome/77','Windows NT 10.0','Browser','2019-10-20 21:01:29','0000-00-00 00:00:00'),('c54a4b2a-b0a9-4da4-abf4-0e62596cbac2','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-23 15:27:19','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','e64124dc-54fd-4104-9f5c-fd03710bd3f5','Chrome/77','Windows NT 10.0','Browser','2019-10-23 23:27:19','0000-00-00 00:00:00'),('c6a08bdf-33df-4f66-bd17-98c9ddb63f98','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-30 15:14:03','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','2934dcbc-1501-4ff0-8fd9-327c954bc7df','Chrome/77','Windows NT 10.0','Browser','2019-10-30 23:14:03','0000-00-00 00:00:00'),('ccd0028a-815d-4479-b21e-ca80fe91b518','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-20 12:41:03','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','9c5da3b6-97e4-4239-b5f8-8990a9e3b9e4','Chrome/77','Windows NT 10.0','Browser','2019-10-20 20:41:03','0000-00-00 00:00:00'),('d17ee2ef-6756-4134-a2ca-cd28ddb815e6','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-07 16:00:20','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','89c1b5eb-9810-457e-b693-0ec7cf0a599b','Chrome/77','Windows NT 10.0','Browser','2019-11-08 00:00:20','0000-00-00 00:00:00'),('d4d748a0-dcac-49a6-938b-f6ef945180a3','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 02:29:48','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','c3a9b83c-bc9a-4f08-8580-0ed476042bfb','Chrome/78','Windows NT 10.0','Browser','2019-11-09 10:29:48','0000-00-00 00:00:00'),('dd022de9-042e-487c-b250-c784c9fff4b0','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-08 15:10:33','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','b64311b4-c64a-4a74-88af-890e87af4a41','Chrome/78','Windows NT 10.0','Browser','2019-11-08 23:10:33','0000-00-00 00:00:00'),('e1c4cc13-3ebe-41ea-b651-cd971a2ed3bd','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-11-09 10:29:06','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','ad186813-1c49-491a-b632-5576e91955b1','Chrome/78','Windows NT 10.0','Browser','2019-11-09 18:29:06','0000-00-00 00:00:00'),('e1ee0549-5adc-4a27-b4e4-f7ddb1007251','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-09 01:40:59','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','4e7e9212-75ba-4655-9da5-5737b65cfae6','Chrome/78','Windows NT 10.0','Browser','2019-11-09 09:40:59','0000-00-00 00:00:00'),('e2e8c05a-a649-40a8-8a8a-4fe6a1d6efc8','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-09 03:33:23','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','6e797edf-faef-4f29-988b-c99d628195a6','Chrome/78','Windows NT 10.0','Browser','2019-11-09 11:33:23','0000-00-00 00:00:00'),('e414b653-78e2-40de-80f1-14834ce53cc7','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','127.0.0.1','2019-12-02 04:10:15','Jwt','7BF5315CA1004CDB8E614B0361C4D46B','','','bc0871db-0163-436c-955c-3d6a8eb98bd5','Chrome/78','Windows NT 10.0','Browser','2019-12-02 12:10:15','0000-00-00 00:00:00'),('f03e8e5b-5613-4e77-b69d-04c3df77e773','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-07 15:54:57','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','71b6b7f9-2b7f-4a2d-81e3-c40e244cb38d','Chrome/77','Windows NT 10.0','Browser','2019-11-07 23:54:57','0000-00-00 00:00:00'),('f3d3e385-aac0-4f3a-a122-4c0c5e17691a','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-02 08:28:25','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','26d1afd0-1741-470a-a558-be7532bebe3c','Chrome/77','Windows NT 10.0','Browser','2019-11-02 16:28:25','0000-00-00 00:00:00'),('fb55c52a-c315-4fb3-a878-1652f67a8373','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-11-08 14:29:24','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','eda90658-e76d-4ba7-b2b7-28729f9b1c6d','Chrome/78','Windows NT 10.0','Browser','2019-11-08 22:29:24','0000-00-00 00:00:00'),('fc6f5e6f-3536-4eb9-9929-737233875f9d','admin','ç³»ç»Ÿç®¡ç†å‘˜','success','0:0:0:0:0:0:0:1','2019-10-31 15:01:26','Local Login','7BF5315CA1004CDB8E614B0361C4D46B','xe00000004','','dc680e9d-83d8-4a48-b92b-73bdc8671fc1','Chrome/77','Windows NT 10.0','Browser','2019-10-31 23:01:26','0000-00-00 00:00:00');
/*!40000 ALTER TABLE `history_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_login_apps`
--

DROP TABLE IF EXISTS `history_login_apps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
INSERT INTO `history_login_apps` VALUES ('097f9281-0511-4cf4-b484-0ca38db03088','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:13:07','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('1','1','2019-10-30 15:04:37','1','1','1','1','1'),('1d397fc8-23dc-4c86-9458-00b0165e46ae','eda90658-e76d-4ba7-b2b7-28729f9b1c6d','2019-11-08 14:34:08','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('1da9367a-2dd9-4b84-bb05-5c7b382bb2a5','0fc5e1e5-4bf3-4a4e-9d52-77e2a84bfb71','2019-11-09 01:21:08','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('27900d59-9b97-4022-8ab1-e7a8842264a5','dfc2eb06-bbbc-4959-a8b1-aedb35686fdc','2019-11-09 10:45:07','c1cabfaeb9a448028ffab2148da9f65c','QQ Login','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('2e498dde-f525-4123-aee0-bf7df0e3e8c9','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:17:47','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('430ba289-01c0-4a75-b0ea-7ec49c507ac9','fcedbcc7-3426-400e-bcf0-def47a14833d','2019-11-09 10:43:28','525d261fa3b04d19af0debabbd5a1e2d','SalesForce     ','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('438fcf0c-cc6f-4349-be81-13e3ca211664','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:10:40','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('647e277d-433f-463c-a0a6-98930d0becd1','eda90658-e76d-4ba7-b2b7-28729f9b1c6d','2019-11-08 14:29:37','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('6a8e9e75-76df-4b58-9135-0a5c6b197e2d','2d32a090-495e-4308-a40c-8a4eacd176cf','2019-12-02 04:10:15','maxkey_mgt','MaxKeyç®¡ç†ç³»ç»Ÿ','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('6d697814-f33d-4e52-819b-df230e9e795b','8854f632-873d-4108-935e-2006cc23f7f0','2019-11-08 14:50:12','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('75230872-b6e3-4334-9998-8efdf845d219','0fc5e1e5-4bf3-4a4e-9d52-77e2a84bfb71','2019-11-09 01:06:09','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('76028fc0-65db-4f14-83b7-e13497b8d48d','0fc5e1e5-4bf3-4a4e-9d52-77e2a84bfb71','2019-11-09 01:25:20','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('797b9202-1ddc-44eb-b84b-4df41ef98f63','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:48:31','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('7a329e91-bf7c-47b4-ac3a-ad428f37a4bd','3fc6ee3b-5910-4e87-b504-5b5621fa48d4','2019-11-09 01:02:52','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('7b2356f4-197c-4afc-a6f2-9f02a1082817','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:18:19','c8038bd4-12a4-4b45-9d43-61b3ecdc2eb4','æœ‰é“äº‘ç¬”è®°','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('8754b8d4-33c4-418b-9440-ec2a68a1487b','fcedbcc7-3426-400e-bcf0-def47a14833d','2019-11-09 10:44:01','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('8b340426-2cb6-4219-a530-70181777ed30','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:44:57','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('93e012fa-0c33-4b00-ac85-7cf0ef2d1468','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:46:16','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('94aeb5db-ccad-42e2-a51c-b916b59b02ec','dfc2eb06-bbbc-4959-a8b1-aedb35686fdc','2019-11-09 10:45:11','c1cabfaeb9a448028ffab2148da9f65c','QQ Login','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('aab7c33d-3ea9-4398-97ee-7e0b63e01739','8854f632-873d-4108-935e-2006cc23f7f0','2019-11-08 14:46:37','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('b4312fc6-811d-41d5-ae3a-268e3a4c9829','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:18:10','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('b6f70f47-528d-4a73-bbb5-de0b76db4b65','0fc5e1e5-4bf3-4a4e-9d52-77e2a84bfb71','2019-11-09 01:26:26','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('c64f34ba-7d6e-45e2-b946-369ec361b0bc','c3d47b1c-f961-45b2-a909-c2a63ebfa28a','2019-11-09 03:25:58','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('dfc32176-616c-481a-98bf-1651a9286ec2','0fc5e1e5-4bf3-4a4e-9d52-77e2a84bfb71','2019-11-09 01:23:55','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('eaa39588-78a8-477a-a2ae-bcbd40152624','0fc5e1e5-4bf3-4a4e-9d52-77e2a84bfb71','2019-11-09 01:08:27','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('f332a0fd-bc87-4639-ae1d-f4894cd95c8c','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:45:17','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('f336631d-2d4b-4d1c-8708-6bfc5de5a439','8854f632-873d-4108-935e-2006cc23f7f0','2019-11-08 14:49:53','850379a1-7923-4f6b-90be-d363b2dfd2ca','ç½‘æ˜“163é‚®ç®±','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('f3d9ba18-370e-48e5-9723-092cbf613413','b64311b4-c64a-4a74-88af-890e87af4a41','2019-11-08 15:43:41','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜'),('fee87158-83e9-4e7a-bcba-476d4f32b7fb','0fc5e1e5-4bf3-4a4e-9d52-77e2a84bfb71','2019-11-09 01:28:48','41065fe3-ae67-4172-a460-fd0079e88294','CAS Demo','7BF5315CA1004CDB8E614B0361C4D46B','admin','ç³»ç»Ÿç®¡ç†å‘˜');
/*!40000 ALTER TABLE `history_login_apps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_logs`
--

DROP TABLE IF EXISTS `history_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = utf8 */;
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
INSERT INTO `sso_saml_v20_config` VALUES ('sdaf','sdaf','0Â‚r0Â‚ZÂ >Â‰ÂºVc0\r	*Â†HÂ†Ã·\r\00O10	UCN10	USH10	USH10U\nconnsec10Uconnsec.com0\r130509143800Z\r130522063135Z0O10	UCN10	USH10	USH10U\nconnsec10Uconnsec.com0Â‚\"0\r	*Â†HÂ†Ã·\r\0Â‚\00Â‚\nÂ‚\0Â„[0u`35Ã”KJÃ©ÃªÃ¼Â§%Ã—Â‚Â¾ÂœÃ˜O6Â•zÃ§Â˜Â½Ã¹Â…Ã¶ÃºÃŸÃ“JÂ»Â”0Â£Ã®Ã±\0Â”Â”>Â€1Z\"ÂÂ£Â“ÃŠÃ¤Ã‚Ã¥UÃ»Ã¶	pVÃ¯vÃ¸ Ã¹%e\n23 ÃªlÂ¹\rG6Ã¹Â´ÃžÂ„Â’*Â£Â‹Â±Ã±MÃ”ÂŽ<Â„Ã¿@<Ã¨\0XÂ—Ã¤[Ã‘.ÃŽÃ´h5Ã©Â·Â¥Â¡ÂWÂ±Â¦Ã­N-A9ÂSÂ’Â’Â½ÃÂ³(,Â¦b#;Ã„\0Â¸Ã¦Â•)SMaÃ±C7Â8Â¿7Ã»JÃ­Â¤uÃ˜\"`Â 0ÃœÃ·Â€Ã»Ã§~ÃŸ#ÂœÃ›Ã½7ÃÂÃ›7XÃ Ã Ã©ÃaÂ¨p	dÂ¸Ã¥5Ã©ÃŠY`ÂÂ’Ã“,OhÃ¤<\nÂˆD>\\#Â°1Â®ÃžjÂ¹t{NÂuÂÃ¿Â…Â›\'=|ÂŠ8\\ÃºÃœ\0Â£T0R0UÃ¿0\00UÃ¿Â 0U%Ã¿0\n+0\ZU0Âconnsec@163.com0\r	*Â†HÂ†Ã·\r\0Â‚\06ÂŽ)3Â“Â¸ÂŒÃ 7Â£Â»Ã“ÂÂÂ› mÂ¨gÂ²0Ã Â±HÃ›eÃ¬Â†Ã‹Âš~ÃšBÃ¤Ã»YÂ“SÃ¶Â£Â¼Ã¦3:[Ã´ÃºÃ¸\'ÃŽÃ­Ã³ÃÃ‚/Â–Âª{	Â›Ã¦5.Ã¼Â¼Ã‹Â›Ã‘fÂ»ÃŽÃ‹Âµ,Ã·HÂŽ_Ã§zÃ‡Ã0Ã©Â§?Ã¨Â¹CÂ€Vf:ÂˆÃº]|pÂŒÃ˜SÂƒFÃ¾Ã¢Â¿Ã¼Ã†vÂŠÃ½MÃ½\0Â¡fÃœTÃŠRÃ¡Ã»%ÂŒ2cÃª?RÂ°Ã§qÂ€ÂŠÃ’ÃŸLÂ¹Ã¨Â”BÃ¸Ã‹Â§Â¦Ã´Ã€ÂžÂ¨Â·Â¶9Ã‹tQ&|Âµ&ÂŒÃ«`Â©oÂ·Â´Ãµx)Ãž&Â¬Ã¦b73Â¥|Â5Ã¼ÃºÂÃ‹i`Â‡Ã¯HOÃ•Â¤0Â»v\0ÃˆÂ¨ÂžÂªH)&~Y?;Â—Â„F)Ã¢Ã®iÃ°CxÂ“Â¥z$/;Â­','Ã¾Ã­Ã¾Ã­\0\0\0\0\0\0\0\0\0\0sdf\0\0>Â™@Ã°ÂŽ\0X.509\0\0v0Â‚r0Â‚ZÂ >Â‰ÂºVc0\r	*Â†HÂ†Ã·\r\00O10	UCN10	USH10	USH10U\nconnsec10Uconnsec.com0\r130509143800Z\r130522063135Z0O10	UCN10	USH10	USH10U\nconnsec10Uconnsec.com0Â‚\"0\r	*Â†HÂ†Ã·\r\0Â‚\00Â‚\nÂ‚\0Â„[0u`35Ã”KJÃ©ÃªÃ¼Â§%Ã—Â‚Â¾ÂœÃ˜O6Â•zÃ§Â˜Â½Ã¹Â…Ã¶ÃºÃŸÃ“JÂ»Â”0Â£Ã®Ã±\0Â”Â”>Â€1Z\"ÂÂ£Â“ÃŠÃ¤Ã‚Ã¥UÃ»Ã¶	pVÃ¯vÃ¸ Ã¹%e\n23 ÃªlÂ¹\rG6Ã¹Â´ÃžÂ„Â’*Â£Â‹Â±Ã±MÃ”ÂŽ<Â„Ã¿@<Ã¨\0XÂ—Ã¤[Ã‘.ÃŽÃ´h5Ã©Â·Â¥Â¡ÂWÂ±Â¦Ã­N-A9ÂSÂ’Â’Â½ÃÂ³(,Â¦b#;Ã„\0Â¸Ã¦Â•)SMaÃ±C7Â8Â¿7Ã»JÃ­Â¤uÃ˜\"`Â 0ÃœÃ·Â€Ã»Ã§~ÃŸ#ÂœÃ›Ã½7ÃÂÃ›7XÃ Ã Ã©ÃaÂ¨p	dÂ¸Ã¥5Ã©ÃŠY`ÂÂ’Ã“,OhÃ¤<\nÂˆD>\\#Â°1Â®ÃžjÂ¹t{NÂuÂÃ¿Â…Â›\'=|ÂŠ8\\ÃºÃœ\0Â£T0R0UÃ¿0\00UÃ¿Â 0U%Ã¿0\n+0\ZU0Âconnsec@163.com0\r	*Â†HÂ†Ã·\r\0Â‚\06ÂŽ)3Â“Â¸ÂŒÃ 7Â£Â»Ã“ÂÂÂ› mÂ¨gÂ²0Ã Â±HÃ›eÃ¬Â†Ã‹Âš~ÃšBÃ¤Ã»YÂ“SÃ¶Â£Â¼Ã¦3:[Ã´ÃºÃ¸\'ÃŽÃ­Ã³ÃÃ‚/Â–Âª{	Â›Ã¦5.Ã¼Â¼Ã‹Â›Ã‘fÂ»ÃŽÃ‹Âµ,Ã·HÂŽ_Ã§zÃ‡Ã0Ã©Â§?Ã¨Â¹CÂ€Vf:ÂˆÃº]|pÂŒÃ˜SÂƒFÃ¾Ã¢Â¿Ã¼Ã†vÂŠÃ½MÃ½\0Â¡fÃœTÃŠRÃ¡Ã»%ÂŒ2cÃª?RÂ°Ã§qÂ€ÂŠÃ’ÃŸLÂ¹Ã¨Â”BÃ¸Ã‹Â§Â¦Ã´Ã€ÂžÂ¨Â·Â¶9Ã‹tQ&|Âµ&ÂŒÃ«`Â©oÂ·Â´Ãµx)Ãž&Â¬Ã¦b73Â¥|Â5Ã¼ÃºÂÃ‹i`Â‡Ã¯HOÃ•Â¤0Â»v\0ÃˆÂ¨ÂžÂªH)&~Y?;Â—Â„F)Ã¢Ã®iÃ°CxÂ“Â¥z$/;Â­\0\0\0\0connsec.com\0\0>Â‰Ã”\rÂ”\0\0\00Â‚Ã¼0\n+*\0Â‚Ã¨Ã‹Â¯aMÃ©ÂºÂ¡-Ã¿5Â³Â·ÂžÂ¿ÂHfk<Ã«ÂžÂ“Â­nH	Â„Ã%ÃÂŠ{Â„4Âº#Â’bÂºÂ¨Ã¹zÃµÃ„;Â“WÂ¿Ã‚\rÃ‹ÂŠÂ£2Â­?0ÂšÃ¬QC#6Â¢ÂˆwP@\\Âª09CJ.Ãµ*Â†Â™pAÃŠaÂ§Ã»Ã¡Ã„H/Ã­Ã¹CÂdB\\ÃºO @Â‚EÂ¹zÃ®=Ã³\0h%Ã¬b:}ÃˆÃ…ÃƒÃ‹NÂ©Ã Ã­Â«Â¼R\'Â˜CÂ‡Ã+Ã†Âš*ksÂ«Ã©wÂ¿xÃ«Â .Ã®GÃ•	Â±k[Â¡wÂ—Â³n_+Â…Ã™\0Â•sÂ—Â†Ã‚=Â©`wdÂ¶@Ã‘Ã±ÃšÂ‡HÂœÃ¼teÂ·``Ã½`ÂÃ¢Â±Â‡(5\'1ZÂ½Ã™4LRhy_Ãº23yÂ°Ã‘Â¿Â™!Â¬Ã‹MÃ¤,Ã©&Ã½ÂCÂÃšzÃ’bÂ†_Â£PÂ¡Ã»Â±B5ÃÂ´ÃÂ•%Â¤Ã¶ Ã½Â¹ÃP}Â”Â³Ã…Â•cÂœÂšÃ—qÂŸÂ‹Hb~Ã„Â¿7MÃ·ÃªyÂ¥ÂœÂ’ÃÂÂ‘`ÂžOÂ•ÂÂŽ=ÂœYÂ»Â`/Â«rÃ¯Ã‘Ã«Â¼:Â›PÂƒ~ÂƒÃ‹Âž4VoÃ¼Â£Â·Â€ÂÂ´Â´AÂˆ7^Â»ÃºÂ/ÃKÃ¾lbÃ»ÂŽÂ‹rÃ€aSÂ‚)Â™$Â¯ÂTÂ‹C?\'Â¤V#Ã¹Â¯iÃ‘Ã„Â¤ÃgÂ”wÃ<eÂ–\nlTÃšÂ®Ã±ÂºMÂ¯Â¥Â›{\0\nÂ¡clÂ–-Â‹Â§ÂƒÂ”Ã‰\0^:VÃ‚Â¦Ã¸#\Z<_Ã¬Â¾Ã“Â‚ÃœÂ•Â¹O+l^}.Â¶Â¼k1SÂ«Ã¸O\'=F6}Ã•Â¾Â³e2Â¼CÂ©Ã¢rÂƒÃŠÃŒÃ˜tÃ³Â²iÂ´Ã·\ndÃ˜	Ã¨Â GJOÃ¶~ZHÂ®QÂeÂ¿Â¦Ã4{Ã‰Ã­Â¥tÃºmÃ€uÂ·ÂÃ“Â«`Â¥FÃ‚YÂ°qÂ­Â¶Â”Â¡tÂ§|k(Ã¸Ã©}Ã²Â’>Â‰JÂ¶RÃ¯Ã­Â‹Ã…jÂ‚Â·4Ã¦Â–Âª\n\\7oÃÂ·Â€Â¬=RÃ¹]ÃŒÃ«lzSÃÂÂ¨uÂ¤Ã¨~ÃšÃ…ÂÂ¿Â€>Â¥EÃ³.O\0Â˜Ã­Ã˜Ã¡ÂÂ¸EHÃ¹Â‡Ã—ÂªqZlNt*xÃ’Ã­ÃºtÂ’Ã…6:?Â”Ã¥Ã¢LUÂºÂ“8Ã¼Ã¥ÂŒ#gÃ¬Ã‘ÂªÂ†;Ã–.Ã-RÂ°Â§>Â“Â„2RÃ£jÃ‹\"Â¬Ã¿Â«ÃŸÂˆÃ€Â–P`Ã—Ã‡QÃ¼zVÂ±71UÃ—Â»$Â‰Ã°RtDÂ‹Â¬_4kÂ¡Ã±Ã¢ÃŽÃ¶,Ã‘ÃoÃ´UÃ¼Ã°sÂ·Â™h&p8kÂ—Â1ÃœÂ¢Â˜4Â¹ÂªÂ—Ã‘Z8Â‰5YÂ‹ÃµÃ”1ÃžÂ—Â¦Ã°ÃˆÃ¡!Â°EÃŸUÃ°OÃ³4/Â 0Ã½ÃœÂ¡6$Â‹Ã¹ZÂ‰hN: Ãˆ_yÂ­\rÃ§]MgÂ…Ã»UÂÃƒ`Â–Cs\rÃ–Ã›Ã«Ã’Ã¹8CÂ—ÂœÃ•lÂºWÃ°Ã‡ Ã¸Â“8Â¾X?uhu=R$ÂˆNÃ“AÂ—zÂ–Â‰Ã˜Â¯eÂ¬Â›{Â˜ÃŽÃ•\Z12Ã£iÃ¯Ã¢:;`ÃŠZ-\0Â²)z\'Ã”Ã¬6]Â¦`Ã‹	Â¾ÂµÂƒaAÃ Ã³,(LÂºÃ«Ã†qbpÃƒÃ¸Â°Ãš\"Â‰Ã·Ã”EÃ¿j%YÃˆÂ¸Ãƒ8Ã‰Â­DZÃ¥ÃÂšÂ©Â´O6D|Ãº<MWÃ´Â€Â¬TÃ¾\Z]5Â¹ÃºÃ˜z>xÂ°Ã¾Ã„Â¿Ã“Ã¬ÂˆfÃiÃ­Ã’vÃ–NÃƒÃ…mÃ’Â¸Ã¢K8WÃ®qIÃ–$ÃŽ	Ã‰F\\ÂŸÃ°LÃ¬Ã½2,jÂ²ÃœÃ\"Ã{AÃ°Â–0ÂˆyÂÂ…Ã¶Pa=kVÃŸ~9Ã…W>Â´oÃ’Ã¢ÂµÂ‹Â”Â®Ã„U^Ã¼ÂœkeÂ‹ET3|ÂŸÂ·n`Ã§Ã¥Ã¯8!ÂQÂ™jÂªKOÂ´s{\r=lÂ¢Ã¸Â§K/ÃJÃ‚.Â—Ã¥Ã¤,Ã¬Ã‡ÂÃÃœ ?QÃ£\"ZÃ·hÂ¶9}ÂB,Â€Ã˜Ã• ÃšÃœ:Ã™ÃˆsuÂ‰ÂŒuÂ˜Â©Â»Ã½s	)$Â†fÃ¯Ã¯Â…?Â¤ÃœÂ¨Â¥-Â¼glÃ¸kÂ¥Ã^ÂŸÃ‘Â¼	OÃ¥Â±Â°Â›Ã”ÂŽÂ¹ÂlÃ±Ã•IÃ$Ã¯lÂ°Â¼KcÃ¸Â¾Ã¯0Â›Â±Ã”Ã–?\"Â¬,ÃÂ†8Â›Â²Ã¨.ÃŽÃ•ÂŽIuxtÂšÂ©?nm]Â®Âœ8Â™JÃ¬Âº2$Ãƒ\0\0\0\0X.509\0\00Â‚\Z0Â‚Â QÂ‹Â¸Ã»0\r	*Â†HÂ†Ã·\r\00O10	UCN10	USH10	USH10U\nconnsec10Uconnsec.com0\r130509145555Z\r400924145555Z0O10	UCN10	USH10	USH10U\nconnsec10Uconnsec.com0Â‚\"0\r	*Â†HÂ†Ã·\r\0Â‚\00Â‚\nÂ‚\0Â›sÂ~Ã¬Ã¿Ã¦g9hÃ‡}Ã²@Â³Â‡Ã¶Ã§Â«Â¦Â¦^ÂŠÃ›ÃšÃ˜kÂ²Â¯Ã¤:ÃªiÃ­&Â^Ã©WÃ¼Ã¶+brÂ‡Ã•ÂšÂ‘Ã¿<KPÃŸuÂ®ÃšxÂ¨7Â›Ã¥ÃiÃÃ»Â¢ÃˆÃ—-.ÂˆBÃˆ1Â¢Â‹lÂ´^DÃ~.Ã—Ã»Ã¸ÂÂÂ„jÂ¬Ã´Ã³Â¨Ã¦Ã‚Ã³FÃ°Q)Ã·#Y#\ZÂ Â«Ã¢Â›X0`ÂŸÃ˜Â·ÃšÃ½Â¯Â©Â‚\ZCÃªÃ·Ã³ÂŽ~Ã€<Ã†Ã‰Â”#Â–PktJÂ²ÂŽÂš$uÃÂ¯ÂEÂ¸Ã§ÂŠÂ›fÂ xÂ‚ÃÃ¶Ã¤Â„Â¿Â±Ã¼ÂºÃ£:Ã¹Ã¶Ã„\0Ãžt6LÂ©ÃšÂ³/8Ã†Ã”ÂˆÃƒFQMÃ¡Â¼zÂ|,Â‰Ã³ÃœÃ¯J9uuX?ÃªÂ ÂŽÂ¢Ã‹DÂ™qÂ–\'Ã»ÂŒnÃ£ÂµÃÃ„kÃ‚a&Ã­Â$sqÃ…\00\r	*Â†HÂ†Ã·\r\0Â‚\0ÂšÂ¤\rÃ…ÂŽÂ²Âš8Ã·xUÂ°Ã‡ÃÃ¨Ã€\"Â½Ã¼Ã§NÂ‡KTÂ Ã™FÂžÃ˜Â€Ã”w^UÃ³_:Â¶ÂŒ!C|ÂµÂŸEhÃ«.Ã¾RtÃªuÂ«Ã¬dÃ¾Ã„Ã±IÂ¬wczÃŒÂŠ.Ã¨=Ã®eÃ‹Z\0Â‘Ã£Ã…\nÃ—ÂŸÃ©Â½Ã†OG-ÂšÂ²#qlBÃ§ÂUÂ“}EB\nj^ÂŸÂ›jÃ‘7Ã¢Ã‘Ã¾ÃºÂ°x[3Ãƒ%ÃsÂº2HÂ‰Â…Ã™ÂˆEÂ¯Â°Ã½Â¥ÃšÂ‘pÂ´zÂŠÂ”oÃªZÂªÃ¢:Ã¦Ã®8pSÂ¹Â–SrÂ€RÂ«)Â‘Ã¸Ã»Ã´Ã¨+h^Â–ÂªÂ«YÂ§RÃ^Ã˜WDÂ˜rOÂ£u Ã¶Â±sÂ¨YZoÂ“Ã¾Â>Â¾#ÃºÃ®Â¢Ã¡NÃ»zxÂœÃ´Ã©Â°)YÂƒ|ÂºÂ¼ÂwÂŒ-}Ã£nÃ¯Â—Ã”mR}(Ã¡GÂ‘jÂ“f%Â•ÂŒÃ¶Â¹ÂŒÂ˜MiÃ•Ã—Â¹CÂŠ','connsec.com','CN=connsec.com, O=connsec, L=SH, ST=SH, C=CN','22 May 2013 06:31:35 GMT','sdf','emailAddress','superadmin','2013-05-12 06:59:31',NULL,NULL,1,234);
/*!40000 ALTER TABLE `sso_saml_v20_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userinfo`
--

DROP TABLE IF EXISTS `userinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
INSERT INTO `userinfo` VALUES ('44D64694BADD4423A336C05D49469B60','superadmin','dJbALpTupUiaiWsbgp/TVT/3mXQK5Q2RwyEOa7Vc3j0=','c0aca9ea77b47ef681862d5a328b2366','TEMP',NULL,'è¶…çº§ç®¡ç†å‘˜','SuperAdmin',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Asia/Shanghai','fr','zh_CN',NULL,NULL,NULL,NULL,'2015-04-30 02:07:25',NULL,'2014-01-20 08:00:00',NULL,'2015-04-30 02:08:09','2014-01-20 08:00:00','{\"age\":\"0\",\"cardno\":\"0\",\"oldname\":\"null\"}','è¶…çº§ç®¡ç†å‘˜','è¶…çº§ç®¡ç†å‘˜','è¶…çº§ç®¡ç†å‘˜',NULL,NULL,NULL,NULL,NULL,'AFG',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'AFG',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'101','ç§‘æŠ€éƒ¨',NULL,NULL,NULL,NULL,NULL,NULL,'2014-01-21','admin','2015-04-30',0,NULL,0,0,NULL,NULL,0,'chaojiguanliyuan','cjgly',1,'0000-00-00 00:00:00',NULL,'0',0,NULL,NULL,0,1,'127.0.0.1',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),('7BF5315CA1004CDB8E614B0361C4D46B','admin','$2a$10$mS6Wj3pqe3jhyBCaOqgE1OMxBgVT6gF5Jty3Hs4yn6KDctB9nwLCW','admin@admin','TEMP','adsystemadmin','ç³»ç»Ÿç®¡ç†å‘˜','ç³»ç»Ÿç®¡ç†å‘˜',1,NULL,NULL,'2342342343242344234','13705130848','0','admin@connsec.com',0,'http://login.maxkey.org/','Asia/Shanghai','de','zh_CN','5','wusdfdsf','0e6bea8d16229f0df9ff644efaf4e749',',41065fe3-ae67-4172-a460-fd0079e88294,52f0002d-4ef7-4b27-8c5b-41b9ee80835d,3f57d0b2-99ab-4e66-a938-718befb55369','2019-12-02 04:06:08',2,'2015-04-29 02:10:51',0,'2019-12-02 04:10:15','2019-12-02 04:10:03','{\"age\":\"12\",\"cardno\":\"11111111111111111111111111111111\",\"oldname\":\"Ã§ÂŸÂ³Ã©Â¸Â£1d\"}','admin','admin','admin',NULL,NULL,NULL,'shimin@qq.com','123123','CN','åŒ—äº¬','åŒ—äº¬','åŒ—äº¬',NULL,'123123','123123','admin@qq.com','123123','ä¸­å›½','åŒ—äº¬','åŒ—äº¬','åŒ—äº¬',NULL,'123123','sdf',NULL,NULL,NULL,'æ€»éƒ¨','105','ç§‘æŠ€éƒ¨','ç³»ç»Ÿç®¡ç†å‘˜',NULL,'ç§‘æŠ€éƒ¨ç»ç†',NULL,NULL,NULL,'2014-01-21','admin','2015-05-05',0,NULL,0,0,NULL,NULL,0,'xitongguanliyuan','xtgly',1,'2019-10-20 12:17:05','6e0cf549e8271c6081525dc3c92acf1412f8a3c5a9aa75eabe367bc9896da58b1e698f87c64032dc548d6ec7dc3c4863','0',0,NULL,NULL,0,653,'127.0.0.1',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);
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

-- Dump completed on 2019-12-02 12:11:11
