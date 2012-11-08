-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Jeu 08 Novembre 2012 à 14:52
-- Version du serveur: 5.5.24
-- Version de PHP: 5.4.4-7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `Broadening`
--

-- --------------------------------------------------------

--
-- Structure de la table `ConfigurationsDetails`
--

CREATE TABLE IF NOT EXISTS `ConfigurationsDetails` (
  `IdConfig` int(11) NOT NULL,
  `ParamName` varchar(50) NOT NULL,
  `ParamValue` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `DefaultRawParameter`
--

CREATE TABLE IF NOT EXISTS `DefaultRawParameter` (
  `RawParamName` varchar(30) NOT NULL,
  `RawParamValue` varchar(40) NOT NULL,
  `RawParamType` varchar(10) NOT NULL,
  `ParentType` varchar(10) NOT NULL,
  `ParentParamName` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `GlobalTechConfig`
--

CREATE TABLE IF NOT EXISTS `GlobalTechConfig` (
  `MaxResultDuration` int(11) NOT NULL,
  `ServeletContainerAdress` varchar(300) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `Inputs`
--

CREATE TABLE IF NOT EXISTS `Inputs` (
  `fileExtension` varchar(20) NOT NULL,
  `filePattern` blob NOT NULL,
  `InputDir` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `Job`
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
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `MailConfig`
--

CREATE TABLE IF NOT EXISTS `MailConfig` (
  `ServerName` varchar(100) NOT NULL,
  `UserName` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `FromAdress` varchar(100) NOT NULL,
  `FromLabel` varchar(100) NOT NULL,
  `Subject` varchar(200) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `Notifications`
--

CREATE TABLE IF NOT EXISTS `Notifications` (
  `IdUser` int(11) NOT NULL,
  `IdConfig` int(11) NOT NULL,
  `Notified` tinyint(1) NOT NULL,
  `DemandDate` varchar(50) DEFAULT NULL,
  `NotificationDate` varchar(50) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `Outputs`
--

CREATE TABLE IF NOT EXISTS `Outputs` (
  `FileExtension` varchar(100) NOT NULL,
  `OutputDir` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `ParamLimits`
--

CREATE TABLE IF NOT EXISTS `ParamLimits` (
  `ParamName` varchar(30) NOT NULL,
  `InfLimit` varchar(10) NOT NULL,
  `SupLimit` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `PermanentNotification`
--

CREATE TABLE IF NOT EXISTS `PermanentNotification` (
  `IdUser` int(11) NOT NULL,
  `IdConfig` int(11) NOT NULL,
  `DemandDate` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `Results`
--

CREATE TABLE IF NOT EXISTS `Results` (
  `IdConfig` int(11) NOT NULL,
  `URLResults` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `Service`
--

CREATE TABLE IF NOT EXISTS `Service` (
  `IdService` int(11) NOT NULL,
  `description` varchar(500) NOT NULL,
  `MaxSimAutorized` int(11) NOT NULL,
  PRIMARY KEY (`IdService`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `IdUser` int(11) NOT NULL AUTO_INCREMENT,
  `Email` varchar(200) NOT NULL,
  PRIMARY KEY (`IdUser`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
