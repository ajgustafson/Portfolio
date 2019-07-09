# April Gustafson
# CS5001
# Homework 7
# November 20, 2018

import turtle
import time
from tile import Tile
from human import Human
from computer import Computer
from scorekeeper import Scorekeeper

N = 8
MAX_TILES = N ** 2
WIDTH = 50

class Board:
    '''
    Class to represent Othello game board
    Attributes: status (nested list of ints and str to represent the status of
            each square on the board; list indices match board column, row
            coordinates; item in list is 0 if square is empty, "black" if square
            contains a black tile, "white" if square contains a white tile),
            tiles (list of tile objects on the board), player (str to indicate
            player color), opponent (str to indicate opponent color),
            moves (dict containing all valid moves, keys are tuples to indicate
            square where new tile can be places, values are lists of tuples
            to indicate all tiles that will be flipped if new tile placed at
            key)
    Required in constructor: none
    Optional in constructor: none
    Default values: squares = [[0 for i in range(N)] for j in range(N)],
            tiles = [], player = "black", opponent = "white", moves = {}
    Methods: draw board, draw_lines (on board), place_tile (draws tile on
            board), start_game (to place 4 initial tiles on board),
            switch_player (to change player and opponent attributes),
            is_opponent (to identify if a square is occupied by opponent's
            tile), is_off_board (to identify if the coordinates of a square is
            off the board), is_player (to identify if a square is occupied by
            player's tile), is_empty (to identify if a square is empty),
            check_directions (to check in one direction from a tile to see if
            there are any valid moves for that tile), get_tile_moves (to check
            in all directions from a tile to find all valid moves from that
            tile), get_all_moves (identifies all valid moves for one player),
            are_valid_moves (to identify if any valid moves exist for either
            player), flip_tiles (to flip all tiles for a given move),
            make_move (to place a new tile in a square and flip all tiles that
            correspond with that move), is_board_full (to identify if board is
            full of tiles), count_tiles (to count the number of tiles that on
            the board for a given color), end_game (to compare tile counts and
            determine win/tie), __str__ (to print attributes of board)
    '''


    def __init__(self):
        '''
        Method __init__
        Parameters: none
        Returns: nothing
        Does: instantiates Board object with default attributes
        '''
        # Status is an 8x8 nested list filled w/ 0's to represent empty squares
        self.status = [[0 for i in range(N)] for j in range(N)]
        self.tiles = []
        self.player = "black"
        self.opponent = "white"
        self.moves = {}

            
    def draw_board(self, n):
        '''
        Method draw_board
        Parameters: n, an int for # of squares
        Replayers: nothing
        Does: Draws an nxn board with a green background
        '''

        turtle.setup(n * WIDTH + WIDTH, n * WIDTH + WIDTH)
        turtle.screensize(n * WIDTH, n * WIDTH)
        turtle.bgcolor("white")

        # Create the turtle to draw the board
        othello = turtle.Turtle()
        othello.penup()
        othello.speed(0)
        othello.hideturtle()

        # Line color is black, fill color is green
        othello.color("black", "forest green")
        
        # Move the turtle to the upper left corner
        corner = -n * WIDTH / 2
        othello.setposition(corner, corner)
      
        # Draw the green background
        othello.begin_fill()
        for i in range(4):
            othello.pendown()
            othello.forward(WIDTH * n)
            othello.left(90)
        othello.end_fill()

        # Draw the horizontal lines
        for i in range(n + 1):
            othello.setposition(corner, WIDTH * i + corner)
            self.draw_lines(othello, n)

        # Draw the vertical lines
        othello.left(90)
        for i in range(n + 1):
            othello.setposition(WIDTH * i + corner, corner)
            self.draw_lines(othello, n)


    def draw_lines(self, turt, n):
        '''
        Method draw_lines
        Parameters: turtle object, int (representing number of squares)
        Returns: nothing
        Does: draws lines to create an nxn grid
        '''
        turt.pendown()
        turt.forward(WIDTH * n)
        turt.penup()      


    def place_tile(self, color, position):
        '''
        Method place_tile
        Parameters: str (color), tuple (coordinates of square)
        Returns: nothing
        Does: creates tile object of given color at given position; draws
                tile in square on board; adds tile to list of tile objects
                in board's tiles attribute; adds tile color at given coord
                to board's status attribute
        '''
        # Instantiate tile object
        tile = Tile(color, position)

        # Draw tile on the board
        tile.draw_tile()

        # Append tile object to list of tiles
        self.tiles.append(tile)

        # Write tile color in status list at corresponding coordinates
        self.status[position[0]][position[1]] = color
        
        
    def start_game(self):
        '''
        Method start_game
        Parameters: none
        Returns: nothing
        Does: starts Othello game by printing welcome message, drawing game
                board, placing 2 initial black and 2 initial white tiles
        '''
        # Welcome user and draw board on turtle screen
        print("Welcome to Othello!")
        turt = turtle.Turtle()
        self.draw_board(N)
        self.draw_lines(turt, N)

        # Set initial black tiles in the center of the board
        for i in range(2):
            position = (N//2 - i , N//2 - 1 + i)
            self.place_tile(self.player, position)
            
        # Set initial white tiles in the center of the board
        for i in range(2):
            position = (N//2 - 1 + i , N//2 - 1 + i)
            self.place_tile(self.opponent, position)

            
    def switch_players(self):
        '''
        Method switch_players
        Parameters: none
        Returns: nothing
        Does: changes board's player and opponent attributes to opposite color
        '''
        # Switch player and opponent attributes
        self.player = ("black" if self.player == "white" else "white")
        self.opponent = ("black" if self.player == "white" else "white")

        
    def is_opponent(self, square):
        '''
        Method is_opponent
        Parameters: tuple (square coordinates)
        Returns:  bool (True is opponent tile in square, False otherwise)
        Does: identifies if opponent's tile is in given square
        '''
        return (self.status[square[0]][square[1]] == self.opponent)


    def is_off_board(self, square):
        '''
        Method is_off_board
        Parameters: tuple (square coordinates)
        Returns:  bool (True if square coordinates are off board,
                    False otherwise)
        Does:  identifies if square is off board
        '''
        return (square[0] < 0 or square[0] > (N - 1) or \
                square[1] < 0 or square[1] > (N - 1))


    def is_player(self, square):
        '''
        Method is_player
        Parameters:  tuple (square coordinates)
        Returns:  bool (True if player's tile in square, False otherwise)
        Does: identifies if player's tile is in given square
        '''
        return (self.status[square[0]][square[1]] == self.player)


    def is_empty(self, square):
        '''
        Method is_empty
        Parameters: tuple (square coordinates)
        Returns: bool (True if square has no tile, False otherwise)
        Does:  identifies if given square is empty
        '''
        return (self.status[square[0]][square[1]] == 0)


    def check_direction(self, direction, position):
        '''
        Method check_direction
        Parameters: tuple (representing direction on board; for example,
                (-1, -1) represents going left one column and down one row
                and (0, 1) represents staying in the same column and going
                up one row), tuple (representing square coordinates)
        Returns: nothing
        Does: starting at position, iterates through square coordinates in given
                direction to see if there are any valid moves in that direction;
                if there are valid moves, board attribute moves is changed to
                contain keys with square where tile could be placed and values
                representing the tiles that will be flipped if a tile is placed
                there
        '''
        column = position[0]
        row = position[1]
        continuing = True
        flips = []
        while continuing:
            # Change square to be the next square in given direction
            column += direction[0]
            row += direction[1]
            square = (column, row)
            # At this square, check if off board (not valid)
            if self.is_off_board(square):
                continuing = False
            # At this square, check if player's tile (not valid)
            elif self.is_player(square):
                continuing = False
            # At this square, check if there is an opponent's tile (maybe valid)
            elif self.is_opponent(square):
                # Keep track of square as possible flip
                flips.append(square)
            # At this square, check if empty
            elif self.is_empty(square):
                # If square empty but no tiles to flip, not valid
                if flips == []:
                    continuing = False
                # Empty and tiles to flip means the end of a valid move
                else:
                    # If square already in moves dict, append more flips
                    if square in self.moves:
                        for coord in flips:
                            self.moves[square].append(coord)
                        continuing = False
                    else:
                        # Make square key and flips list value in moves dict
                        self.moves[square] = flips
                        continuing = False


    def get_tile_moves(self, position):
        '''
        Method get_tile_moves
        Parameters: tuple (square coordinates)
        Returns: nothing
        Does: iterates through all 8 directions around a given tile position
                and calls the method check direction for each direction; result
                is that board's attribute moves contains all possible moves and
                flips for that tile
        '''
        # List all directions
        direction_steps= [(-1, 1), (0, 1), (1, 1), (1, 0), (1, -1), (0, -1), \
                       (-1, -1), (-1, 0)]

        # Call check_direction in every direction for given position
        for direction in direction_steps:
            self.check_direction(direction, position)


    def get_all_moves(self):
        '''
        Method get_all_moves
        Parameters: none
        Returns: nothing
        Does: wipes out any previous move dictionary; iterates through all
                player tiles on board and calls method get_tile_moves for each
                tile; result is that board's attribute moves contains all
                possible moves for that player
        '''
        # Erase any previous moves dictionary
        self.moves = {}

        # Iterate through status list
        # If item in list is player's tile, call get_tile moves for that tile
        for i in range(N):
            for j in range(N):
                if self.status[i][j] == self.player:
                    self.get_tile_moves((i, j))


    def are_valid_moves(self):
        '''
        Method are_valid_moves
        Parameters: none
        Returns: bool or None(True if there are valid moves for current player;
                False if there are no valid moves for current player; None if
                there are no valid moves for either player)
        Does: checks to see if there are any valid moves for current player;
                if there are not, it checks to see if there are valid moves for
                the current opponent; if there are no moves for either player,
                calls method end_game
        '''
        # If moves in dictionary, return true
        if self.moves != {}:
            return True
        # If no moves, switch players and check if they have valid moves
        else:
            print("No valid moves for ", self.player, ".\nChecking valid moves "
                  "for ", self.opponent, "...")
            self.switch_players()
            self.get_all_moves()
            # If other player has valid moves, switch back to original player
            # and return False
            if self.moves != {}:
                self.switch_players()
                return False
            # If no moves for either player, end game
            else:
                print("No valid moves for either player.")
                self.end_game()
                return


    def flip_tiles(self, chosen_move):
        '''
        Method flip_tiles
        Parameters: tuple (square coordinates)
        Returns: nothing
        Does: chosen_move represents a key in board's moves dictionary; "flips"
            all key values by changes corresponding elements in board's status
            attribute and calling tile method flip_tile for each value
        '''
        # Iterate through all flip coordinate valuse for chosen move key
        for flip_coord in self.moves[chosen_move]:
            # Change status list to reflect flips
            self.status[flip_coord[0]][flip_coord[1]] = self.player
            # Change tile colors and redraw
            for tile in self.tiles:
                if tile.position == flip_coord:
                    tile.flip_tile()
        

    def make_move(self, position):
        '''
        Method make_move
        Parameters: tuple (square coordinates)
        Returns: nothing
        Does: calls method place_tile at given position
        '''
        print("Placing tile at ", position)
        
        # Place new tile at move position
        self.place_tile(self.player, position)

        # Flip tiles associated with move position
        self.flip_tiles(position)
        
        
    def is_board_full(self):
        '''
        Method is_board_full
        Parameters: none
        Returns: bool (True if board full, False otherwise)
        Does:  checks to see if the number of tiles played equals the max
                number of tiles for the game
        '''
        if len(self.tiles) == MAX_TILES:
            return True
        else:
            return False
    
    def take_turns(self, x, y):
        '''
        Method take_turns
        Parameters: int (representing x and y coordinates from
                turtle.onscreenclick)
        Returns: None (if human click is invalid, or if game ending)
        Does: instatiates human and computer objects; gets all moves for human,
                if no valid moves, switch to computer, otherwise, collects
                human's click coordinates, makes a move at human's chosen
                square; checks to see if board is full, if it is, call method
                end_game, otherwise switches to computer; gets all moves
                for computer, if no valid moves, switch to human, otherwise,
                makes a move at computer's chosen square; chekcs
                to see if board is full, if it is, call method end_game,
                otherwise, switch back to human
        '''
        # Instantiate human and computer objects
        human = Human()
        computer = Computer()
        
        # Get all moves for human (black)
        self.get_all_moves()

        # If valid moves for human, set human attributes and collect click
        if self.are_valid_moves():
            human.board_status = self.status
            human.moves_dict = self.moves
            human.get_click(x, y)
            # If  clicked on invalid move, exit method so they can click again
            if human.position == ():
                return
            # If valid click, make move
            else:
                self.make_move(human.position)
                human.position = ()
            # If board not full, switch players
            if not self.is_board_full():
                self.switch_players()
            # If board is full, end game
            else:
                self.end_game()
                return
        # If no valid moves for human, switch players
        else:
            self.switch_players()

        # Get all moves for computer (white)
        self.get_all_moves()
        # If valid moves for computer, computer chooses and makes a move
        if self.are_valid_moves():
            computer.choose_move(self.moves)
            time.sleep(1)
            self.make_move(computer.position)
            # If board not full, switch players
            if not self.is_board_full():
                self.switch_players()
            # If board is full, end game
            else:
                self.end_game()
                return
        # If no valid moves for computer, switch players
        else:
            self.switch_players()

    
    def play_game(self):
        '''
        Method play_game
        Parameters: none
        Returns: nothing
        Does: calls turtle.onscreenclick method and passes in take_turns method
                as the parameter (calls take_turns whenever the user clicks
                the board)
        '''
        
        turtle.onscreenclick(self.take_turns)
          
        
    def count_tiles(self, player):
        '''
        Method count_tiles
        Parameters: str (player color)
        Returns: int (number of player tiles on board)
        Does: counts the number of player tiles on the board
        '''
        count = 0
        for tile in self.tiles:
            if tile.color == player:
                count +=1
        return count



    def get_results(self):
        # Count black and white tiles
        black_count = self.count_tiles("black")
        white_count = self.count_tiles("white")
        printable_count = "Black had " + str(black_count) + \
                  " tiles.  White had " + str(white_count) + \
                  " tiles.\n"
        
        # Determine winner or tie
        if black_count == white_count:
            result = printable_count + "It's a tie!"
        elif black_count > white_count:
            result = printable_count + "Black wins!"
        else:
            result = printable_count + "White wins!"
        
        # Display final results
        print(result)

        # Collect user info as ["Name", "tile_count"]
        user = input("Enter your name for the score board: ")
        user_info = [user, str(black_count)]
        return user_info


    def end_game(self):
        '''
        Method end_game
        Parameters: none
        Returns: str (results of game)
        Does: creates string to display the results of the game, incuding tile
                count for both players and win/tie; closes turtle window
        '''
        # Exit turtle screen
        turtle.bye()
        
        # Get results
        user_info = self.get_results()
        
        # Save results
        scorekeeper = Scorekeeper(user_info)
        scorekeeper.save_score()





                
    def __str__(self):
        '''
        Method __str__
        Parameters: none
        Returns: str (formatted board attributes)
        Does: formats board attributes for printing
        '''

        # Access and format tile position for each tile object
        black_tiles = []
        for tile in self.tiles:
            if tile.color == "black":
                black_tiles.append(tile.position)

        white_tiles = []
        for tile in self.tiles:
            if tile.color == "white":
                white_tiles.append(tile.position)
            
        # Format all attributes for printing
        printable_board = "It is " + self.player + "'s turn.\n" + \
                          "There are " + str(len(self.tiles)) + \
                          " tiles on the board.\nThe black tile coordinates" + \
                          " are " + str(black_tiles) + "\nThe white tile " + \
                          " coordinates are " + str(white_tiles)
                          
        return printable_board
