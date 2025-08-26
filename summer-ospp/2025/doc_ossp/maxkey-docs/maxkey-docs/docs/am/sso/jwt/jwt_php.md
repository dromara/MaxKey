---
sidebar_position: 2
title: JWT PHP客户端集成
---

## JWT PHP客户端集成

### 引入依赖firebase/php-jwt

```php
composer require firebase/php-jwt
```

### 验证签名

```php
<?php
require 'vendor/autoload.php';
use \Firebase\JWT\JWT;
use \Firebase\JWT\Key;

$publicKey = <<<EOD
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvyfZwQuBLNvJDhmziUCF
uAfIv+bC6ivodcR6PfanTt8XLd6G63Yx10YChAdsDACjoLz1tEU56WPp/ee/vcTS
sEZT3ouWJYghuGI2j4XclXlEj0S7DzdpcBBpI4n5dr8K3iKY+3JUMZR1AMBHI50U
aMST9ZTZJAjUPIYxkhRdca5lWBo4wGUh1yj/80+Bq6al0ia9S5NTzNLaJ18jSxFq
Z79BAkBm+KjkP248YUk6WBGtYEAV5Fws4dpse4hrqJ3RRHiMZV1o1iTmPHz/l55Z
SDP3vpYf6iKqKzoK2RmdjfH5mGpbc4+PclTs4GKfwZ7cWfrny6B7sMnQfzujCH99
6QIDAQAB
-----END PUBLIC KEY-----
EOD;

$jwt = $_POST["jwt"];
echo "jwt:\n" .$jwt. "\n";

$decoded = JWT::decode($jwt, new Key($publicKey, 'RS256'));

/*
 * NOTE: This will now be an object instead of an associative array. 
 *	   To get an associative array, you will need to cast it as such:
 */
$decoded_array = (array) $decoded;
echo "Decode:\n" . print_r($decoded_array, true) . "\n";
?>
```