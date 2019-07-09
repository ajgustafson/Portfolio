# April Gustafson
# CS5001
# Homework 7
# November 20, 2018

class Scorekeeper:
    '''
    Class to represent scorekeeper for Othello game
    Attributes: user_info (list with two str - user name and user tile
            count), current_score (user's score as an int), score_file (str w/
            .txt ending), past_scores (nested list of previous scores), output
            (str to output to file)
    Required in constructor: user_info
    Optional in constructor: none
    Default values: score_file = "scores.txt", past_scores = [], output = ""
    Methods: save_output (saves str to file), get_file (reads file and changes
            past_scores attribute to a list of lines), strip_file_lst (strips
            "\n" off each list item), build_score_lst (builds a nested list by
            turning each list item into a list separated by spaces), add_score
            (adds high scores to the beginning of a list, adds all other scores
            to the end of the list), string_score_output (turns nested list into
            a str), save_score (goes through entire process of saving user score
            to file)
    '''

    def __init__(self, user_info):
        '''
        Method __init__
        Parameters: user_info (lst of 2 str)
        Returns: nothing
        Does: instantiates Scorekeeper object
        '''
        self.user_info = user_info
        self.current_score = int(user_info[1])
        self.score_file = "scores.txt"
        self.past_scores = []
        self.output = ""
        
        
    def save_output(self):
        '''
        Method save_output
        Parameters: none
        Returns: nothing
        Does: writes string to file
        '''
        try:
            outfile = open(self.score_file, "w")
            outfile.write(self.output)
            outfile.close()
        except OSError:
            print("Error saving file.")


    def get_file(self):
        '''
        Method get_file
        Parameters: none
        Returns: nothing
        Does: reads file and adds each line as an item in a list, sets
                past_scores attribute to this list
        '''
        # If scores file already exists
        try:
            infile = open(self.score_file, "r")
            lst = infile.readlines()
            infile.close()
            self.past_scores = lst
        # If scores file does not already exist
        except OSError:
            # Try writing an empty string to the new file, then recall get_file
            try:
                self.save_output()
                self.get_file()
            except OSError:
                print("Error reading file.")

        
    def strip_file_lst(self):
        '''
        Method strip_file_lst
        Parameters: none
        Returns: nothing
        Does: removes new line character from each item in past_scores attribute
        '''
        for i in range(len(self.past_scores)):
            self.past_scores[i] = self.past_scores[i].strip("\n")
        

    def build_score_lst(self):
        '''
        Method build_score_lst
        Parameters: none
        Returns: nothing
        Does: builds a nested list from each item in past_scores; each item
                becomes a list of length 2; past_scores set to new nested list
        '''
        nested_lst = []
        for entry in self.past_scores:
            # Split each list item into a new list (split at spaces)
            entry_split = entry.split()
            nested_lst.append(entry_split)
        self.past_scores = nested_lst


    def add_score(self):
        '''
        Method add_score
        Parameters: none
        Returns: nothing
        Does: sets user info as first item in score list if user's score is
                higher than any previous score, otherwise appends user info to
                end of score list
        '''
        # If score list empty, user automatically is high score
        if self.past_scores == []:
            self.past_scores.append(self.user_info)
        # If user score higher than any previous, set at beginning of list
        elif self.current_score > int(self.past_scores[0][1]):
            self.past_scores.insert(0, self.user_info)
        # Otherwise, append user info at end of list
        else:
            self.past_scores.append(self.user_info)

    def string_score_output(self):
        '''
        Method string_score_output
        Parameters: none
        Returns: nothing
        Does: turns nested list into string with a space between list items
        '''
        for lst in self.past_scores:
            self.output = self.output + lst[0] + " " + str(lst[1]) + "\n"


    def save_score(self):
        '''
        Method save_score
        Parameters: none
        Returns: nothing
        Does: gets previous score file, turns score file into nested list, adds
                user info, turns nested list into string, save string to file
        '''
        self.get_file()
        if self.past_scores != []:
            self.strip_file_lst()
            self.build_score_lst()
        self.add_score()
        self.string_score_output()
        self.save_output()


    def __str__(self):
        '''
        Method __str__
        Parameters: none
        Returns: str
        Does: formats scorekeeper attributes for printing
        '''
        printable = "Scorekeeper is saving " + str(self.user_info) + \
                    "to " + self.score_file + " file."

