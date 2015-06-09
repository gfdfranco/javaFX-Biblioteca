-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-05-2015 a las 06:47:25
-- Versión del servidor: 5.6.24
-- Versión de PHP: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `intranet`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administrador`
--

CREATE TABLE IF NOT EXISTS `administrador` (
  `NOMBRE` varchar(50) NOT NULL,
  `CLAVE` int(4) NOT NULL,
  `EMAIL` varchar(30) NOT NULL,
  `TEL` varchar(10) NOT NULL,
  `PASSWORD` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `administrador`
--

INSERT INTO `administrador` (`NOMBRE`, `CLAVE`, `EMAIL`, `TEL`, `PASSWORD`) VALUES
('JOSE RAMON TOLENTINO', 1, 'TOLENTINO@HOTMAIL.COM', '4441023658', 'tole123');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumno`
--

CREATE TABLE IF NOT EXISTS `alumno` (
  `NOMBRE` varchar(50) NOT NULL,
  `MATRICULA` int(6) NOT NULL,
  `TEL` varchar(10) NOT NULL,
  `DIRECCION` varchar(50) NOT NULL,
  `CARRERA` enum('ITI','ITEM','ITMA','ISTI','LAG','LMKT') NOT NULL,
  `EMAIL` varchar(30) NOT NULL,
  `PASSWORD` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `alumno`
--

INSERT INTO `alumno` (`NOMBRE`, `MATRICULA`, `TEL`, `DIRECCION`, `CARRERA`, `EMAIL`, `PASSWORD`) VALUES
('OSCAR EMMANUEL YANEZ HERNANDEZ', 130034, '85201456', 'DIRECCION DE OSCAR #76', 'ITI', 'OSCAR@HOTMAIL.COM', 'oscar123'),
('MIGUEL ANGEL SILVA MARTINEZ', 130454, '4445210236', 'DIRECCION DE MIKE #23', 'ITI', 'MIKE@HOTMAIL.COM', 'mike123');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `calificaciones`
--

CREATE TABLE IF NOT EXISTS `calificaciones` (
  `CLAVE_MATERIA` int(4) NOT NULL,
  `MATRICULA_ALUM` int(6) NOT NULL,
  `CAL1` float DEFAULT NULL,
  `CAL2` float DEFAULT NULL,
  `CAL3` float DEFAULT NULL,
  `FINAL` float DEFAULT NULL,
  `EXTRA` float DEFAULT NULL,
  `INASISTENCIAS` int(2) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `calificaciones`
--

INSERT INTO `calificaciones` (`CLAVE_MATERIA`, `MATRICULA_ALUM`, `CAL1`, `CAL2`, `CAL3`, `FINAL`, `EXTRA`, `INASISTENCIAS`) VALUES
(1, 130034, 8, NULL, NULL, NULL, NULL, 1),
(1, 130454, 5, NULL, NULL, NULL, NULL, 3),
(5, 130454, NULL, NULL, NULL, NULL, NULL, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `materia`
--

CREATE TABLE IF NOT EXISTS `materia` (
  `NOMBRE` varchar(25) NOT NULL,
  `CLAVE` int(4) NOT NULL,
  `CLAVE_PROFESOR` int(4) NOT NULL,
  `AULA` varchar(3) NOT NULL,
  `HORA` varchar(25) NOT NULL,
  `CARRERA` enum('ITI','ITEM','ITMA','ISTI','LAG','LMKT') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `materia`
--

INSERT INTO `materia` (`NOMBRE`, `CLAVE`, `CLAVE_PROFESOR`, `AULA`, `HORA`, `CARRERA`) VALUES
('CIRCUITOS', 1, 9, '53', '13:00-14:00', 'ITI'),
('MATEMATICAS 4', 5, 32, '12', '08:00-09:00', 'ITI');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parcial_activo`
--

CREATE TABLE IF NOT EXISTS `parcial_activo` (
  `activo` enum('1','2','3','final','extra') NOT NULL,
  `id` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `parcial_activo`
--

INSERT INTO `parcial_activo` (`activo`, `id`) VALUES
('1', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesor`
--

CREATE TABLE IF NOT EXISTS `profesor` (
  `NOMBRE` varchar(50) NOT NULL,
  `RFC` varchar(10) NOT NULL,
  `TEL` varchar(10) NOT NULL,
  `DIRECCION` varchar(50) NOT NULL,
  `CLAVE` int(4) NOT NULL,
  `EMAIL` varchar(30) NOT NULL,
  `PASSWORD` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `profesor`
--

INSERT INTO `profesor` (`NOMBRE`, `RFC`, `TEL`, `DIRECCION`, `CLAVE`, `EMAIL`, `PASSWORD`) VALUES
('AGUSTIN IRVIN GARCIA PEREZ', 'RFCAGUS123', '4448521025', 'DIRECCION DE AGUSTIN #12', 9, 'AGUS@HOTMAIL.COM', 'agus123'),
('GERARDO FRANCO DELGADO', 'RFCGERAS85', '85410265', 'DIRECCION DE GERARDO #12', 32, 'GERARDO@HOTMAIL.COM', 'geras123');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `administrador`
--
ALTER TABLE `administrador`
  ADD PRIMARY KEY (`CLAVE`);

--
-- Indices de la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD PRIMARY KEY (`MATRICULA`);

--
-- Indices de la tabla `calificaciones`
--
ALTER TABLE `calificaciones`
  ADD UNIQUE KEY `calKey` (`CLAVE_MATERIA`,`MATRICULA_ALUM`), ADD KEY `MATRICULA_ALUM` (`MATRICULA_ALUM`);

--
-- Indices de la tabla `materia`
--
ALTER TABLE `materia`
  ADD PRIMARY KEY (`CLAVE`), ADD KEY `CLAVE_PROFESOR` (`CLAVE_PROFESOR`);

--
-- Indices de la tabla `profesor`
--
ALTER TABLE `profesor`
  ADD PRIMARY KEY (`CLAVE`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `calificaciones`
--
ALTER TABLE `calificaciones`
ADD CONSTRAINT `calificaciones_ibfk_1` FOREIGN KEY (`CLAVE_MATERIA`) REFERENCES `materia` (`CLAVE`),
ADD CONSTRAINT `calificaciones_ibfk_2` FOREIGN KEY (`MATRICULA_ALUM`) REFERENCES `alumno` (`MATRICULA`);

--
-- Filtros para la tabla `materia`
--
ALTER TABLE `materia`
ADD CONSTRAINT `materia_ibfk_1` FOREIGN KEY (`CLAVE_PROFESOR`) REFERENCES `profesor` (`CLAVE`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
