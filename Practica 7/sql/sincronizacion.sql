-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 14-06-2018 a las 20:21:11
-- Versión del servidor: 10.1.31-MariaDB
-- Versión de PHP: 7.1.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sincronizacion`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipos`
--

CREATE TABLE `equipos` (
  `ID` int(11) NOT NULL,
  `IP` varchar(45) DEFAULT NULL,
  `Nombre` varchar(45) DEFAULT NULL,
  `Latencia` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `equipos`
--

INSERT INTO `equipos` (`ID`, `IP`, `Nombre`, `Latencia`) VALUES
(0, '127.0.0.1', 'Equipo 2', '0'),
(1, '127.0.0.1', 'Equipo 2', '0'),
(2, '127.0.0.1', 'Equipo 2', '0'),
(3, '127.0.0.1', 'Equipo 1', '0'),
(4, '127.0.0.1', 'Equipo 1', '1'),
(5, '127.0.0.1', 'Equipo 1', '0'),
(6, '127.0.0.1', 'Equipo 1', '1'),
(7, '127.0.0.1', 'Equipo 1', '0'),
(8, '127.0.0.1', 'Equipo 1', '1'),
(9, '127.0.0.1', 'Equipo 3', '0'),
(10, '127.0.0.1', 'Equipo 3', '0'),
(11, '127.0.0.1', 'Equipo 3', '0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `horacentral`
--

CREATE TABLE `horacentral` (
  `ID` int(11) NOT NULL,
  `hUTC` varchar(45) DEFAULT NULL,
  `hLocal` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `horacentral`
--

INSERT INTO `horacentral` (`ID`, `hUTC`, `hLocal`) VALUES
(0, '13:44:13', '13:44:13'),
(1, '13:44:15', '13:44:15'),
(2, '13:44:17', '13:44:17'),
(3, '13:50:52', '13:50:52'),
(4, '13:50:55', '13:50:55'),
(5, '13:50:58', '13:50:58'),
(6, '13:51:01', '13:51:01'),
(7, '13:51:04', '13:51:04'),
(8, '13:51:07', '13:51:07'),
(9, '13:56:00', '13:56:00'),
(10, '13:56:02', '13:56:02'),
(11, '13:56:04', '13:56:04');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `horaequipos`
--

CREATE TABLE `horaequipos` (
  `ID` int(11) NOT NULL,
  `IDhUTC` int(11) NOT NULL,
  `IDEquipo` int(11) NOT NULL,
  `hEquipo` varchar(45) DEFAULT NULL,
  `aEquipo` varchar(45) DEFAULT NULL,
  `ralentizar` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `horaequipos`
--

INSERT INTO `horaequipos` (`ID`, `IDhUTC`, `IDEquipo`, `hEquipo`, `aEquipo`, `ralentizar`) VALUES
(0, 0, 0, '1313:44:14', '13:44:13', 1),
(1, 1, 1, '13:44:14', '13:44:15', 1),
(2, 2, 2, '13:44:17', '13:44:17', 1),
(3, 3, 3, '13:50:51', '13:50:52', 1),
(4, 4, 4, '13:50:53', '13:50:56', 1),
(5, 5, 5, '13:50:57', '13:50:58', 1),
(6, 6, 6, '13:50:59', '13:51:02', 1),
(7, 7, 7, '13:51:3', '13:51:04', 1),
(8, 8, 8, '13:51:5', '13:51:08', 1),
(9, 9, 9, '1313:56:0', '13:56:00', 1),
(10, 10, 10, '13:56:1', '13:56:02', 1),
(11, 11, 11, '13:56:4', '13:56:04', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `equipos`
--
ALTER TABLE `equipos`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `horacentral`
--
ALTER TABLE `horacentral`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `horaequipos`
--
ALTER TABLE `horaequipos`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `idEquipo_idx` (`IDEquipo`),
  ADD KEY `IDhUTC_idx` (`IDhUTC`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `horaequipos`
--
ALTER TABLE `horaequipos`
  ADD CONSTRAINT `IDhUTC` FOREIGN KEY (`IDhUTC`) REFERENCES `horacentral` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `IdEquipo` FOREIGN KEY (`IDEquipo`) REFERENCES `equipos` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
