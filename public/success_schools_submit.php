<?php
// Check if a success message exists in the session
if (isset($_SESSION['success_message'])) {
    $successMessage = $_SESSION['success_message'];
    unset($_SESSION['success_message']); // Remove the message from the session
} else {
    // Default success message (you can customize this)
    $successMessage = 'Schools table modified -- Database update successful!';
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Success Page</title>
    <!-- Include Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .container.mt-5{
            display: flex;
            justify-content: center;
            position: relative;
        }
        .blink {
    animation: blink 1s 3 alternate;
        }
        @keyframes blink {
            0% { opacity: 1; }
            50% { opacity: 0; }
            100% { opacity: 1; }
        }
        .continue{
            display: flex;
            justify-content: center; /* Center horizontally */
            align-items: center;     /* Center vertically */
            height: 0px;     
        }
        .text{
            display: flex;
            justify-content: center; /* Center horizontally */
            align-items: center;     /* Center vertically */
            height: 100px;   
        }
        .logo1 {
    display: flex;
    justify-content: center; /* Center horizontally */
    align-items: center; /* Center vertically */
    }
        body::before {
            content: "";
            background-image: url('num.jpg');
            background-size: cover;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            filter: blur(6px); /* Adjust the blur radius as needed */
            z-index: -1;
        }
    </style>
</head>
<body>
    <div class="logo1"><img src="black/img/Logo3.png" alt="Logo" width="400" height="400"></div>
    <div class="blink">
        <div class="container mt-5">
            <!-- Display the success message -->
            <div class="alert alert-success" role="alert">
                <?php echo $successMessage; ?>
            </div>
        </div>
    </div>
    <div class="text"><p>Press the button to continue</p></div>
    <div class="continue">
        <a href="{{ route('challenges') }}" ><button id="">Continue</button></a>
    </div>
</body>
</html>