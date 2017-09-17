YCbCr Approach for text hiding - steganography..

The secret message is encrypted using RSA algorithm to obtain cipher text.
An authentication code for cover image is generated using MD5 algorithm.
The RGB information of cover image is converted to YCbCr color space.
The cipher text and authentication code are converted to binary format using each character’s ASCII representation and are embedded into the LSB of luminance component (Y).
Then the YCbCr values are converted back to RGB color space to form the stego image.
During extraction the same process is inversed to obtain the embedded information.

Note: Not compatible with all types of images. 
