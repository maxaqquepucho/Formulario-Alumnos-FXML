-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 01-03-2018 a las 17:44:28
-- Versión del servidor: 10.1.29-MariaDB
-- Versión de PHP: 7.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db_alumnos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_alumnos`
--

CREATE TABLE `tbl_alumnos` (
  `codigo_alumno` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `edad` int(11) NOT NULL,
  `genero` varchar(1) DEFAULT NULL,
  `fecha_ingreso` date NOT NULL,
  `codigo_centro` int(11) NOT NULL,
  `codigo_carrera` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tbl_alumnos`
--

INSERT INTO `tbl_alumnos` (`codigo_alumno`, `nombre`, `apellido`, `edad`, `genero`, `fecha_ingreso`, `codigo_centro`, `codigo_carrera`) VALUES
(1, 'Alejandro', 'Rodriguez', 45, 'M', '2018-02-16', 1, 3),
(2, 'Juan', 'Perez', 56, 'M', '2018-02-26', 2, 4),
(3, 'Deisy', 'Machaca Trompe', 19, 'F', '2018-02-14', 2, 1),
(4, 'Jenny', 'Miller', 21, 'F', '2018-02-07', 1, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_carreras`
--

CREATE TABLE `tbl_carreras` (
  `codigo_carrera` int(11) NOT NULL,
  `nombre_carrera` varchar(100) NOT NULL,
  `cantidad_asignaturas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tbl_carreras`
--

INSERT INTO `tbl_carreras` (`codigo_carrera`, `nombre_carrera`, `cantidad_asignaturas`) VALUES
(1, 'ingenieria de sistemas', 45),
(2, 'Administracion', 25),
(3, 'ingenieria de Industrial', 43),
(4, 'Derecho', 46);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_centro_estudio`
--

CREATE TABLE `tbl_centro_estudio` (
  `codigo_centro` int(11) NOT NULL,
  `nombre_centro_estudio` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tbl_centro_estudio`
--

INSERT INTO `tbl_centro_estudio` (`codigo_centro`, `nombre_centro_estudio`) VALUES
(1, 'U. Cesar Vallejo'),
(2, 'U. San Marcos');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tbl_alumnos`
--
ALTER TABLE `tbl_alumnos`
  ADD PRIMARY KEY (`codigo_alumno`),
  ADD KEY `fk_tbl_alumnos_tbl_centro_estudio_idx` (`codigo_centro`),
  ADD KEY `fk_tbl_alumnos_tbl_carreras1_idx` (`codigo_carrera`);

--
-- Indices de la tabla `tbl_carreras`
--
ALTER TABLE `tbl_carreras`
  ADD PRIMARY KEY (`codigo_carrera`);

--
-- Indices de la tabla `tbl_centro_estudio`
--
ALTER TABLE `tbl_centro_estudio`
  ADD PRIMARY KEY (`codigo_centro`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tbl_alumnos`
--
ALTER TABLE `tbl_alumnos`
  MODIFY `codigo_alumno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `tbl_carreras`
--
ALTER TABLE `tbl_carreras`
  MODIFY `codigo_carrera` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `tbl_centro_estudio`
--
ALTER TABLE `tbl_centro_estudio`
  MODIFY `codigo_centro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tbl_alumnos`
--
ALTER TABLE `tbl_alumnos`
  ADD CONSTRAINT `fk_tbl_alumnos_tbl_carreras1` FOREIGN KEY (`codigo_carrera`) REFERENCES `tbl_carreras` (`codigo_carrera`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_tbl_alumnos_tbl_centro_estudio` FOREIGN KEY (`codigo_centro`) REFERENCES `tbl_centro_estudio` (`codigo_centro`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
