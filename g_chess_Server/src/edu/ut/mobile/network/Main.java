/*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* Please send inquiries to huber AT ut DOT ee
*/

package edu.ut.mobile.network;


public class Main {

    public static void main(String[] args) {
        NetworkManagerServer nm = new NetworkManagerServer(NetInfo.port);
        nm.makeconnection();
    }

}
