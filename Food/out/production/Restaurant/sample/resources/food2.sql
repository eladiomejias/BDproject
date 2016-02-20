-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 17-02-2016 a las 17:56:19
-- Versión del servidor: 5.6.20-log
-- Versión de PHP: 5.4.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `food`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `auxiliar`
--

CREATE TABLE IF NOT EXISTS `auxiliar` (
  `idAuxiliar` int(11) NOT NULL,
  `FechaNacimiento` varchar(45) DEFAULT NULL,
  `Empleados_idEmpleados` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `auxiliar`
--

INSERT INTO `auxiliar` (`idAuxiliar`, `FechaNacimiento`, `Empleados_idEmpleados`) VALUES
(1, 'Diciembre 1990', 4),
(2, 'December 2000', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cocinero`
--

CREATE TABLE IF NOT EXISTS `cocinero` (
  `idCocinero` int(11) NOT NULL,
  `AñosServicio` int(11) DEFAULT NULL,
  `Empleados_idEmpleados` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `cocinero`
--

INSERT INTO `cocinero` (`idCocinero`, `AñosServicio`, `Empleados_idEmpleados`) VALUES
(1, 25, 1),
(2, 10, 2),
(3, 20, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cocineroconplatos`
--

CREATE TABLE IF NOT EXISTS `cocineroconplatos` (
  `Cocinero_idCocinero` int(11) NOT NULL,
  `Plato_Nombre` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `cocineroconplatos`
--

INSERT INTO `cocineroconplatos` (`Cocinero_idCocinero`, `Plato_Nombre`) VALUES
(1, 'California Roll'),
(1, 'Pasta Boloñesa'),
(2, 'California Roll'),
(2, 'Pasta Boloñesa');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

CREATE TABLE IF NOT EXISTS `empleados` (
  `idEmpleados` int(11) NOT NULL,
  `Nombre` varchar(45) DEFAULT NULL,
  `Telefono` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `empleados`
--

INSERT INTO `empleados` (`idEmpleados`, `Nombre`, `Telefono`) VALUES
(1, 'Eladio Mejias', '04244681148'),
(2, 'Samir Mahomud', '04244567654'),
(3, 'David ', '04123452231'),
(4, 'Samuel Murillo', '04125678899'),
(5, 'Gregory Gregorio', '12123456543');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ingrediente`
--

CREATE TABLE IF NOT EXISTS `ingrediente` (
  `Nombre` varchar(45) NOT NULL,
  `Cantidad` int(11) DEFAULT NULL,
  `DP` int(11) DEFAULT NULL,
  `DS` int(11) DEFAULT NULL,
  `CF` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `ingrediente`
--

INSERT INTO `ingrediente` (`Nombre`, `Cantidad`, `DP`, `DS`, `CF`) VALUES
('Aji', 20, 0, 1, 0),
('Atun', 11, 0, 0, 1),
('Carne Mechada', 12, 0, 0, 1),
('Papa', 25, 0, 1, 0),
('Platanos', 2, 0, 1, 0),
('Salmon', 1, 0, 0, 1),
('Tomate', 12, 1, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `plato`
--

CREATE TABLE IF NOT EXISTS `plato` (
  `Nombre` varchar(45) NOT NULL,
  `Tipo` varchar(45) DEFAULT NULL,
  `Precio` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `plato`
--

INSERT INTO `plato` (`Nombre`, `Tipo`, `Precio`) VALUES
('California Roll', 'Sushi', '2500bsf'),
('Pasta Boloñesa', 'Pasta', '1300bs'),
('Pasta Carbonara', 'Pasta', '1400bsf');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `platoconingredientes`
--

CREATE TABLE IF NOT EXISTS `platoconingredientes` (
  `Plato_Nombre` varchar(45) NOT NULL,
  `Ingrediente_Nombre` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `platoconingredientes`
--

INSERT INTO `platoconingredientes` (`Plato_Nombre`, `Ingrediente_Nombre`) VALUES
('California Roll', 'Platanos'),
('California Roll', 'Salmon'),
('Pasta Boloñesa', 'Tomate');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `auxiliar`
--
ALTER TABLE `auxiliar`
 ADD PRIMARY KEY (`idAuxiliar`), ADD KEY `fk_Auxiliar_Empleados1_idx` (`Empleados_idEmpleados`);

--
-- Indices de la tabla `cocinero`
--
ALTER TABLE `cocinero`
 ADD PRIMARY KEY (`idCocinero`), ADD KEY `fk_Cocinero_Empleados_idx` (`Empleados_idEmpleados`);

--
-- Indices de la tabla `cocineroconplatos`
--
ALTER TABLE `cocineroconplatos`
 ADD PRIMARY KEY (`Cocinero_idCocinero`,`Plato_Nombre`), ADD KEY `fk_Cocinero_has_Plato_Plato3_idx` (`Plato_Nombre`), ADD KEY `fk_Cocinero_has_Plato_Cocinero3_idx` (`Cocinero_idCocinero`);

--
-- Indices de la tabla `empleados`
--
ALTER TABLE `empleados`
 ADD PRIMARY KEY (`idEmpleados`);

--
-- Indices de la tabla `ingrediente`
--
ALTER TABLE `ingrediente`
 ADD PRIMARY KEY (`Nombre`);

--
-- Indices de la tabla `plato`
--
ALTER TABLE `plato`
 ADD PRIMARY KEY (`Nombre`);

--
-- Indices de la tabla `platoconingredientes`
--
ALTER TABLE `platoconingredientes`
 ADD PRIMARY KEY (`Plato_Nombre`,`Ingrediente_Nombre`), ADD KEY `fk_Plato_has_Ingrediente3_Ingrediente1_idx` (`Ingrediente_Nombre`), ADD KEY `fk_Plato_has_Ingrediente3_Plato1_idx` (`Plato_Nombre`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `auxiliar`
--
ALTER TABLE `auxiliar`
ADD CONSTRAINT `fk_Auxiliar_Empleados1` FOREIGN KEY (`Empleados_idEmpleados`) REFERENCES `empleados` (`idEmpleados`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `cocinero`
--
ALTER TABLE `cocinero`
ADD CONSTRAINT `fk_Cocinero_Empleados` FOREIGN KEY (`Empleados_idEmpleados`) REFERENCES `empleados` (`idEmpleados`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `cocineroconplatos`
--
ALTER TABLE `cocineroconplatos`
ADD CONSTRAINT `fk_Cocinero_has_Plato_Cocinero3` FOREIGN KEY (`Cocinero_idCocinero`) REFERENCES `cocinero` (`idCocinero`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_Cocinero_has_Plato_Plato3` FOREIGN KEY (`Plato_Nombre`) REFERENCES `plato` (`Nombre`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `platoconingredientes`
--
ALTER TABLE `platoconingredientes`
ADD CONSTRAINT `fk_Plato_has_Ingrediente3_Ingrediente1` FOREIGN KEY (`Ingrediente_Nombre`) REFERENCES `ingrediente` (`Nombre`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_Plato_has_Ingrediente3_Plato1` FOREIGN KEY (`Plato_Nombre`) REFERENCES `plato` (`Nombre`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
