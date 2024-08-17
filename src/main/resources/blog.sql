-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 16, 2024 at 04:25 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `java_thymeleaf_blog`
--

-- --------------------------------------------------------

--
-- Table structure for table `blog`
--

CREATE TABLE `blog` (
  `id` int(11) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `enabled` tinyint(4) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `blog`
--

INSERT INTO `blog` (`id`, `title`, `content`, `user_id`, `enabled`, `created_at`, `updated_at`) VALUES
(2, 'aguleri', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus consectetur vitae mauris sed ultrices. Integer a lectus eget augue malesuada tristique. Duis sed accumsan tellus. Nullam et lectus est. Nam ac sollicitudin elit. Curabitur in lorem at nulla rhoncus dictum. Pellentesque luctus, turpis at interdum pharetra, nulla quam sodales sem, id interdum urna dolor sed mi. Sed et porta ante, a consectetur mi. Nam a turpis cursus, gravida sapien ut, gravida nisi. Ut vitae tristique enim, gravida consectetur ligula. ', 1, 1, '2024-08-06 15:17:56', '2024-08-06 16:17:56'),
(3, 'Lorem', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus consectetur vitae mauris sed ultrices. Integer a lectus eget augue malesuada tristique. Duis sed accumsan tellus. Nullam et lectus est. ', 1, 1, '2024-08-06 16:25:07', '2024-08-06 17:25:07'),
(4, 'Checker', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus consectetur vitae mauris sed ultrices. Integer a lectus eget augue malesuada tristique. Duis sed accumsan tellus. Nullam et lectus est. ', 1, 1, '2024-08-06 16:25:17', '2024-08-06 17:25:17'),
(5, 'To Go', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus consectetur vitae mauris sed ultrices. Integer a lectus eget augue malesuada tristique. Duis sed accumsan tellus. Nullam et lectus est. ', 1, 1, '2024-08-06 16:25:25', '2024-08-06 17:25:25'),
(6, 'Try again', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus consectetur vitae mauris sed ultrices. Integer a lectus eget augue malesuada tristique. Duis sed accumsan tellus. Nullam et lectus est. ', 1, 1, '2024-08-06 16:25:36', '2024-08-06 17:25:36'),
(7, 'To Go Aguleri', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus consectetur vitae mauris sed ultrices. Integer a lectus eget augue malesuada tristique. Duis sed accumsan tellus. Nullam et lectus est. ', 1, 1, '2024-08-06 16:49:21', '2024-08-06 17:49:21'),
(8, 'Ebuka title', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus consectetur vitae mauris sed ultrices. Integer a lectus eget augue malesuada tristique. Duis sed accumsan tellus. ', 1, 1, '2024-08-06 17:10:36', '2024-08-06 18:10:36'),
(9, 'Oduguw', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus consectetur vitae mauris sed ultrices. Integer a lectus eget augue malesuada tristique. Duis sed accumsan tellus. ', 1, 1, '2024-08-06 17:10:45', '2024-08-06 18:10:45'),
(10, 'anambar', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus consectetur vitae mauris sed ultrices. Integer a lectus eget augue malesuada tristique. Duis sed accumsan tellus. ', 1, 1, '2024-08-06 17:11:20', '2024-08-06 18:11:20'),
(11, 'Dangote', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus consectetur vitae mauris sed ultrices. Integer a lectus eget augue malesuada tristique. Duis sed accumsan tellus. ', 1, 1, '2024-08-06 17:11:28', '2024-08-06 18:11:28'),
(12, 'Emeka', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus consectetur vitae mauris sed ultrices. Integer a lectus eget augue malesuada tristique. Duis sed accumsan tellus. ', 1, 1, '2024-08-06 17:11:45', '2024-08-06 18:11:45'),
(13, 'Pass', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus consectetur vitae mauris sed ultrices. Integer a lectus eget augue malesuada tristique. Duis sed accumsan tellus. ', 1, 1, '2024-08-06 17:11:52', '2024-08-06 18:11:52');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `blog`
--
ALTER TABLE `blog`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `blog`
--
ALTER TABLE `blog`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
