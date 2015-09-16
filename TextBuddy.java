/**
  *Lim Chen Gim A0113645L
  *
  * This program TextBuddy manipulates text in a file.
  * Users can input commands such as 'ADD', 'DELETE', 'DISPLAY', 'CLEAR',
  * 'SORT', 'SEARCH', 'EXIT' to edit contents in a text file. This program
  * will auto-save after every change user makes.
  *
  * Assumptions:
  * 1. Inputs entered are always valid. (Otherwise program will terminate due
  * to error as I did not implement error catching.)
  *
  */
  
  import java.io.BufferedWriter;
  import java.io.File;
  import java.io.FileWriter;
  import java.io.IOException;
  import java.io.PrintWriter;
  import java.util.ArrayList;
  import java.util.Collections;
  import java.util.Scanner;
