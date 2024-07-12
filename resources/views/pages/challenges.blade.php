@extends('layouts.app', ['page' => __('Challenges'), 'pageSlug' => 'challenges'])

@section('content')
<html>
<head>
    <title>School Registration</title>
    <style>
        .availability {
            font-size: 20px;
            text-align: center; /* Center horizontally */
            vertical-align: middle; /* Center vertically */
        }
        .container{
            width: 50%;
            float: right;
        }

    </style>
</head>
<body>
    <div class="content">  
        <h1 class="card-title">Set Challenge parameters to create a challenge</h1> 
        
        <div class="container">
            <div class="alert alert-info alert-with-icon" data-notify="container">
                <button type="button" aria-hidden="true" class="close" data-dismiss="alert" aria-label="Close">
                    <i class="tim-icons icon-simple-remove"></i>
                </button>
                <span data-notify="message"> After setting parameters and creating a challenge, the challenge will be uploaded to the database together with it's accompanying properties </span>
            </div>
        </div>
               
        <form method="post" action="/submit-challenge">
        @csrf
        <table class="table table-striped table-hover table-borderless table-lg col-6">
            <tr><th><h4>Number of questions:</h4></th> <td><h4><input type="text" name="no.questions" required></h4></td><tr>
            <tr><th><h4>Duration:</h4></th> <td><h4><input type="time" name="duration" required></h4></td><tr>
            <tr><th><h4>Start date:</h4></th> <td><h4><input type="date" name="start" id="start-date" required></h4></td><tr>
            <tr><th><h4>End date:</h4></th> <td><h4><input type="date" name="end" id="end-date" required></h4></td><tr>
            <!-- <tr><th></th> <td></td><tr> -->
        </table>
                @if (session('status'))
                    <div class="alert alert-success">{{session('status')}}</div>
                @endif
    
        <button class="btn btn-secondary" type= "submit" id=""><i class="tim-icons icon-cloud-upload-94"></i>    Create Challenge</button>
        </form>
    <br><br>
    <a href="{{ route('challenges') }}" class="btn btn-primary"> <i class="tim-icons icon-components"></i>   View Uploaded Challenges</a>

    </div>

    <script>
        const startDateInput = document.getElementById('start-date');
        const endDateInput = document.getElementById('end-date');
        const availabilityCell = document.querySelector('.availability');
        const currentDate = new Date();

        endDateInput.addEventListener('change', updateAvailability);
        startDateInput.addEventListener('change', updateAvailability);

        function updateAvailability() {
            const startDate = new Date(startDateInput.value);
            const endDate = new Date(endDateInput.value);

            if (startDate <= currentDate && currentDate <= endDate) {
                availabilityCell.textContent = '✓'; // Display tick symbol
            } else {
                availabilityCell.textContent = '✗'; // Display x symbol
            }
        }
    </script>
    <script>
  document.getElementById('addRow').addEventListener('click', function() {
    const tableBody = document.querySelector('tbody');
    const newRow = document.createElement('tr');
    newRow.classList.add('rows'); // Add the 'row' class to the new row

    // Create and append new cells to the row
    newRow.innerHTML = `
        <td><input type="text" name="no.questions" required></td>
        <td><input type="time" name="duration" required></td>
        <td><input type="date" name="start" id="start-date" required></td>
        <td><input type="date" name="end" id="end-date" required></td>        
        <td class="availability" id="availability-symbol"></td>                        
    `;

    tableBody.appendChild(newRow);

    // Update the availability symbol for the new row
    const newStartDateInput = newRow.querySelector('#start-date');
    const newEndDateInput = newRow.querySelector('#end-date');
    const newAvailabilityCell = newRow.querySelector('.availability');

    // Function to update availability symbol
    function updateAvailability() {
        const currentDate = new Date();
        const startDate = new Date(newStartDateInput.value);
        const endDate = new Date(newEndDateInput.value);

        if (startDate <= currentDate && currentDate <= endDate) {
            newAvailabilityCell.textContent = '✓'; // Display tick symbol
        } else {
            newAvailabilityCell.textContent = '✗'; // Display x symbol
        }
    }

    // Attach event listeners to the new start and end date inputs
    newStartDateInput.addEventListener('change', updateAvailability);
    newEndDateInput.addEventListener('change', updateAvailability);
});
</script>
</body>
</html>
@endsection
