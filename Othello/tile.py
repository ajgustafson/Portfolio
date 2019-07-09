# April Gustafson
# CS5001
# Homework 7
# November 20, 2018

import turtle

N = 8
WIDTH = 50
RADIUS = 25

class Tile:
    '''
    Class to represent Othello game tile
    Attributes: color (str to represent tile color), position (tuple of ints to
            represent column, row where tile is placed)
    Required in constructor:  all
    Optional in constructor: none
    Default values: none
    Methods: place_tile (determines where to draw the tile based on position
            attribute and draws tile), flip_tile (changes tile color attribute),
            __str__ (prints tile attributes), __eq__ (two tiles are equivalent
            if they have the same position attribute)
    '''

    def __init__(self, color, position):
        '''
        Method __init__
        Parameters: str (color of tile, for Othello either "black" or "white",
                    tuple of ints (column, row coordinates for tile position)
        Returns: nothing
        Does: instatiates tile object with given color and position attributes

        '''
        self.color = color
        self.position = position


    def draw_tile(self):
        '''
        Method place_tile
        Parameters: none
        Returns: tuple (x, y coordinates for draw_position)
        Does:  takes position attribute of tile object and determines the x, y
                coordinates where turtle will start drawing the tile; draw
                circular tile with turtle using color determined by tile's
                color attribute
        '''
        tile = turtle.Turtle()
        # Get board column and row from tile position
        column = self.position[0]
        row = self.position[1]
        # Determine where turtle will start drawing circle
        x_coord = (-WIDTH * (N/2 - column)) + RADIUS
        y_coord = (-WIDTH * (N/2 - row))
        draw_position = (x_coord, y_coord)
        
        # Use turtle to draw circle and fill with correct color
        tile.hideturtle()
        tile.speed(0)
        tile.fillcolor(self.color)
        tile.penup()
        tile.goto(draw_position)
        tile.pendown()
        tile.begin_fill()
        tile.circle(RADIUS)
        tile.end_fill()
        
        # Return value used for testing function
        return draw_position


    def flip_tile(self):
        '''
        Method flip_tile
        Parameters: none
        Returns: nothing
        Does:  changes the color attribute of a tile object and redraws the tile
                in the same position on the board
        '''
        # Change to opposite color
        self.color = ("black" if self.color == "white" else "white")
        
        # Redraw the tile on the board in new color
        self.draw_tile()


    def __str__(self):
        '''
        Method __str__
        Parameters: none
        Returns:  string (containing formatted tile attributes)
        Does:  Formats all tile attributes for printing
        '''
        printable_tile = "Tile in square " + str(self.position) + \
                         ":\nColor is " +  self.color
        return printable_tile


    def __eq__(self, other):
        '''
        Method __eq__
        Parametrs: tile object
        Returns:  boolean (True if self.position is the same as other.position,
                False otherwise)
        Does:  checks to see if two tile objects are equivalent (they are
                equivalent if their positions are the same)
        '''
        return self.position == other.position



    





