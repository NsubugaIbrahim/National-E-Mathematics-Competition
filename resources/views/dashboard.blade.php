@extends('layouts.app', ['pageSlug' => 'dashboard'])

@section('content')

<!DOCTYPE html>
<html>
<head>
    <title>Schools Added Over Time</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
            .head h3 {
        color: pink; 
    }
    </style>
</head>
<body>
    <div class="head"><h3>Graph showing number of schools registered over a week</h3></div>
    <div class="row">
        <div class="col-12">
            <div class="card card-chart">
                <div class="card-body">
                    <div style="width: 75%; margin: auto;">
                        <canvas id="schoolsChart"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="head"><h3>Graph showing number of participants confirmed over a week</h3></div>
    <div class="row">
        <div class="col-12">
            <div class="card card-chart">
                <div class="card-body">
                    <div style="width: 75%; margin: auto;">
                        <canvas id="participantsChart" ></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>



    <script>
        document.addEventListener('DOMContentLoaded', function () {
            // Schools Chart
            var ctxSchools = document.getElementById('schoolsChart').getContext('2d');
            var schools = @json($schools);
            console.log("Schools Data: ", schools); // Debugging output
            var schoolLabels = schools.map(school => {
                var year = school.week.toString().substring(0, 4);
                var week = school.week.toString().substring(4);
                return `Week ${week}, ${year}`;
            });
            var schoolData = schools.map(school => school.count);

            var schoolsChart = new Chart(ctxSchools, {
                type: 'line',
                data: {
                    labels: schoolLabels,
                    datasets: [{
                        label: 'Number of Schools Added',
                        data: schoolData,
                        borderColor: 'rgba(75, 192, 192, 1)',
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        fill: true,
                        tension: 0.1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        x: {
                            title: {
                                display: true,
                                text: 'Week',
                                color: 'rgba(255, 99, 132, 1)' // Change the color of the x-axis title
                            },
                            ticks: {
                                color: 'rgba(255, 99, 132, 1)' // Change the color of the x-axis labels
                            }
                        },
                        y: {
                            beginAtZero: true,
                            ticks: {
                                stepSize: 1,
                                color: 'rgba(54, 162, 235, 1)' // Change the color of the y-axis labels
                            },
                            title: {
                                display: true,
                                text: 'Number of Schools',
                                color: 'rgba(54, 162, 235, 1)' // Change the color of the y-axis title
                            }
                        }
                    }
                }
            });

            // Participants Chart
            var ctxParticipants = document.getElementById('participantsChart').getContext('2d');
            var participants = @json($participants);
            console.log("Participants Data: ", participants); // Debugging output
            var participantLabels = participants.map(participant => {
                var year = participant.week.toString().substring(0, 4);
                var week = participant.week.toString().substring(4);
                return `Week ${week}, ${year}`;
            });
            var participantData = participants.map(participant => participant.count);

            var participantsChart = new Chart(ctxParticipants, {
                type: 'line',
                data: {
                    labels: participantLabels,
                    datasets: [{
                        label: 'Number of Participants Added',
                        data: participantData,
                        borderColor: 'rgba(54, 162, 235, 1)',
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        fill: true,
                        tension: 0.1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        x: {
                            title: {
                                display: true,
                                text: 'Week',
                                color: 'rgba(255, 99, 132, 1)' // Change the color of the x-axis title
                            },
                            ticks: {
                                color: 'rgba(255, 99, 132, 1)' // Change the color of the x-axis labels
                            }
                        },
                        y: {
                            beginAtZero: true,
                            ticks: {
                                stepSize: 1,
                                color: 'rgba(54, 162, 235, 1)' // Change the color of the y-axis labels
                            },
                            title: {
                                display: true,
                                text: 'Number of Participants',
                                color: 'rgba(54, 162, 235, 1)' // Change the color of the y-axis title
                            }
                        }
                    }
                }
            });
        });
    </script>


</body>
</html>


@endsection

