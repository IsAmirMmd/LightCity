-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 25, 2023 at 03:35 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lightcity`
--

-- --------------------------------------------------------

--
-- Table structure for table `properties`
--

CREATE TABLE `properties` (
  `id` int(11) NOT NULL,
  `width` float NOT NULL,
  `height` float NOT NULL,
  `x_coordinate` float NOT NULL,
  `y_coordinate` float NOT NULL,
  `owner` varchar(100) DEFAULT NULL,
  `ForSale` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `properties`
--

INSERT INTO `properties` (`id`, `width`, `height`, `x_coordinate`, `y_coordinate`, `owner`, `ForSale`) VALUES
(1084389, 40, 40, 20, 20, 'mayor', 1),
(1084390, 40, 40, 70, 20, 'mayor', 1),
(1084391, 40, 40, 120, 20, 'mayor', 1),
(1084392, 40, 40, 20, 70, 'mayor', 1),
(1084393, 40, 40, 70, 70, 'mayor', 1),
(1084394, 40, 40, 120, 70, 'mayor', 1),
(1084395, 40, 40, 20, 120, 'mayor', 1),
(1084396, 40, 40, 70, 120, 'mayor', 1),
(1084397, 40, 40, 120, 120, 'mayor', 1),
(1084398, 40, 40, 20, 170, 'mayor', 1),
(1084399, 40, 40, 70, 170, 'mayor', 1),
(1084400, 40, 40, 120, 170, 'mayor', 1),
(1084401, 40, 40, 20, 220, 'mayor', 1),
(1084402, 40, 40, 70, 220, 'mayor', 1),
(1084403, 40, 40, 120, 220, 'mayor', 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `password`) VALUES
('amir', '1234');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `properties`
--
ALTER TABLE `properties`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `properties`
--
ALTER TABLE `properties`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1084404;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
