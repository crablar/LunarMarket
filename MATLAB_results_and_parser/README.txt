%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Function imports an mp3 and returns a Nx3 matrix where N is the number
% of segments for which frequencies are extracted. N is inversely
% proportional to the segment duration given as arg2 in call. Column 1 of
% the return is the segment end time, column 2 is the dominant frequency on
% the low end of the spectrum, and column 3 is the dominant frequency on
% the high end of the spectrum. Output is contained in tab delimited text
% file call "resultsOut.txt".
% 
% NOTE: Can import both mp3 channels but deals with channel 1 by default.
%
% Accepts three args: (1) filename, (2) duration of each segment in sec,
% and (3) a boolean that governs whether a plot of the time domain signal
% and frequency spectrum are displayed.
% If no segment duration is given, 0.1 sec used as default.
%
% Example call: extractMp3Freq('250Hz_44100Hz_16bit_05sec.mp3',0.1,1)
%
% Ver. 2012-11-20
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
