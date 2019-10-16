from math import log10, sqrt

"""
Calculate Moles
Parameters: volume of solution in liters, molarity of solution
Does: calculates the moles of solute in a solution of given molarity and volume
Returns:  the number of moles of solute in the solution
"""
def calculate_moles(volume, molarity):
    return volume * molarity


"""
Calculate SS PH
Parameters: moles of acid, moles of base and total volume of titration solution
Does:  calculates the resulting pH of a strong acid-strong base titration
Returns:  pH of a resulting solution from a strong acid-strong base titration
"""
def calculate_ss_ph(moles_acid, moles_base, titration_volume):
    if moles_acid == moles_base:
        print("pH = 7.00\n")
    elif moles_acid > moles_base:
        final_moles_acid = moles_acid - moles_base
        conc_a = final_moles_acid / titration_volume
        ph = -log10(conc_a)
        print("pH = %.2f\n" % ph)
    elif moles_acid < moles_base:
        final_moles_base = moles_base - moles_acid
        conc_b = final_moles_base / titration_volume
        ph = 14 + log10(conc_b)
        print("pH = %.2f\n" % ph)
    else:
        print("Sorry there was an error.  Try entering those values again.\n")


"""
Calculate WASB PH
Parameters: molarity of acid solution, moles of acid, moles of base, and total volume of titration solution
Does:  calculates the resulting pH of a weak acid-strong base titration
Returns:  pH of a resulting solution from a weak acid-strong base titration
"""
def calculate_wasb_ph(molarity_acid, moles_acid, moles_base, titration_volume):
    ka = float(input("Enter ka value of weak acid: "))
    kb = 1e-14 / ka
    if moles_acid == 0:
        hydronium_conc = sqrt(ka * molarity_acid)
        ph = -log10(hydronium_conc)
        print("pH = %.2f\n" % ph)
    elif moles_acid > moles_base:
        final_moles_acid = moles_acid - moles_base
        moles_conj_base = moles_base
        ph = -log10(ka) + log10(moles_conj_base/final_moles_acid)
        print("pH = %.2f\n" % ph)
    elif moles_acid == moles_base:
        moles_conj_base = moles_base
        conc_conj_base = moles_conj_base / titration_volume
        hydroxide_conc = sqrt(kb * conc_conj_base)
        ph = 14 + log10(hydroxide_conc)
        print("pH = %.2f\n" % ph)
    elif moles_acid < moles_base:
        final_moles_base = moles_base - moles_acid
        conc_b = final_moles_base / titration_volume
        ph = 14 + log10(conc_b)
        print("pH = %.2f\n" % ph)
    else:
        print("Sorry there was an error.  Try entering those values again.\n")


"""
Get Menu Choice
Parameters: none
Does:  displays menu instructions for pH calculator
Returns:  user input
"""
def get_menu_choice():
    print("pH Calculator Menu")
    return input("Enter Q to quit or any other key to calculate titration pH: ")


def main():
    quit = ["Q", "q"]
    while get_menu_choice() not in quit:
        # Ask user for acid identity to determine if strong or weak.
        acid_identity = input("Enter acid identity (for example H2SO4): ").upper()

        # List strong acids.  Acids not listed are weak acids by default.
        strong_acids = ["HCL", "HNO3", "H2SO4", "HBR", "HI", "HCLO4", "HCLO3"]

        # Define input variables.
        volume_acid = float(input("Enter volume of acid in liters: "))
        molarity_acid = float(input("Enter molarity of acid: "))
        volume_base = float(input("Enter volume of base in liters: "))
        molarity_base = float(input("Enter molarity of base: "))

        # Use input variables to calculate other global variables.
        moles_acid = calculate_moles(volume_acid, molarity_acid)
        moles_base = calculate_moles(volume_base, molarity_base)
        titration_volume = volume_acid + volume_base

        # Determine if acid is strong or weak by comparing to the strong acid list.  Call appropriate function.
        if acid_identity in strong_acids:
            calculate_ss_ph(moles_acid, moles_base, titration_volume)
        else:
            calculate_wasb_ph(molarity_acid, moles_acid, moles_base, titration_volume)


main()
