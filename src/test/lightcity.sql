-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 01, 2023 at 09:16 PM
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
-- Table structure for table `bank-account`
--

CREATE TABLE `bank-account` (
  `username` text NOT NULL,
  `password` text NOT NULL,
  `money` float NOT NULL,
  `last` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bank-account`
--

INSERT INTO `bank-account` (`username`, `password`, `money`, `last`) VALUES
('test5', '8888', 0, 'Wed Jun 28 16:40:58 GMT+03:30 2023'),
('test6', '4785', 0, 'Wed Jun 28 16:43:31 GMT+03:30 2023'),
('test7', '12354', 0, 'Wed Jun 28 16:44:40 GMT+03:30 2023'),
('test8', '1235', 0, 'Wed Jun 28 16:57:06 GMT+03:30 2023'),
('test9', '1239', 10, 'Fri Jun 30 03:12:47 GMT+03:30 2023'),
('test10', '12310', 0, 'Fri Jun 30 03:14:54 GMT+03:30 2023'),
('tcccc', 'cssc', 10, 'Fri Jun 30 19:23:08 GMT+03:30 2023'),
('test13', '12313', 10, 'Sat Jul 01 16:38:41 GMT+03:30 2023'),
('user', 'pass', 10, 'Sat Jul 01 22:06:27 GMT+03:30 2023'),
('new', '4444', 10, 'Sat Jul 01 22:40:13 GMT+03:30 2023');

-- --------------------------------------------------------

--
-- Table structure for table `characters`
--

CREATE TABLE `characters` (
  `username` text NOT NULL,
  `password` text NOT NULL,
  `money` float NOT NULL,
  `life` text NOT NULL,
  `job` text NOT NULL,
  `location` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `characters`
--

INSERT INTO `characters` (`username`, `password`, `money`, `life`, `job`, `location`) VALUES
('test7', '12354', 0, '100.0,100.0,100.0', 'null', 'null'),
('test8', '1235', 3.5, '106.92998,64.46005,102.19995', 'null', '20.0,170.0'),
('test9', '1239', 0, '100.0,100.0,100.0', 'null', 'null'),
('test10', '12310', 5.5, '85.32996,58.160046,79.99992', 'machin-buy', '120.0,220.0'),
('tcccc', 'cssc', 10, '100.0,100.0,100.0', 'null', 'null'),
('test13', '12313', 10, '100.0,100.0,100.0', 'null', 'null'),
('user', 'pass', 10, '99.2,98.600006,98.399994', 'null', '20.0,170.0'),
('new', '4444', 10, '100.0,100.0,100.0', 'null', 'null');

-- --------------------------------------------------------

--
-- Table structure for table `city`
--

CREATE TABLE `city` (
  `isCity` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `username` text NOT NULL,
  `industry_title` text NOT NULL,
  `base_salary` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `industry`
--

CREATE TABLE `industry` (
  `title` text NOT NULL,
  `cordinate_x` int(11) NOT NULL,
  `cordinate_y` int(11) NOT NULL,
  `owner` text NOT NULL,
  `income` float NOT NULL,
  `employees` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `job`
--

CREATE TABLE `job` (
  `title` text NOT NULL,
  `income` float NOT NULL,
  `inid` text NOT NULL,
  `username` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `ForSale` tinyint(1) NOT NULL DEFAULT 1,
  `price` float NOT NULL DEFAULT 4.5,
  `indtitle` text NOT NULL DEFAULT '\'not-industry\''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `requset`
--

CREATE TABLE `requset` (
  `oldowner` text NOT NULL,
  `newowner` text NOT NULL,
  `coordinate` text NOT NULL,
  `price` float NOT NULL,
  `id` int(11) NOT NULL COMMENT '	AUTO_INCREMENT'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
('new', '4444'),
('test10', '12310'),
('test8', '1235'),
('user', 'pass');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `properties`
--
ALTER TABLE `properties`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `requset`
--
ALTER TABLE `requset`
  ADD UNIQUE KEY `id` (`id`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1084524;

--
-- AUTO_INCREMENT for table `requset`
--
ALTER TABLE `requset`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '	AUTO_INCREMENT', AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
