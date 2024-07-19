<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\Request;

class SchoolRepModel extends Model
{
    use HasFactory;

    protected $table = 'representatives';

    static public function getSingle($id)
    {
        return self::where('id', $id)->where('is_delete', 0)->first();
    }

    static public function getRecord()
    {
        $return = self::select('representatives.*', 'users.name as created_name')
            ->join('users', 'users.id', '=', 'representatives.created_by');

        if (!empty(Request::get('representative_name'))) {
            $return = $return->where('representatives.name', 'like', '%' . Request::get('representative_name') . '%');
        }

        if (!empty(Request::get('representative_email'))) {
            $return = $return->where('representatives.representative_email', 'like', '%' . Request::get('representative_email') . '%');
        }

        if (!empty(Request::get('school_regNo'))) {
            $return = $return->where('representatives.school_regNo', 'like', '%' . Request::get('school_regNo') . '%');
        }

        $return = $return->where('representatives.is_delete', 0)
            ->orderBy('representatives.id', 'desc')
            ->paginate(2);
        return $return;
    }

    static public function getExam()
    {
        $return = self::select('representatives.*')
            ->join('users', 'users.id', '=', 'representatives.created_by')
            ->where('representatives.is_delete', 0)
            ->orderBy('representatives.name', 'asc')
            ->get();
        return $return;
    }
}
