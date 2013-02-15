-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Ven 15 Février 2013 à 12:37
-- Version du serveur: 5.5.28
-- Version de PHP: 5.4.4-11

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `montage`
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

--
-- Contenu de la table `DefaultRawParameter`
--

INSERT INTO `DefaultRawParameter` (`RawParamName`, `RawParamValue`, `RawParamType`, `ParentType`, `ParentParamName`) VALUES
('CTYPE2', 'DEC--TAN', 'string', 'string', 'CTYPE2'),
('NAXIS1', '1024', 'integer', 'integer', 'NAXIS1'),
('NAXIS2', '1024', 'integer', 'integer', 'NAXIS2'),
('CTYPE1', 'RA--TAN', 'string', 'string', 'CTYPE1'),
('CDELT1', '0.001', 'real', 'real', 'CDELT1'),
('CRPIX2', '0', 'integer', 'integer', 'CRPIX2'),
('CRPIX1', '0', 'integer', 'integer', 'CRPIX1'),
('CRVAL2', '0', 'real', 'real', 'CRVAL2'),
('CRVAL1', '0', 'real', 'real', 'CRVAL1'),
('CDELT2', '0.001', 'real', 'real', 'CDELT2'),
('CROTA2', '0', 'real', 'real', 'CROTA2'),
('EQUINOX', '2000', 'string', 'string', 'EQUINOX'),
('ImageLocation', 'SampleLocation', 'string', 'string', 'ImageLocation');

-- --------------------------------------------------------

--
-- Structure de la table `GlobalTechConfig`
--

CREATE TABLE IF NOT EXISTS `GlobalTechConfig` (
  `MaxResultDuration` int(11) NOT NULL,
  `ServeletContainerAdress` varchar(300) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `GlobalTechConfig`
--

INSERT INTO `GlobalTechConfig` (`MaxResultDuration`, `ServeletContainerAdress`) VALUES
(10000, 'http://pdl-calc.obspm.fr:8081/montage/');

-- --------------------------------------------------------

--
-- Structure de la table `Inputs`
--

CREATE TABLE IF NOT EXISTS `Inputs` (
  `fileExtension` varchar(20) NOT NULL,
  `filePattern` blob NOT NULL,
  `InputDir` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `Inputs`
--

INSERT INTO `Inputs` (`fileExtension`, `filePattern`, `InputDir`) VALUES
('hdr', 0x53494d504c4520203d20540a42495450495820203d202d36340a4e415849532020203d20320a4e415849533120203d2024244e415849533124240a4e415849533220203d2024244e415849533224240a43545950453120203d202724244354595045312424270a43545950453220203d202724244354595045322424270a435256414c3120203d20202424435256414c3124240a435256414c3220203d2020202424435256414c3224240a4344454c543120203d20202024244344454c543124240a4344454c543220203d2020202024244344454c543224240a43525049583120203d202020202020242443525049583124240a43525049583220203d202020202020242443525049583224240a43524f54413220203d202020242443524f54413224240a454e440a, '/home/czwolf/montage/processingFolder/input'),
('sh', 0x23212f62696e2f626173680a232073637269707420666f722064726976696e67206d6f6e7461676520636f6d7075746174696f6e206f6e20657665727920776f726b696e67206e6f64650a6364202f6d6e742f6d6f6e746167652f696e7075742f0a726d202d726620242452756e496424242e73680a0a6d6b646972202f746d702f70726f63657373696e672d6d6f6e746167652d242452756e496424240a6d76202f6d6e742f6d6f6e746167652f696e7075742f242452756e496424242e686472202f746d702f70726f63657373696e672d6d6f6e746167652d242452756e496424242f0a6364202f746d702f70726f63657373696e672d6d6f6e746167652d242452756e496424242f0a6d6b6469722070726f6a65637465640a6d6b6469722066696e616c0a6c6e202d73202f6d6e742f6d6f6e746167652f7261772f2424496d6167654c6f636174696f6e2424207261770a0a23206d6f6e7461676520506172740a6d496d6774626c207261772072696d616765732e74626c203b0a6d50726f6a45786563202d70207261772072696d616765732e74626c20242452756e496424242e6864722070726f6a65637465642073746174732e74626c203b0a6d496d6774626c2070726f6a65637465642070696d616765732e74626c203b0a6d416464202d702070726f6a65637465642070696d616765732e74626c20242452756e496424242e6864722066696e616c2f242452756e496424242e66697473203b0a0a746172202d7a63766620242452756e496424242e74677a2066696e616c2f0a0a6d7620242452756e496424242e74677a202f6d6e742f6d6f6e746167652f6f75747075742f0a0a6364202f746d702f0a726d202d72662070726f63657373696e672d6d6f6e746167652d242452756e496424242f0a6364202f, '/home/czwolf/montage/processingFolder/input');

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

--
-- Contenu de la table `MailConfig`
--

INSERT INTO `MailConfig` (`ServerName`, `UserName`, `Password`, `FromAdress`, `FromLabel`, `Subject`) VALUES
('serverName', 'vo-pdl', 'password', 'vo-pdl@obspm.fr', 'Montage PDL Service', 'Montage PDL-OV Service');

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
  `OutputDir` varchar(100) NOT NULL,
  `ResultName` varchar(200) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `Outputs`
--

INSERT INTO `Outputs` (`FileExtension`, `OutputDir`, `ResultName`) VALUES
('tar', '/home/czwolf/montage/processingFolder/output', 'fileResult');

-- --------------------------------------------------------

--
-- Structure de la table `ParamLimits`
--

CREATE TABLE IF NOT EXISTS `ParamLimits` (
  `ParamName` varchar(30) NOT NULL,
  `InfLimit` varchar(10) NOT NULL,
  `SupLimit` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `ParamLimits`
--

INSERT INTO `ParamLimits` (`ParamName`, `InfLimit`, `SupLimit`) VALUES
('NAXIS2', '1', '100000'),
('NAXIS1', '1', '100000'),
('CRVAL1', '0', '360'),
('CRVAL2', '-90', '90');

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
  `URLResults` varchar(100) NOT NULL,
  `ResultName` varchar(200) NOT NULL
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

--
-- Contenu de la table `Service`
--

INSERT INTO `Service` (`IdService`, `description`, `MaxSimAutorized`) VALUES
(1, 'ImageMosaicingService', 1000);

-- --------------------------------------------------------

--
-- Structure de la table `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `IdUser` int(11) NOT NULL AUTO_INCREMENT,
  `Email` varchar(200) NOT NULL,
  PRIMARY KEY (`IdUser`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Contenu de la table `User`
--

INSERT INTO `User` (`IdUser`, `Email`) VALUES
(5, 'carlo-maria.zwolf@obspm.fr'),
(6, 'jer@iaa.es'),
(7, 'tetrarquis@gmail.com');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
