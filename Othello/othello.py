# April Gustafson
# CS5001
# Homework 7
# November 20, 2018


import turtle
from board import Board
from tile import Tile
from human import Human
from computer import Computer
from scorekeeper import Scorekeeper


N = 8
WIDTH = 50
RADIUS = 25
MAX_TILES = N ** 2


def main():
    # Instantiate board object
    board = Board()

    # Draw board and place initial tiles on the board
    board.start_game()

    # Play game!
    board.play_game()




main()   
