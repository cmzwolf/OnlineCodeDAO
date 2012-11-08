-- phpMyAdmin SQL Dump
-- version 3.3.7deb5build0.10.10.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 03, 2012 at 04:41 PM
-- Server version: 5.1.49
-- PHP Version: 5.3.3-1ubuntu9.5



/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `PDR_Calc`
--

-- --------------------------------------------------------

--
-- Table structure for table `ConfigurationsDetails`
--

CREATE TABLE IF NOT EXISTS `ConfigurationsDetails` (
  `IdConfig` int(11) NOT NULL,
  `ParamName` varchar(50) NOT NULL,
  `ParamValue` varchar(50) NOT NULL
) TYPE=MyISAM;

--
-- Dumping data for table `ConfigurationsDetails`
--


-- --------------------------------------------------------

--
-- Table structure for table `DefaultRawParameter`
--

CREATE TABLE IF NOT EXISTS `DefaultRawParameter` (
  `RawParamName` varchar(30) NOT NULL,
  `RawParamValue` varchar(40) NOT NULL,
  `RawParamType` varchar(10) NOT NULL,
  `ParentType` varchar(10) NOT NULL,
  `ParentParamName` varchar(30) NOT NULL
) TYPE=MyISAM;

--
-- Dumping data for table `DefaultRawParameter`
--

INSERT INTO `DefaultRawParameter` (`RawParamName`, `RawParamValue`, `RawParamType`, `ParentType`, `ParentParamName`) VALUES
('ifafm', '20', 'integer', 'integer', 'ifafm'),
('itrfer', '0', 'integer', 'integer', 'itrfer'),
('jfg1kh2', '0', 'integer', 'integer', 'jfg1kh2'),
('ieqth', '1', 'integer', 'integer', 'ieqth'),
('ifisob', '0', 'integer', 'integer', 'ifisob'),
('NPresse', '3', 'integer', 'real', 'Presse'),
('deltaPresse', '1000', 'real', 'real', 'Presse'),
('PresseInf', '1000', 'real', 'real', 'Presse'),
('PresseVariationMethod', 'lin', 'string', 'real', 'Presse'),
('TgazInf', '2.7', 'real', 'real', 'Tgaz'),
('deltaTgaz', '100', 'real', 'real', 'Tgaz'),
('NTgaz', '0', 'integer', 'real', 'Tgaz'),
('TgazVariationMethod', 'lin', 'string', 'real', 'Tgaz'),
('vturb', '2', 'real', 'real', 'vturb'),
('AvmaxInf', '1.0', 'real', 'real', 'Avmax'),
('NAvmax', '0', 'integer', 'real', 'Avmax'),
('deltaAvmax', '1.0', 'real', 'real', 'Avmax'),
('AvmaxVariationMethod', 'lin', 'string', 'real', 'Avmax'),
('dsour', '0', 'real', 'real', 'dsour'),
('NFmrc', '0', 'integer', 'real', 'Fmrc'),
('deltaFmrc', '1.0', 'real', 'real', 'Fmrc'),
('FmrcInf', '5.0', 'real', 'real', 'Fmrc'),
('FmrcVariationMethod', 'lin', 'string', 'real', 'Fmrc'),
('NRadm', '0', 'integer', 'real', 'Radm'),
('deltaRadm', '10.0', 'real', 'real', 'Radm'),
('RadmInf', '1.0', 'real', 'real', 'Radm'),
('RadmVariationMethod', 'lin', 'string', 'real', 'Radm'),
('NRadp', '0', 'integer', 'real', 'Radp'),
('deltaRadp', '1.0', 'real', 'real', 'Radp'),
('RadpInf', '1.0', 'real', 'real', 'Radp'),
('RadpVariationMethod', 'lin', 'string', 'real', 'Radp'),
('srcpp', 'none.txt', 'string', 'string', 'srcpp'),
('F_dustem', '0', 'integer', 'integer', 'F_dustem'),
('alpgr', '3.5', 'real', 'real', 'alpgr'),
('gratio', '0.01', 'real', 'real', 'gratio'),
('iforh2', '0', 'integer', 'integer', 'iforh2'),
('istic', '4', 'integer', 'integer', 'istic'),
('los_ext', 'Galaxy', 'string', 'string', 'los_ext'),
('rgrmax', '3.0e-5', 'real', 'real', 'rgrmax'),
('rgmin', '3e-7', 'real', 'real', 'rgmin'),
('cdunit', '5.8e21', 'real', 'real', 'cdunit'),
('Chimie', 'ch1109_cnos_ER_Mathis.chi', 'string', 'string', 'Chimie'),
('rrr', '3.1', 'real', 'real', 'rrr'),
('Fisrf', '1', 'integer', 'integer', 'Fisrf'),
('ichh2', '2', 'integer', 'integer', 'ichh2'),
('densh', '100', 'real', 'real', 'densh'),
('fprofil', 'none.pfl', 'string', 'string', 'fprofil');

-- --------------------------------------------------------

--
-- Table structure for table `GlobalTechConfig`
--

CREATE TABLE IF NOT EXISTS `GlobalTechConfig` (
  `MaxResultDuration` int(11) NOT NULL
) TYPE=MyISAM;

--
-- Dumping data for table `GlobalTechConfig`
--

INSERT INTO `GlobalTechConfig` (`MaxResultDuration`) VALUES
(100);

-- --------------------------------------------------------

--
-- Table structure for table `Inputs`
--

CREATE TABLE IF NOT EXISTS `Inputs` (
  `fileExtension` varchar(20) NOT NULL,
  `filePattern` blob NOT NULL,
  `InputDir` varchar(100) NOT NULL
) TYPE=MyISAM;

