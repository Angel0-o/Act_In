-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 09, 2018 at 06:47 PM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.2.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pokemon`
--
CREATE DATABASE IF NOT EXISTS `pokemon` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
USE `pokemon`;

-- --------------------------------------------------------

--
-- Table structure for table `datos`
--

CREATE TABLE `datos` (
  `num` int(11) NOT NULL,
  `nombre` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `tipo1` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `tipo2` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `hp` int(11) NOT NULL,
  `ataque` int(11) NOT NULL,
  `defensa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `datos`
--

INSERT INTO `datos` (`num`, `nombre`, `tipo1`, `tipo2`, `hp`, `ataque`, `defensa`) VALUES
(1, 'bulbasaur', 'poison', 'grass', 45, 49, 49),
(2, 'ivysaur', 'poison', 'grass', 60, 62, 63),
(3, 'venusaur', 'poison', 'grass', 80, 82, 83),
(4, 'charmander', 'fire', '(None)', 39, 52, 43),
(5, 'charmeleon', 'fire', '(None)', 58, 64, 58),
(6, 'charizard', 'flying', 'fire', 78, 84, 78),
(7, 'squirtle', 'water', '(None)', 44, 48, 65),
(8, 'wartortle', 'water', '(None)', 59, 63, 80),
(9, 'blastoise', 'water', '(None)', 79, 83, 100),
(10, 'caterpie', 'bug', '(None)', 45, 30, 35),
(11, 'metapod', 'bug', '(None)', 50, 20, 55),
(12, 'butterfree', 'flying', 'bug', 60, 45, 50),
(13, 'weedle', 'poison', 'bug', 40, 35, 30),
(14, 'kakuna', 'poison', 'bug', 45, 25, 50),
(15, 'beedrill', 'poison', 'bug', 65, 90, 40),
(16, 'pidgey', 'flying', 'normal', 40, 45, 40),
(17, 'pidgeotto', 'flying', 'normal', 63, 60, 55),
(18, 'pidgeot', 'flying', 'normal', 83, 80, 75),
(19, 'rattata', 'normal', '(None)', 30, 56, 35),
(20, 'raticate', 'normal', '(None)', 55, 81, 60),
(21, 'spearow', 'flying', 'normal', 40, 60, 30),
(22, 'fearow', 'flying', 'normal', 65, 90, 65),
(23, 'ekans', 'poison', '(None)', 35, 60, 44),
(24, 'arbok', 'poison', '(None)', 60, 95, 69),
(25, 'pikachu', 'electric', '(None)', 35, 55, 40),
(26, 'raichu', 'electric', '(None)', 60, 90, 55),
(27, 'sandshrew', 'ground', '(None)', 50, 75, 85),
(28, 'sandslash', 'ground', '(None)', 75, 100, 110),
(29, 'nidoran-f', 'poison', '(None)', 55, 47, 52),
(30, 'nidorina', 'poison', '(None)', 70, 62, 67),
(31, 'nidoqueen', 'ground', 'poison', 90, 92, 87),
(32, 'nidoran-m', 'poison', '(None)', 46, 57, 40),
(33, 'nidorino', 'poison', '(None)', 61, 72, 57),
(34, 'nidoking', 'ground', 'poison', 81, 102, 77),
(35, 'clefairy', 'fairy', '(None)', 70, 45, 48),
(36, 'clefable', 'fairy', '(None)', 95, 70, 73),
(37, 'vulpix', 'fire', '(None)', 38, 41, 40),
(38, 'ninetales', 'fire', '(None)', 73, 76, 75),
(39, 'jigglypuff', 'fairy', 'normal', 115, 45, 20),
(40, 'wigglytuff', 'fairy', 'normal', 140, 70, 45),
(41, 'zubat', 'flying', 'poison', 40, 45, 35),
(42, 'golbat', 'flying', 'poison', 75, 80, 70),
(43, 'oddish', 'poison', 'grass', 45, 50, 55),
(44, 'gloom', 'poison', 'grass', 60, 65, 70),
(45, 'vileplume', 'poison', 'grass', 75, 80, 85),
(46, 'paras', 'grass', 'bug', 35, 70, 55),
(47, 'parasect', 'grass', 'bug', 60, 95, 80),
(48, 'venonat', 'poison', 'bug', 60, 55, 50),
(49, 'venomoth', 'poison', 'bug', 70, 65, 60),
(50, 'diglett', 'ground', '(None)', 10, 55, 25),
(51, 'dugtrio', 'ground', '(None)', 35, 100, 50),
(52, 'meowth', 'normal', '(None)', 40, 45, 35),
(53, 'persian', 'normal', '(None)', 65, 70, 60),
(54, 'psyduck', 'water', '(None)', 50, 52, 48),
(55, 'golduck', 'water', '(None)', 80, 82, 78),
(56, 'mankey', 'fighting', '(None)', 40, 80, 35),
(57, 'primeape', 'fighting', '(None)', 65, 105, 60),
(58, 'growlithe', 'fire', '(None)', 55, 70, 45),
(59, 'arcanine', 'fire', '(None)', 90, 110, 80),
(60, 'poliwag', 'water', '(None)', 40, 50, 40),
(61, 'poliwhirl', 'water', '(None)', 65, 65, 65),
(62, 'poliwrath', 'fighting', 'water', 90, 95, 95),
(63, 'abra', 'psychic', '(None)', 25, 20, 15),
(64, 'kadabra', 'psychic', '(None)', 40, 35, 30),
(65, 'alakazam', 'psychic', '(None)', 55, 50, 45),
(66, 'machop', 'fighting', '(None)', 70, 80, 50),
(67, 'machoke', 'fighting', '(None)', 80, 100, 70),
(68, 'machamp', 'fighting', '(None)', 90, 130, 80),
(69, 'bellsprout', 'poison', 'grass', 50, 75, 35),
(70, 'weepinbell', 'poison', 'grass', 65, 90, 50),
(71, 'victreebel', 'poison', 'grass', 80, 105, 65),
(72, 'tentacool', 'poison', 'water', 40, 40, 35),
(73, 'tentacruel', 'poison', 'water', 80, 70, 65),
(74, 'geodude', 'ground', 'rock', 40, 80, 100),
(75, 'graveler', 'ground', 'rock', 55, 95, 115),
(76, 'golem', 'ground', 'rock', 80, 120, 130),
(77, 'ponyta', 'fire', '(None)', 50, 85, 55),
(78, 'rapidash', 'fire', '(None)', 65, 100, 70),
(79, 'slowpoke', 'psychic', 'water', 90, 65, 65),
(80, 'slowbro', 'psychic', 'water', 95, 75, 110),
(81, 'magnemite', 'steel', 'electric', 25, 35, 70),
(82, 'magneton', 'steel', 'electric', 50, 60, 95),
(83, 'farfetchd', 'flying', 'normal', 52, 90, 55),
(84, 'doduo', 'flying', 'normal', 35, 85, 45),
(85, 'dodrio', 'flying', 'normal', 60, 110, 70),
(86, 'seel', 'water', '(None)', 65, 45, 55),
(87, 'dewgong', 'ice', 'water', 90, 70, 80),
(88, 'grimer', 'poison', '(None)', 80, 80, 50),
(89, 'muk', 'poison', '(None)', 105, 105, 75),
(90, 'shellder', 'water', '(None)', 30, 65, 100),
(91, 'cloyster', 'ice', 'water', 50, 95, 180),
(92, 'gastly', 'poison', 'ghost', 30, 35, 30),
(93, 'haunter', 'poison', 'ghost', 45, 50, 45),
(94, 'gengar', 'poison', 'ghost', 60, 65, 60),
(95, 'onix', 'ground', 'rock', 35, 45, 160),
(96, 'drowzee', 'psychic', '(None)', 60, 48, 45),
(97, 'hypno', 'psychic', '(None)', 85, 73, 70),
(98, 'krabby', 'water', '(None)', 30, 105, 90),
(99, 'kingler', 'water', '(None)', 55, 130, 115),
(100, 'voltorb', 'electric', '(None)', 40, 30, 50),
(101, 'electrode', 'electric', '(None)', 60, 50, 70),
(102, 'exeggcute', 'psychic', 'grass', 60, 40, 80),
(103, 'exeggutor', 'psychic', 'grass', 95, 95, 85),
(104, 'cubone', 'ground', '(None)', 50, 50, 95),
(105, 'marowak', 'ground', '(None)', 60, 80, 110),
(106, 'hitmonlee', 'fighting', '(None)', 50, 120, 53),
(107, 'hitmonchan', 'fighting', '(None)', 50, 105, 79),
(108, 'lickitung', 'normal', '(None)', 90, 55, 75),
(109, 'koffing', 'poison', '(None)', 40, 65, 95),
(110, 'weezing', 'poison', '(None)', 65, 90, 120),
(111, 'rhyhorn', 'rock', 'ground', 80, 85, 95),
(112, 'rhydon', 'rock', 'ground', 105, 130, 120),
(113, 'chansey', 'normal', '(None)', 250, 5, 5),
(114, 'tangela', 'grass', '(None)', 65, 55, 115),
(115, 'kangaskhan', 'normal', '(None)', 105, 95, 80),
(116, 'horsea', 'water', '(None)', 30, 40, 70),
(117, 'seadra', 'water', '(None)', 55, 65, 95),
(118, 'goldeen', 'water', '(None)', 45, 67, 60),
(119, 'seaking', 'water', '(None)', 80, 92, 65),
(120, 'staryu', 'water', '(None)', 30, 45, 55),
(121, 'starmie', 'psychic', 'water', 60, 75, 85),
(122, 'mr-mime', 'fairy', 'psychic', 40, 45, 65),
(123, 'scyther', 'flying', 'bug', 70, 110, 80),
(124, 'jynx', 'psychic', 'ice', 65, 50, 35),
(125, 'electabuzz', 'electric', '(None)', 65, 83, 57),
(126, 'magmar', 'fire', '(None)', 65, 95, 57),
(127, 'pinsir', 'bug', '(None)', 65, 125, 100),
(128, 'tauros', 'normal', '(None)', 75, 100, 95),
(129, 'magikarp', 'water', '(None)', 20, 10, 55),
(130, 'gyarados', 'flying', 'water', 95, 125, 79),
(131, 'lapras', 'ice', 'water', 130, 85, 80),
(132, 'ditto', 'normal', '(None)', 48, 48, 48),
(133, 'eevee', 'normal', '(None)', 55, 55, 50),
(134, 'vaporeon', 'water', '(None)', 130, 65, 60),
(135, 'jolteon', 'electric', '(None)', 65, 65, 60),
(136, 'flareon', 'fire', '(None)', 65, 130, 60),
(137, 'porygon', 'normal', '(None)', 65, 60, 70),
(138, 'omanyte', 'water', 'rock', 35, 40, 100),
(139, 'omastar', 'water', 'rock', 70, 60, 125),
(140, 'kabuto', 'water', 'rock', 30, 80, 90),
(141, 'kabutops', 'water', 'rock', 60, 115, 105),
(142, 'aerodactyl', 'flying', 'rock', 80, 105, 65),
(143, 'snorlax', 'normal', '(None)', 160, 110, 65),
(144, 'articuno', 'flying', 'ice', 90, 85, 100),
(145, 'zapdos', 'flying', 'electric', 90, 90, 85),
(146, 'moltres', 'flying', 'fire', 90, 100, 90),
(147, 'dratini', 'dragon', '(None)', 41, 64, 45),
(148, 'dragonair', 'dragon', '(None)', 61, 84, 65),
(149, 'dragonite', 'flying', 'dragon', 91, 134, 95),
(150, 'mewtwo', 'psychic', '(None)', 106, 110, 90),
(151, 'mew', 'psychic', '(None)', 100, 100, 100);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `datos`
--
ALTER TABLE `datos`
  ADD PRIMARY KEY (`num`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
