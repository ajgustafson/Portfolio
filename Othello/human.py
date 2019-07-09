# April Gustafson
# CS5001
# Homework 7
# November 20, 2018

import turtle
from math import floor

N = 8
MAX_TILES = N ** 2
WIDTH = 50

class Human:
    '''
    Class to represent Human player in Othello game
    Attributes: position (tuple representing x, y coordinates of players current
            move, board_status (nested list of ints and str to represent where
            tiles are currently placed on the board), moves_dict (dict
            containing keys of valid moves)
    Required in constructor: none
    Optional in constructor: none
    Default values: position = (), board_status = [], moves_dict = {}
    Methods: identify_position (determines column, row coordinates for any x
            and y value on the board and sets this coordinate as the position
            attribute), is_empty_square (determines if user clicked on an empty
            square), is_valid_move (determines if user clicked on a square that
            is a valid move), get_click (passes x and y values from click to
            identify_square, and the calls is_empty_square and is_valid_move
            with the new position attribute set by identify_square)
    '''


    def __init__(self):
        '''
        Method __init__
        Parameters: none
        Returns: nothing
        Does:  instatiates a human object with default parameters
        '''
        self.position = ()
        self.board_status = []
        self.moves_dict = {}


    def identify_position(self, x, y):
        '''
        Method identify_square
        Parameters: float (x-coordinate on Othello board), float (y-coordinate
                on Othello board)
        Returns: nothing
        Does: determines the column and row coordinates for any x, y
                coordinates on the game board; prints a message to the user
                if coordinates are not on the game board
        '''
        # Delete any previous position
        self.position = ()

        # Calculate column and row based on x and y values
        column = int((N/2) + (floor(x/WIDTH)))
        row = int((N/2) + (floor(y/WIDTH)))

        # If square not on the board, prompt user and position unchanged
        if column < 0 or column > 7 or row < 0 or row > 7:
            print("Please choose a square on the board.")
        # If square is on the board, set position attribute
        else:
            self.position = (column, row)


    def is_empty_square(self):
        '''
        Method is_empty_square
        Parameters: none
        Returns: bool (True if square represented by human position attribute
                is empty, False otherwise)
        Does: checks to see if there is a tile in the square on the board
                represented by human position attribute
        '''
        if self.position != ():
            # For clicks inside board, check to see if user clicked empty square
            if self.board_status[self.position[0]][self.position[1]] != 0:
                self.position = ()
                print("Choose an empty square.")
                return False
            else:
                return True
                

    def is_valid_move(self):
        '''
        Method is_valid_move
        Parameters: none
        Returns: bool (True if value of human position attribute is a valid
                move, False otherwise)
        Does: checks to see if value of human position attribute is a valid
                move by determining if it is a key in the moves dictionary
        '''
        # As long as self.position is not empty
        # Return false and print error message if position not in moves dict
        # Return True if position is in the dictionary of valid moves
        if self.position != ():
            if self.position not in self.moves_dict:
                self.position = ()
                print("Not a valid move.  Choose another square.")
                return False
            else:
                return True

        
    def get_click(self, x, y):
        '''
        Method get_click
        Parameters: float (x-coordinate on Othello board), float (y-coordinate
                on Othello board)
        Returns: nothing
        Does: passes x and y to identify_position (which sets the human
                position attribute), then calls is_empty_square and
                is_valid_move
        '''
        # Call identify_position to set self.position 
        self.identify_position(x, y)
        # Validate user square choice by checking to see that it is not empty
        # and that it is a valid move
        self.is_empty_square()
        self.is_valid_move()

    def __str__(self):
        '''
        Method __str__
        Parameters: none
        Returns: str (human attributes formatted for printing)
        Does: formats human attributes for printing
        '''
        # Collect all keys from moves dict to display as current valid moves
        moves = []
        for key in self.moves_dict:
            moves.append(key)

        # Return printable string with player's current position and moves
        printable = "Human players's current position is: " + \
                    str(self.position) + \
                    "\nThe current valid moves for this player are: " +\
                    str(moves)
        return printable
            