--
-- Dumping data for table `Inputs`
--

INSERT INTO `Inputs` (`fileExtension`, `filePattern`, `InputDir`) VALUES
('in', 0x52756e49640a4368696d69650a696661666d0a41766d61780a64656e73680a46697372660a5261646d0a526164700a73726370700a64736f75720a466d72630a69657174680a5467617a0a696669736f620a6670726f66696c0a5072657373650a76747572620a6974726665720a6a6667316b68320a69636868320a6c6f735f6578740a7272720a6364756e69740a67726174696f0a616c7067720a72676d696e0a7267726d61780a465f64757374656d0a69666f7268320a69737469630a, '/Users/zwolf/Work');

-- --------------------------------------------------------

--
-- Table structure for table `Job`
--

CREATE TABLE IF NOT EXISTS `Job` (
  `IdConfig` int(11) NOT NULL,
  `IdService` int(11) NOT NULL,
  `Processed` tinyint(1) NOT NULL,
  `Finished` tinyint(1) NOT NULL,
  `DemandDate` varchar(50) DEFAULT NULL,
  `ProcessingDate` varchar(50) DEFAULT NULL,
  `FinishingDate` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdConfig`)
) TYPE=MyISAM;

--
-- Dumping data for table `Job`
--


-- --------------------------------------------------------

--
-- Table structure for table `MailConfig`
--

CREATE TABLE IF NOT EXISTS `MailConfig` (
  `ServerName` varchar(100) NOT NULL,
  `UserName` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `FromAdress` varchar(100) NOT NULL,
  `FromLabel` varchar(100) NOT NULL,
  `Subject` varchar(200) NOT NULL
) TYPE=MyISAM;

--
-- Dumping data for table `MailConfig`
--

INSERT INTO `MailConfig` (`ServerName`, `UserName`, `Password`, `FromAdress`, `FromLabel`, `Subject`) VALUES
('smtp-m.obspm.fr', 'vo-pdl', '5tv!!@rs0o', 'vo-pdl@obspm.fr', 'PDL Service', 'PDL-OV Service Results');

-- --------------------------------------------------------

--
-- Table structure for table `Notifications`
--

CREATE TABLE IF NOT EXISTS `Notifications` (
  `IdUser` int(11) NOT NULL,
  `IdConfig` int(11) NOT NULL,
  `Notified` tinyint(1) NOT NULL,
  `DemandDate` varchar(50) DEFAULT NULL,
  `NotificationDate` varchar(50) DEFAULT NULL
) TYPE=MyISAM;

--
-- Dumping data for table `Notifications`
--


-- --------------------------------------------------------

--
-- Table structure for table `Outputs`
--

CREATE TABLE IF NOT EXISTS `Outputs` (
  `FileExtension` varchar(30) NOT NULL,
  `OutputDir` varchar(30) NOT NULL
) TYPE=MyISAM;

--
-- Dumping data for table `Outputs`
--

INSERT INTO `Outputs` (`FileExtension`, `OutputDir`) VALUES
('toto', '/Users/zwolf/Work'),
('titi', '/Users/zwolf/Work');

-- --------------------------------------------------------

--
-- Table structure for table `ParamLimits`
--

CREATE TABLE IF NOT EXISTS `ParamLimits` (
  `ParamName` varchar(30) NOT NULL,
  `InfLimit` varchar(10) NOT NULL,
  `SupLimit` varchar(20) NOT NULL
) TYPE=MyISAM;

--
-- Dumping data for table `ParamLimits`
--

INSERT INTO `ParamLimits` (`ParamName`, `InfLimit`, `SupLimit`) VALUES
('ifafm', '6', '100'),
('Avmax', '1E-4', '300'),
('ieqth', '0', '1'),
('F_dustem', '0', '1'),
('ifisob', '0', '2'),
('iforh2', '0', '3'),
('istic', '1', '4'),
('itrfer', '0', '2'),
('jfg1kh2', '0', '7'),
('Presse', '1e3', '1e10'),
('Radm', '0', '1e8'),
('Radp', '0', '1e8'),
('Tgaz', '2.7', '1e4'),
('vturb', '0.1', '100'),
('Fisrf', '1', '2'),
('ichh2', '0', '3'),
('densh', '0.1', '1e7');

-- --------------------------------------------------------

--
-- Table structure for table `Results`
--

CREATE TABLE IF NOT EXISTS `Results` (
  `IdConfig` int(11) NOT NULL,
  `URLResults` varchar(100) NOT NULL
) TYPE=MyISAM;

--
-- Dumping data for table `Results`
--


-- --------------------------------------------------------

--
-- Table structure for table `Service`
--

CREATE TABLE IF NOT EXISTS `Service` (
  `IdService` int(11) NOT NULL,
  `description` varchar(500) NOT NULL,
  `MaxSimAutorized` int(11) NOT NULL,
  PRIMARY KEY (`IdService`)
) TYPE=MyISAM;

--
-- Dumping data for table `Service`
--

INSERT INTO `Service` (`IdService`, `description`, `MaxSimAutorized`) VALUES
(1, 'PDR-beta', 100);

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `IdUser` int(11) NOT NULL AUTO_INCREMENT,
  `Email` varchar(200) NOT NULL,
  PRIMARY KEY (`IdUser`)
) TYPE=MyISAM  AUTO_INCREMENT=2 ;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`IdUser`, `Email`) VALUES
(1, 'carlo-maria.zwolf@obspm.fr');