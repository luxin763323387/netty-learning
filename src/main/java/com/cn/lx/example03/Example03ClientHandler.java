package com.cn.lx.example03;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class Example03ClientHandler extends ChannelInboundHandlerAdapter {

    private byte[] req;
    private int counter;

    //解决 拆包、粘包
    //适配 LineBasedFrameDecoder 去除”\n” 或者” \r\n”
    public Example03ClientHandler() {
        req = ("Unless required by applicable dfslaw or agreed to in writing, software" +
                "  distributed under the License is distributed on an \"AS IS\" BASIS," +
                "  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied." +
                "  See the License for the specific language governing permissions and" +
                "  limitations under the License.This connector uses the BIO implementation that requires the JSSE" +
                "  style configuration. When using the APR/native implementation, the" +
                "  penSSL style configuration is required as described in the APR/native" +
                "  documentation.An Engine represents the entry point (within Catalina) that processes" +
                "  every request.  The Engine implementation for Tomcat stand alone" +
                "  analyzes the HTTP headers included with the request, and passes them" +
                "  on to the appropriate Host (virtual host)# Unless required by applicable law or agreed to in writing, software" +
                "# distributed under the License is distributed on an \"AS IS\" BASIS," +
                "# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied." +
                "# See the License for the specific language governing permissions and" +
                "# limitations under the License.# For example, set the org.apache.catalina.util.LifecycleBase logger to log" +
                "# each component that extends LifecycleBase changing state:" +
                "#org.apache.catalina.util.LifecycleBase.level = FINE\n"
        ).getBytes();
    }


    //展示拆包、沾包
//    public Example03ClientHandler() {
//        req = ("Unless required by applicable law or agreed to in writing, software\n" +
//                "  distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
//                "  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
//                "  See the License for the specific language governing permissions and\n" +
//                "  limitations under the License.This connector uses the BIO implementation that requires the JSSE\n" +
//                "  style configuration. When using the APR/native implementation, the\n" +
//                "  penSSL style configuration is required as described in the APR/native\n" +
//                "  documentation.An Engine represents the entry point (within Catalina) that processes\n" +
//                "  every request.  The Engine implementation for Tomcat stand alone\n" +
//                "  analyzes the HTTP headers included with the request, and passes them\n" +
//                "  on to the appropriate Host (virtual host)# Unless required by applicable law or agreed to in writing, software\n" +
//                "# distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
//                "# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
//                "# See the License for the specific language governing permissions and\n" +
//                "# limitations under the License.# For example, set the org.apache.catalina.util.LifecycleBase logger to log\n" +
//                "# each component that extends LifecycleBase changing state:\n" +
//                "#org.apache.catalina.util.LifecycleBase.level = FINE"
//        ).getBytes();
//    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message;
        //将上面的所有字符串作为一个消息体发送出去 出现拆包
        message = Unpooled.buffer(req.length);
        message.writeBytes(req);
        ctx.writeAndFlush(message);

        // 出现粘包
//        for (int i = 0; i < 3; i++) {
//            message = Unpooled.buffer(req.length);
//            message.writeBytes(req);
//            ctx.writeAndFlush(message);
//        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String buf = (String) msg;
        System.out.println("Now is : " + buf + " ; the counter is : "+ (++counter));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
