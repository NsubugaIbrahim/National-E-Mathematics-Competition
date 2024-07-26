<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('results', function (Blueprint $table) {
            $table->id("resultId"); // Auto-increment primary key
            $table->foreignId('participantId')
                  ->constrained('participants')
                  ->onDelete('cascade'); // Foreign key to participants table
            $table->foreignId('challengeId')
                  ->constrained('challenges')
                  ->onDelete('cascade'); // Foreign key to challenges table
            $table->integer('score');
            $table->integer('correctAnswers');
            $table->integer('totalQuestions');
            $table->boolean('completed');
            $table->timestamp('receivedAt');
            $table->timestamps();

        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('results');
    }
};
