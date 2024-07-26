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
        Schema::create('attempts', function (Blueprint $table) {
            $table->id('attemptId');
            $table->unsignedBigInteger('participantId');
            $table->unsignedBigInteger('challengeId');
            $table->unsignedBigInteger('questionId');
            $table->boolean('isCorrect');
            $table->integer('duration'); // Removed AFTER keyword
            $table->timestamp('attemptedAt');
            $table->timestamps();

            // Add foreign key constraints
            $table->foreign('participantId')->references('participantId')->on('participants')->onDelete('cascade');
            $table->foreign('challengeId')->references('challengeId')->on('challenges')->onDelete('cascade');
            $table->foreign('questionId')->references('questionId')->on('questions')->onDelete('cascade');
        });

    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('attempts');
    }
};
