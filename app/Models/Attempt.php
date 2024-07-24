<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Attempt extends Model
{
    use HasFactory;
    
    protected $primaryKey = 'attemptId';

    // If your primary key is not an integer, you should also set this property
    // public $incrementing = false;

    // If your primary key is not named 'id', you should disable the auto-incrementing behavior
    public $incrementing = false;

    protected $fillable = [
        'participant_id',
        'challenge_id',
        'question_id',
        'is_correct',
        'timestamp',
    ];
}
