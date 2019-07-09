# April Gustafson
# CS5001
# Homework 7
# November 20, 2018


class Computer:
    '''
    Class to represent Computer player in Othello game
    Attributes: position (tuple to represent computer's chosen move)
    Required in constructor: none
    Optional in constructor: none
    Default values: position = ()
    Methods: choose_move (chooses a random move from a dictionary of valid
                moves
    '''

    def __init__(self):
        '''
        Method __init__
        Parameters: none
        Returns: nothing
        Does: instantiates Computer object with default attributes
        '''
        self.position = ()


    def choose_move(self, moves_dict):
        '''
        Method choose_move
        Parameters: dict (keys are tuples of valid moves, values are lists of
                tuples containing tile flips for given move)
        Returns: nothing
        Does: chooses a move key from a dictionary of valid moves based on the
                length of the flip list for that key; sets position attribute
                to this key
        
        '''
        most_flips = 0
        for key in moves_dict:
            if len(moves_dict[key]) > most_flips:
                most_flips = len(moves_dict[key])
                best_move = key
        self.position = best_move
        print("Computer chooses square ", self.position)


    def __str__(self):
        '''
        Method __str__
        Parameters: none
        Returns: str (for printing)
        Does: formats computer attribute for printing
        '''
        printable = "Computer's current position is: " + str(self.position)
        return printable
                

    

