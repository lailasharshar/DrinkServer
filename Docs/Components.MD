Different parts:

NFC reader - Phone or Card
Pump actuator - Pours the coffee
Volume estimator - Monitors amount coffee poured

On device:

    NFC Reader
    Pump
    Small computer(Raspberry Pi)

Remote:

    Application server
        Maintains user accounts
        Respond to user queries from devices
        Processes/maintains account balances
        Maintains health information on the individual devices
        Processes credit card requests
        Responds to mobile apps
        
Mobile App (Android/iPhone):

        View locations of devices
        Sign up
        Check balance
        Registers cards
        Pay for coffee
    
Events 

    When coffee is poured (how much)
    When coffee is low
    
Requests

    How much is left
    Purchases in time period
    Users in time period
    
    