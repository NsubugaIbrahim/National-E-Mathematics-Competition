<!DOCTYPE html>
<html>
<head>
    <title>Top Participants</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <style>
        body {
            background-color: #27293d;
            font-family: 'Arial', sans-serif;
        }
        h1, h3 {
            font-family: 'Georgia', serif;
            color: yellow;
            text-align: center;
        }
        .table-container {
            width: 90%;
            margin: 0 auto;
        }
        .table {
            font-size: 1.1rem;
            width: 100%;
        }
        .table thead {
            background-color: #007bff;
            color: #fff;
        }
        .table tbody tr:nth-child(odd) {
            background-color: #e9ecef;
        }
        .table tbody tr:nth-child(even) {
            background-color: #f8f9fa;
        }
        .logo1 {
                display: flex;
                justify-content: center; /* Center horizontally */
                align-items: center; /* Center vertically */
                }
    </style>
</head>
<body>
<div class="logo1"><img src="black/img/Logo4.png" alt="Logo" width="250" height="250"></div>
    <div class="container mt-5">
        <h1 class="mb-4 text-center">Winners</h1>
        <h3 class="mb-4 text-center">Best two participants (based on average score across participants' attempted challenges)</h1>
        <div class="table-container">
            <div class="table-responsive">
                <table class="table table-bordered table-striped table-hover">
                    <thead>
                        <tr>
                            <th>Participant ID</th>
                            <th>Username</th>
                            <th>School Name</th>
                            <th>Average Score</th>
                        </tr>
                    </thead>
                    <tbody>
                        @foreach($topParticipants as $participant)
                            <tr>
                                <td>{{ $participant->participantId }}</td>
                                <td>{{ $participant->username }}</td>
                                <td>{{ $participant->schoolName }}</td>
                                <td>{{ number_format($participant->average_score, 2) }}</td>
                            </tr>
                        @endforeach
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <!-- Bootstrap JS (optional for Bootstrap components) -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
